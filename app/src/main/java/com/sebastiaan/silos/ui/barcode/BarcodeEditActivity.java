package com.sebastiaan.silos.ui.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.helper.barcodeHelper;
import com.sebastiaan.silos.db.async.helper.productHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.db.policy.interfaces.DbPolicyInterface;
import com.sebastiaan.silos.db.policy.BarcodePolicy;
import com.sebastiaan.silos.ui.entities.ui_barcode;
import com.sebastiaan.silos.ui.inputMode;
import com.sebastiaan.silos.ui.inputStatus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.sebastiaan.silos.ui.inputMode.EDIT;
import static com.sebastiaan.silos.ui.inputMode.NEW;
import static com.sebastiaan.silos.ui.resultCodes.CANCELED;
import static com.sebastiaan.silos.ui.resultCodes.INSERTED;
import static com.sebastiaan.silos.ui.resultCodes.OK;
import static com.sebastiaan.silos.ui.resultCodes.OVERRIDE;

public class BarcodeEditActivity extends AppCompatActivity implements DbPolicyInterface<barcode> {
    private AsyncManager manager;
    private barcodeHelper barcodeHelper;
    private inputMode inputMode;
    private barcode edit_barcode;

    private Button associateButton;
    private TextView recognisedText;
    private EditText amount;

    private product product;

    private int resultStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(CANCELED);
        setContentView(R.layout.activity_barcode_edit);
        prepareAsync();
        findGlobalViews();
        setupActionBar();
        setupButton();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || !bundle.containsKey("product_parcel"))
            throw new RuntimeException("BarcodeEditActivity did not get a productID in bundle");


        //noinspection ConstantConditions
        product = bundle.getParcelable("product_parcel");

        inputMode = (bundle.containsKey("barcode_parcel")) ? EDIT : NEW;

        if (inputMode == EDIT) {
            edit_barcode = bundle.getParcelable("barcode_parcel");
            //noinspection ConstantConditions
            setBarcode(edit_barcode);
        } else {
            edit_barcode = null;
        }
    }

    private void prepareAsync() {
        manager = new AsyncManager();
        barcodeHelper = new barcodeHelper(manager, getApplicationContext());
    }

    private void findGlobalViews() {
        associateButton = findViewById(R.id.barcode_edit_associate_btn);
        recognisedText = findViewById(R.id.barcode_edit_recognised);
        amount = findViewById(R.id.barcode_edit_amount);
    }

    private void setupActionBar() {
        Toolbar myToolbar = findViewById(R.id.product_edit_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null)
            actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupButton() {
        associateButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, barScanActivity.class);
            startActivityForResult(intent, 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0)
            switch (resultCode) {
                case OK:
                    if (data != null)
                        recognisedText.setText(data.getStringExtra("result"));
            }
    }

    private void setBarcode(barcode barcode) {
        recognisedText.setText(barcode.getBarcodeString());
        amount.setText(String.valueOf(barcode.getAmount()));
    }

    private ui_barcode getBarcode() {
        return new ui_barcode(recognisedText.getText().toString(), Integer.valueOf(amount.getText().toString()));
    }

    private inputStatus checkInput() {
        ui_barcode current = getBarcode();
        if (current.barcodeString.isEmpty() || amount.getText().length() == 0 || current.amount < 1) {
            showEmptyErrors();
            return inputStatus.FIELDSEMPTY;
        }
        return inputStatus.OK;
    }

    private void showEmptyErrors() {
        ui_barcode current = getBarcode();
        if (current.barcodeString.length() == 0)
            associateButton.setError("You must associate a barcode");
        if (amount.getText().length() == 0)
            amount.setError("You must specify how many products (>0) are counted for this barcode");
    }

    private void storeBarcode(ui_barcode input) {
        BarcodePolicy n = new BarcodePolicy(this, barcodeHelper);
        if (inputMode == NEW) {
            resultStatus = INSERTED;
            n.insert(input, product.getId());
        } else if (inputMode == EDIT) {
            resultStatus = OVERRIDE;
            n.update(input.to_barcode(edit_barcode.getId()));
        }
    }

    private void store_forceBarcode(barcode input) {
        BarcodePolicy n = new BarcodePolicy(this, barcodeHelper);
        if (inputMode == NEW) {
            resultStatus = OVERRIDE; //TODO: does this work on UI?
            n.insert_force(input);
        } else if (inputMode == EDIT){
            resultStatus = OVERRIDE;
            n.update_force(input);
        }
    }

    @Override
    public void onConflict(barcode entity, barcode conflictEntity) {
        productHelper h = new productHelper(manager, getApplicationContext());
        h.findByID(new product("", ""), conflictEntity.getProductID(), conflictProduct -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Overwrite");
            alertBuilder.setMessage("Barcode already assigned to "+conflictProduct.getName()+". Overwrite?");
            alertBuilder.setPositiveButton("Yes", (dialog, which) -> store_forceBarcode(entity));
            alertBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            alertBuilder.create().show();
        });
    }

    @Override
    public void onSuccess(barcode entity) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("result", entity);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(resultStatus, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_menu_done:
                if (checkInput() == inputStatus.OK) {
                    storeBarcode(getBarcode());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        manager.cancelAllWorking();
        super.onDestroy();
    }
}

package com.sebastiaan.silos.ui.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.helper.barcodeHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.ui.entities.ui_barcode;
import com.sebastiaan.silos.ui.inputMode;
import com.sebastiaan.silos.ui.inputStatus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.sebastiaan.silos.ui.inputMode.EDIT;
import static com.sebastiaan.silos.ui.inputMode.NEW;
import static com.sebastiaan.silos.ui.resultCodes.CANCELED;
import static com.sebastiaan.silos.ui.resultCodes.INSERTED;
import static com.sebastiaan.silos.ui.resultCodes.OK;

public class BarcodeEditActivity extends AppCompatActivity {
    private AsyncManager manager;
    private barcodeHelper barcodeHelper;
    private inputMode inputMode;
    private barcode edit_barcode;

    private Button associateButton;
    private TextView recognisedText;
    private EditText amount;

    private product product;

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
        Toolbar myToolbar = findViewById(R.id.barcode_edit_toolbar);
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

    private void checkInput() {
        ui_barcode current = getBarcode();
        if (current.barcodeString.isEmpty()) {
            onDoneChecking(current, inputStatus.FIELDSEMPTY);
            return;
        }

        if (inputMode == NEW || (inputMode == EDIT && !edit_barcode.getBarcodeString().equals(current.barcodeString))) {
            barcodeHelper.find(current.barcodeString, result -> {
                if (result == null)
                    onDoneChecking(current, inputStatus.OK);
                else
                    onDoneChecking(current, inputStatus.CONFLICT);
            });
            return;
        }
        onDoneChecking(current, inputStatus.OK);
    }

    private void showEmptyErrors() {
        if (recognisedText.getText().length() == 0)
            associateButton.setError("You must associate a barcode");
        if (amount.getText().length() == 0)
            amount.setError("You must specify how many products are counted for this barcode");
    }

    private void onDoneChecking(ui_barcode input, inputStatus state) {
        switch (state) {
            case OK: {
                barcodeHelper.insert(input, product.getProductID(), result -> activityFinish(INSERTED, input.to_barcode(product.getProductID())));
                break;
            }
            case FIELDSEMPTY: showEmptyErrors(); break;
            case CONFLICT:
                Snackbar snackbar = Snackbar.make(findViewById(R.id.barcode_edit_layout),
                        "Barcode already in use by another product", Snackbar.LENGTH_LONG);
                snackbar.show();

    //        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
    //        alertBuilder.setTitle("Overwrite");
    //        alertBuilder.setMessage(" already exists. Overwrite?");
    //        alertBuilder.setPositiveButton("Yes", (dialog, which) -> {
    //            barcodeHelper.update(supplierID, input, result -> {
    //                activityFinish(OVERRIDE, input.to_barcode(productID));
    //            });
    //        });
    //        alertBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
    //        alertBuilder.create().show();
                break;
        }
    }

    private void activityFinish(int resultCode, barcode barcode) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("result", barcode);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(resultCode, intent);
        Log.e("TESTTTTT", "Activity finish success!");
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_menu_done:
                checkInput();
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

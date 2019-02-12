package com.sebastiaan.silos.ui.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.helper.supplierHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.ui.entities.ui_supplier;
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
import static com.sebastiaan.silos.ui.resultCodes.OVERRIDE;

public class SupplierEditActivity extends AppCompatActivity {
    private AsyncManager manager;
    private supplierHelper supplierHelper;
    private inputMode inputMode;
    private supplier edit_supplier;
    
    private EditText name;
    private EditText city;
    private EditText postalCode;
    private EditText streetname;
    private EditText housenumber;
    private EditText phonenumber;
    private EditText email;
    private EditText webaddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(CANCELED);
        setContentView(R.layout.activity_supplier_edit);
        prepareAsync();
        findGlobalViews();
        setupActionBar();

        Bundle bundle = getIntent().getExtras();
        inputMode = (bundle != null && bundle.containsKey("supplier_parcel")) ? EDIT : NEW;

        if (inputMode == EDIT) {
            edit_supplier = bundle.getParcelable("supplier_parcel");
            //noinspection ConstantConditions
            setSupplier(edit_supplier);
        } else {
            edit_supplier = null;
        }
    }

    private void prepareAsync() {
        manager = new AsyncManager();
        supplierHelper = new supplierHelper(manager, getApplicationContext());
    }

    private void findGlobalViews() {
        name = findViewById(R.id.supplier_edit_name);
        city = findViewById(R.id.supplier_edit_city);
        postalCode = findViewById(R.id.supplier_edit_postalcode);
        streetname = findViewById(R.id.supplier_edit_streetname);
        housenumber = findViewById(R.id.supplier_edit_housenumber);
        phonenumber = findViewById(R.id.supplier_edit_phonenumber);
        email = findViewById(R.id.supplier_edit_email);
        webaddress = findViewById(R.id.supplier_edit_webaddress);
    }

    private void setupActionBar() {
        Toolbar myToolbar = findViewById(R.id.supplier_edit_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null)
            actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void setSupplier(supplier s) {
        name.setText(s.getName());
        city.setText(s.getCity());
        postalCode.setText(s.getPostalcode());
        streetname.setText(s.getStreetname());
        housenumber.setText(s.getHousenumber());
        phonenumber.setText(s.getPhonenumber());
        email.setText(s.getEmailaddress());
        webaddress.setText(s.getWebsite());
    }

    private ui_supplier getSupplier() {
        return new ui_supplier(
                name.getText().toString(),
                streetname.getText().toString(),
                housenumber.getText().toString(),
                city.getText().toString(),
                postalCode.getText().toString(),
                phonenumber.getText().toString(),
                email.getText().toString(),
                webaddress.getText().toString());
    }

    private void checkInput() {
        ui_supplier current = getSupplier();
        if (current.name.isEmpty() || current.city.isEmpty() || current.postalcode.isEmpty() ||
                current.streetname.isEmpty() || current.housenumber.isEmpty()) {
            onDoneChecking(current, inputStatus.FIELDSEMPTY);
            return;
        }
        
        if (inputMode == NEW || (inputMode == EDIT && !edit_supplier.getName().equals(current.name))) {
            supplierHelper.find(current.name, result -> {
                if (result == null)
                    onDoneChecking(current, inputStatus.OK);
                else
                    onDoneChecking(current, result.getSupplierID());
            });
            return;
        }
        onDoneChecking(current, inputStatus.OK);
    }

    private void showEmptyErrors() {
        if (name.getText().length() == 0)
            name.setError("This field must be filled");

        if (city.getText().length() == 0)
            city.setError("This field must be filled");

        if (postalCode.getText().length() == 0)
            postalCode.setError("This field must be filled");

        if (streetname.getText().length() == 0)
            streetname.setError("This field must be filled");

        if (housenumber.getText().length() == 0)
            housenumber.setError("This field must be filled");
    }

    private void onDoneChecking(ui_supplier input, inputStatus state) {
        switch (state) {
            case OK: {
                supplierHelper.insert(input, result -> {
                    activityFinish(INSERTED, input.to_supplier(result));
                });
                break;
            }
            case FIELDSEMPTY: showEmptyErrors(); break;
        }
    }

    private void onDoneChecking(ui_supplier input, long supplierID) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Overwrite");
        alertBuilder.setMessage(input.name + " already exists. Overwrite?");
        alertBuilder.setPositiveButton("Yes", (dialog, which) -> {
            supplierHelper.update(supplierID, input, result -> {
                activityFinish(OVERRIDE, input.to_supplier(supplierID));
            });
        });
        alertBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertBuilder.create().show();
    }

    private void activityFinish(int resultCode, supplier supplier) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("result", supplier);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(resultCode, intent);
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
}

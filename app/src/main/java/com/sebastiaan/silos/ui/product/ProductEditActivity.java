package com.sebastiaan.silos.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.helper.productHelper;
import com.sebastiaan.silos.db.async.helper.supplierHelper;
import com.sebastiaan.silos.db.async.helper.supplier_productHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.ui.adapters.supplier.supplierAdapterCheckable;
import com.sebastiaan.silos.ui.entities.ui_product;
import com.sebastiaan.silos.ui.inputMode;
import com.sebastiaan.silos.ui.inputStatus;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.sebastiaan.silos.ui.inputMode.EDIT;
import static com.sebastiaan.silos.ui.inputMode.NEW;
import static com.sebastiaan.silos.ui.resultCodes.INSERTED;
import static com.sebastiaan.silos.ui.resultCodes.OVERRIDE;

public class ProductEditActivity extends AppCompatActivity {
    private AsyncManager manager;

    private EditText productNameText;
    private EditText productDescriptionText;
    private RecyclerView supplierlist;

    private supplierAdapterCheckable supplierAdapter;

    private inputMode inputMode;

    private product edit_product;
    private Set<supplier> edit_suppliers;

    private productHelper productHelper;
    private supplierHelper supplierHelper;
    private supplier_productHelper supplier_productHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareAscync();
        setContentView(R.layout.activity_product_edit);
        setResult(RESULT_CANCELED);
        findGlobalViews();

        Bundle bundle = getIntent().getExtras();
        inputMode = (bundle != null && bundle.containsKey("product_parcel")) ? EDIT : NEW;

        if (inputMode == EDIT) {
            edit_product = bundle.getParcelable("product_parcel");
            //noinspection ConstantConditions
            edit_suppliers = new HashSet<>(bundle.getParcelableArrayList("suppliers_parcel"));
            setProduct(edit_product);
        } else {
            edit_product = null;
            edit_suppliers = null;
        }
        prepareList(edit_suppliers);
        setupActionBar();
    }

    private void prepareAscync() {
         manager = new AsyncManager();
         productHelper = new productHelper(manager, getApplicationContext());
         supplierHelper = new supplierHelper(manager, getApplicationContext());
         supplier_productHelper = new supplier_productHelper(manager, getApplicationContext());
    }

    private void prepareList(Set<supplier> enabledList) {
        supplierHelper.getAll(result -> {
            if (enabledList != null)
                supplierAdapter = new supplierAdapterCheckable(result, enabledList);
            else
                supplierAdapter = new supplierAdapterCheckable(result);

            supplierlist.setLayoutManager(new LinearLayoutManager(this));
            supplierlist.setAdapter(supplierAdapter);
            supplierlist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        });
    }

    private void findGlobalViews() {
        productNameText = findViewById(R.id.product_edit_name);
        productDescriptionText = findViewById(R.id.product_edit_description);
        supplierlist = findViewById(R.id.product_edit_supplierslist);
    }

    private void setupActionBar() {
        Toolbar myToolbar = findViewById(R.id.product_edit_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setProduct(product product) {
        productNameText.setText(product.getProductName());
        productDescriptionText.setText(product.getProductDescription());
    }

    private ui_product getProduct() {
        return new ui_product(productNameText.getText().toString(), productDescriptionText.getText().toString());
    }

    private void checkInput() {
        ui_product current = getProduct();
        if (current.productname.isEmpty() || current.description.isEmpty() || supplierAdapter.getSelectedItemCount() == 0) {
            onDoneChecking(current, inputStatus.FIELDSEMPTY);
            return;
        }
        //check for override issues when new product is done or when existing product changed name
        if (inputMode == NEW || (inputMode == EDIT && !edit_product.getProductName().equals(current.productname))) {
            productHelper.find(current.productname, result -> {
                if (result == null)
                    onDoneChecking(current, inputStatus.OK);
                else
                    onDoneChecking(current, result.getProductID());
            });
            return;
        }
        onDoneChecking(current, inputStatus.OK);
    }

    private void showEmptyErrors(ui_product current) {
        if (current.productname.isEmpty())
            productNameText.setError("This field must be filled");

        if (current.description.isEmpty())
            productDescriptionText.setError("This field must be filled");

        if (supplierAdapter.getSelectedItemCount() == 0) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.product_edit_layout),"Give at least 1 supplier", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void onDoneChecking(ui_product current, inputStatus state) {
        switch (state) {
            case OK:
                if (inputMode == NEW)
                    insertProduct(current);
                else
                    overrideProduct(current, edit_product.getProductID());
                break;
            case FIELDSEMPTY: showEmptyErrors(current); break;
        }
    }

    private void onDoneChecking(ui_product current, long conflictID) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Overwrite");//TODO: use string resources
        alertBuilder.setMessage(current.productname + " already exists. Overwrite?");
        alertBuilder.setPositiveButton("Yes", (dialog, which) -> overrideProduct(current, conflictID));
        alertBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

    }

    private void insertProduct(ui_product input) {
        productHelper.insert(input, result -> {
            insertRelations(result, input);
        });
    }

    private void insertRelations(long productID, ui_product input) {
        supplier_productHelper.insert(productID, supplierAdapter.getSelectedItems(), result -> {
            activityFinish(INSERTED, input.to_product(productID));
        });
    }

    private void overrideProduct(ui_product input, long productID) {
        productHelper.update(productID, input, result -> {
            updateRelations(input, productID, supplierAdapter.getSelectedItems());
        });
    }

    private void updateRelations(ui_product input, long productID, Set<supplier> suppliers) {
        supplier_productHelper.update(productID, edit_suppliers, suppliers, result -> {
            activityFinish(OVERRIDE, input.to_product(productID));
        });
    }

    private void activityFinish(int resultCode, product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("result", product);
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

    @Override
    protected void onDestroy() {
        manager.cancelAllWorking();
        super.onDestroy();
    }
}

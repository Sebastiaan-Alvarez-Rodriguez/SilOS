package com.sebastiaan.silos.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.helper.productHelper;
import com.sebastiaan.silos.db.async.helper.supplierHelper;
import com.sebastiaan.silos.db.async.helper.supplier_productHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.db.policy.interfaces.DbPolicyInterface;
import com.sebastiaan.silos.db.policy.ProductPolicy;
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
import static com.sebastiaan.silos.ui.resultCodes.CANCELED;
import static com.sebastiaan.silos.ui.resultCodes.INSERTED;
import static com.sebastiaan.silos.ui.resultCodes.OVERRIDE;

public class ProductEditActivity extends AppCompatActivity implements DbPolicyInterface<product> {
    private AsyncManager manager;

    private EditText name;
    private EditText description;
    private RecyclerView supplierlist;

    private supplierAdapterCheckable supplierAdapter;

    private inputMode inputMode;

    private product edit_product;
    private Set<supplier> edit_suppliers;

    private productHelper productHelper;
    private supplierHelper supplierHelper;
    private supplier_productHelper supplier_productHelper;

    private int resultStatus = CANCELED;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareAscync();
        setContentView(R.layout.activity_product_edit);
        setResult(CANCELED);
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
                supplierAdapter = new supplierAdapterCheckable(result, null, enabledList);
            else
                supplierAdapter = new supplierAdapterCheckable(result, null);

            supplierlist.setLayoutManager(new LinearLayoutManager(this));
            supplierlist.setAdapter(supplierAdapter);
            supplierlist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        });
    }

    private void findGlobalViews() {
        name = findViewById(R.id.product_edit_name);
        description = findViewById(R.id.product_edit_description);
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
        name.setText(product.getName());
        description.setText(product.getProductDescription());
    }

    private ui_product getProduct() {
        return new ui_product(name.getText().toString(), description.getText().toString());
    }

    private inputStatus checkInput() {
        ui_product current = getProduct();
        if (current.productname.isEmpty() || current.description.isEmpty() || supplierAdapter.getSelectedItemCount() == 0) {
            showEmptyErrors();
            return inputStatus.FIELDSEMPTY;
        }
        return inputStatus.OK;
    }

    private void showEmptyErrors() {
        ui_product current = getProduct();
        if (current.productname.isEmpty())
            name.setError("This field must be filled");

        if (current.description.isEmpty())
            description.setError("This field must be filled");

        if (supplierAdapter.getSelectedItemCount() == 0) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.product_edit_layout),"Give at least 1 supplier", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private boolean nameChanged() {
        return edit_product != null && !edit_product.getName().equals(name.getText().toString());
    }

    private void storeProduct(ui_product input) {
        ProductPolicy n = new ProductPolicy(this, productHelper);
        if (inputMode == NEW) {
            resultStatus = INSERTED;
            n.insert(input);
        } else if (inputMode == EDIT) {
            resultStatus = OVERRIDE;
            n.update(input.to_product(edit_product.getId()));
        }
    }

    private void store_forceProduct(product input) {
        ProductPolicy n = new ProductPolicy(this, productHelper);
        if (inputMode == NEW) {
            resultStatus = INSERTED;
            n.insert_force(input);
        } else if (inputMode == EDIT) {
            resultStatus = OVERRIDE;
            n.update_force(input);
        }
    }

    private void store_SupplierProducts(Set<supplier> selected, long productID, DbAsyncInterface<long[]> onFinish) {
//        if (inputMode == NEW) {
            supplier_productHelper.insert(productID, selected, onFinish);
//        }
    }

    @Override
    public void onConflict(product entity, product conflictEntity) {
        //TODO: product_supplier relations
        //AND: edit situation
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Overwrite");//TODO: use string resources
        alertBuilder.setMessage(entity.getName() + " already exists. Overwrite?");
        alertBuilder.setPositiveButton("Yes", (dialog, which) -> store_forceProduct(entity));
        alertBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertBuilder.create().show();
    }

    @Override
    public void onSuccess(product entity) {
        //TODO: product_supplier relations
        store_SupplierProducts(supplierAdapter.getSelectedItems(), entity.getId(), resultIDs -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("result", entity);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(resultStatus, intent);
            finish();
        });
    }

    private void insertRelations(long productID, ui_product input) {
        supplier_productHelper.insert(productID, supplierAdapter.getSelectedItems(), result -> {
//            activityFinish(INSERTED, input.to_product(productID));
        });
    }

    private void overrideProduct(ui_product input, long productID) {
        productHelper.update(productID, input, result -> {
            updateRelations(input, productID, supplierAdapter.getSelectedItems());
        });
    }

    private void updateRelations(ui_product input, long productID, Set<supplier> suppliers) {
        supplier_productHelper.update(productID, edit_suppliers, suppliers, result -> {
//            activityFinish(OVERRIDE, input.to_product(productID));
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_menu_done:
                if (checkInput() == inputStatus.OK) {
                    storeProduct(getProduct());
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

package com.sebastiaan.silos.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.helper.productHelper;
import com.sebastiaan.silos.db.async.helper.supplier_productHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.ui.adapters.actionCallback;
import com.sebastiaan.silos.ui.adapters.product.productAdapterAction;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.sebastiaan.silos.ui.resultCodes.INSERTED;
import static com.sebastiaan.silos.ui.resultCodes.OVERRIDE;

public class ProductsActivity extends AppCompatActivity implements ActionMode.Callback, actionCallback<product> {
    private static final int NEW_PRODUCT = 0;
    private static final int EDIT_PRODUCT = 1;

    private AsyncManager manager;
    private productHelper productHelper;
    private supplier_productHelper supplier_productHelper;

    private productAdapterAction adapter;

    private ActionMode actionMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        prepareAsync();
        prepareList();
        setupActionBar();
        setFab();
    }

    private void prepareAsync() {
        manager = new AsyncManager();
        productHelper = new productHelper(manager, getApplicationContext());
        supplier_productHelper = new supplier_productHelper(manager, getApplicationContext());
    }

    private void prepareList() {
        RecyclerView productList = findViewById(R.id.activity_list_list);
        productHelper.getAll(result -> {
            productList.setLayoutManager(new LinearLayoutManager(this));
            adapter = new productAdapterAction(result,this, supplier_productHelper);
            adapter.setSelectedColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
            productList.setAdapter(adapter);
            productList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        });
    }

    private void setupActionBar() {
        Toolbar myToolbar = findViewById(R.id.list_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Products"); //TODO: use string resource
        }
    }

    private void setFab() {
        FloatingActionButton addFab = findViewById(R.id.activity_list_addBtn);
        addFab.setOnClickListener(v-> {
            Intent intent = new Intent(this, ProductEditActivity.class);
            startActivityForResult(intent, NEW_PRODUCT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NEW_PRODUCT:
                switch (resultCode) {
                    case INSERTED:
                        if (data != null)
                            adapter.itemAdded(data.getParcelableExtra("result"));
                        break;
                    case OVERRIDE:
                        if (data != null)
                            adapter.itemOverriden(data.getParcelableExtra("result"));
                        break;
                }
                break;
            case EDIT_PRODUCT:
                //TODO: bouw hier iets voor
        }
    }

    private void deleteSelected() {
        List<product> list = adapter.getSelected();

        productHelper.deleteAll(list, result -> {
            prepareList();
        });
        //TODO: worden de relations automatisch gedelete als ik product delete?
//        List<Integer> idList = new ArrayList<>();
//        for (product p : list)
//            idList.add(p.getProductID());
//        supplier_productQueries.deleteForProductTask task2 = new supplier_productQueries.deleteForProductTask(manager, getApplicationContext(), result -> prepareList(), idList.toArray(new Integer[0]));
//        task2.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                deleteSelected();
                adapter.finishActionMode();
                mode.finish();
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
    }

    @Override
    public boolean onItemClick(View v, product object) {
        if (actionMode == null) {
            Intent editIntent = new Intent(this, ProductEditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("product_parcel", object);

            supplier_productHelper.getForProduct(object.getProductID(), result -> {
                Log.e("TEST", "found relations: " + result.size());
                bundle.putParcelableArrayList("suppliers_parcel", new ArrayList<>(result));
                editIntent.putExtras(bundle);
                startActivityForResult(editIntent, EDIT_PRODUCT);
            });
        }
        return true;
    }

    @Override
    public boolean onItemLongClick(View v, product object) {
        if (actionMode == null)
            actionMode = startSupportActionMode(this);
        return true;
    }

    @Override
    public void onEmptyItemSelection() {
        adapter.finishActionMode();
        actionMode.finish();
    }

    @Override
    protected void onDestroy() {
        manager.cancelAllWorking();
        super.onDestroy();
    }
}
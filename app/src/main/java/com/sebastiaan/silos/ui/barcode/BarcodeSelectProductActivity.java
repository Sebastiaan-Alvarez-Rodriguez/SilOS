package com.sebastiaan.silos.ui.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.helper.productHelper;
import com.sebastiaan.silos.db.async.helper.supplier_productHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.product.productAdapterBase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BarcodeSelectProductActivity extends AppCompatActivity implements clickCallback<product> {
    private AsyncManager manager;
    private productHelper productHelper;
    private supplier_productHelper supplier_productHelper;
    private productAdapterBase adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareAsync();
        setContentView(R.layout.activity_list);

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
            adapter = new productAdapterBase(result, this, supplier_productHelper);
            productList.setLayoutManager(new LinearLayoutManager(this));
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
//        FloatingActionButton addFab = findViewById(R.id.activity_list_addBtn);
//        addFab.setVisibility(View.GONE);
        findViewById(R.id.activity_list_addBtn).setVisibility(View.GONE);
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
    public boolean onItemClick(View v, product p) {
        Intent editIntent = new Intent(this, BarcodesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("product_parcel", p);
        editIntent.putExtras(bundle);
        startActivity(editIntent);
        return true;
    }

    @Override
    public boolean onItemLongClick(View v, product p) {
        return onItemClick(v, p);
    }

    @Override
    protected void onDestroy() {
        manager.cancelAllWorking();
        super.onDestroy();
    }
}

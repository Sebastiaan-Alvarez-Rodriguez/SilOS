package com.sebastiaan.silos.ui.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.ui.adapters.interfaces.actionCallback;
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.supplier.supplierAdapterAction;
import com.sebastiaan.silos.ui.requestCodes;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SuppliersActivity extends AppCompatActivity implements ActionMode.Callback, clickCallback<supplier>, actionCallback {
    private SupplierViewModel model;
    private supplierAdapterAction adapter;
    private ActionMode actionMode = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = ViewModelProviders.of(this).get(SupplierViewModel.class);

        setContentView(R.layout.activity_list);
        prepareList();
        setupActionBar();
        setFab();
    }

    private void prepareList() {
        adapter = new supplierAdapterAction(new ArrayList<>(), this, this);
        adapter.setSelectedColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));

        model.getAll().observe(this, adapter.getObserver());

        RecyclerView supplierlist = findViewById(R.id.activity_list_list);
        supplierlist.setLayoutManager(new LinearLayoutManager(this));
        supplierlist.setAdapter(adapter);
        supplierlist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.list_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Suppliers");//TODO: use string resource
        }
    }

    private void setFab() {
        FloatingActionButton addFab = findViewById(R.id.activity_list_addBtn);
        addFab.setOnClickListener(v-> {
            Intent intent = new Intent(this, SupplierEditActivity.class);
            startActivityForResult(intent, requestCodes.NEW);
        });
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

    private void deleteSelected() {
        model.deleteAll(adapter.getSelected(), result -> prepareList());
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
    public boolean onItemClick(View v, supplier s) {
        if (actionMode == null) {
            Intent editIntent = new Intent(this, SupplierEditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("supplier_parcel", s);
            editIntent.putExtras(bundle);
            startActivityForResult(editIntent, requestCodes.EDIT);
        }
        return true;
    }

    @Override
    public boolean onItemLongClick(View v, supplier s) {
        if (actionMode == null)
            actionMode = startSupportActionMode(this);
        return true;
    }

    @Override
    public void onEmptyItemSelection() {
        adapter.finishActionMode();
        actionMode.finish();
    }
}

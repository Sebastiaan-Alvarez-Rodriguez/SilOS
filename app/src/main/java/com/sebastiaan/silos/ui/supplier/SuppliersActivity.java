package com.sebastiaan.silos.ui.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.helper.supplierHelper;
import com.sebastiaan.silos.db.async.task.AsyncManager;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.ui.adapters.interfaces.actionCallback;
import com.sebastiaan.silos.ui.adapters.supplier.supplierAdapterAction;
import com.sebastiaan.silos.ui.requestCodes;

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

public class SuppliersActivity extends AppCompatActivity implements ActionMode.Callback, actionCallback<supplier> {
    private AsyncManager manager;
    private supplierHelper supplierHelper;

    private supplierAdapterAction adapter;
    private ActionMode actionMode = null;

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
         supplierHelper = new supplierHelper(manager, getApplicationContext());
    }

    private void prepareList() {
        RecyclerView supplierlist = findViewById(R.id.activity_list_list);
        supplierHelper.getAll(list -> {
            supplierlist.setLayoutManager(new LinearLayoutManager(this));
            adapter = new supplierAdapterAction(list, this);
            adapter.setSelectedColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
            supplierlist.setAdapter(adapter);
            supplierlist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case requestCodes.NEW:
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
            case requestCodes.EDIT:
                //TODO: bouw hier iets voor
        }
    }

    private void deleteSelected() {
        List<supplier> list = adapter.getSelected();
        supplierHelper.deleteAll(list, result -> prepareList());
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

    @Override
    protected void onDestroy() {
        manager.cancelAllWorking();
        super.onDestroy();
    }
}

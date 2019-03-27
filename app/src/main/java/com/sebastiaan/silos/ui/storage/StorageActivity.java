package com.sebastiaan.silos.ui.storage;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.entities.storage;
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.storage.storageAdapterBase;
import com.sebastiaan.silos.ui.requestCodes;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

public class StorageActivity extends AppCompatActivity implements clickCallback<storage> {
        private StorageViewModel model;
        private storageAdapterBase adapter;

        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            model = ViewModelProviders.of(this).get(StorageViewModel.class);

            setContentView(R.layout.activity_list);
            prepareList();
            setupActionBar();
            setFab();
            setAmount();
        }

        private void prepareList() {
            adapter = new storageAdapterBase(new ArrayList<>(), this);

            model.getAll().observe(this, adapter.getObserver());

            RecyclerView storageList = findViewById(R.id.activity_list_list);
            storageList.setLayoutManager(new LinearLayoutManager(this));
            storageList.setAdapter(adapter);
            storageList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        }

        private void setupActionBar() {
            Toolbar toolbar = findViewById(R.id.list_toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            if (actionbar != null) {
                actionbar.setDisplayHomeAsUpEnabled(true);
                actionbar.setTitle("Storage");
            }
        }

        private void setFab() {
            FloatingActionButton addFab = findViewById(R.id.activity_list_addBtn);
            addFab.setOnClickListener(v-> {
                Intent intent = new Intent(this, StorageEditActivity.class);
                intent.putExtra("IN", true);
                startActivityForResult(intent, requestCodes.NEW);
            });
        }

        private void setAmount() {
            EditText amount = findViewById(R.id.storage_number);
            FloatingActionButton sub = findViewById(R.id.storage_sub_btn);
            sub.setOnClickListener(v-> {
                int amount_nr = Integer.parseInt(amount.getText().toString());
                if ( amount_nr > 0) {
                    amount.setText(String.valueOf(--amount_nr));
                }
            });

            FloatingActionButton add = findViewById(R.id.storage_add_btn);
            add.setOnClickListener(v-> {
                int amount_nr = Integer.parseInt(amount.getText().toString());
                amount.setText(String.valueOf(++amount_nr));
            });
        }

    @Override
    public boolean onItemClick(View v, storage object) {
        return false;
    }

    @Override
    public boolean onItemLongClick(View v, storage object) {
        return false;
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
}


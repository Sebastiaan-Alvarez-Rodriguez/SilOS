package com.sebastiaan.silos.ui.storage;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.ui.requestCodes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

public class StorageActivity extends AppCompatActivity {
        private StorageViewModel model;

        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            model = ViewModelProviders.of(this).get(StorageViewModel.class);

            setContentView(R.layout.activity_list);
            prepareList();
            setupActionBar();
            setFab();
        }

        private void prepareList() {
            //model.getAll();

            RecyclerView storageList = findViewById(R.id.activity_list_list);
            storageList.setLayoutManager(new LinearLayoutManager(this));
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
                startActivityForResult(intent, requestCodes.NEW);
            });
        }
}


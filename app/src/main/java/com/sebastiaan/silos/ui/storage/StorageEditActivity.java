package com.sebastiaan.silos.ui.storage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.sebastiaan.silos.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StorageEditActivity extends AppCompatActivity  {
    private boolean in;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_storage_edit);

        Intent intent = getIntent();
        in = intent.getBooleanExtra("IN", false);

        setupActionBar();
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.storage_edit_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            String title = (in) ? "Storage in": "Storage out";
            actionbar.setTitle(title);
        }
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

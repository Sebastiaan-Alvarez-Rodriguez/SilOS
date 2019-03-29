package com.sebastiaan.silos.ui.storage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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
        setupIncrDecr();
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

    private void setupIncrDecr() {
        EditText amount = findViewById(R.id.storage_number);
        Button sub = findViewById(R.id.storage_sub_btn);
        sub.setOnClickListener(v-> {
            int amount_nr = Integer.parseInt(amount.getText().toString());
            if ( amount_nr > 0) {
                amount.setText(String.valueOf(--amount_nr));
            }
        });

        Button add = findViewById(R.id.storage_add_btn);
        add.setOnClickListener(v-> {
            int amount_nr = Integer.parseInt(amount.getText().toString());
            amount.setText(String.valueOf(++amount_nr));
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
}

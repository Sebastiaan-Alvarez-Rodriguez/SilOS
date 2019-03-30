package com.sebastiaan.silos.ui.storage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        setupNewValue();
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

    //for long click: https://stackoverflow.com/questions/4402740/android-long-click-on-a-button-perform-actions
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

    private void setupNewValue() {
        EditText amount = findViewById(R.id.storage_number);
        TextView cur_value = findViewById(R.id.storage_current_nmr);
        TextView new_value = findViewById(R.id.storage_new_nmr);

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String amount_string = amount.getText().toString();
                int amount_value = ((amount_string.equals("")) ? 0 : Integer.parseInt(amount_string));
                int cur_amount_value = Integer.parseInt(cur_value.getText().toString());
                if (in)
                    new_value.setText(String.valueOf(cur_amount_value + amount_value));
                else {
                    new_value.setText(String.valueOf(cur_amount_value - amount_value));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
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

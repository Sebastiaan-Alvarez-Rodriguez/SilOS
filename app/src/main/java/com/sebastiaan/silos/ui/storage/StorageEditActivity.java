package com.sebastiaan.silos.ui.storage;

import android.content.Intent;
import android.os.Bundle;

import com.sebastiaan.silos.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StorageEditActivity extends AppCompatActivity  {
    private boolean in;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_storage_edit);

        Intent intent = getIntent();
        in = intent.getBooleanExtra("IN", false);


    }
}

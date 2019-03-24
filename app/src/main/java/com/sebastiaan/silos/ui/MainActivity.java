package com.sebastiaan.silos.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.sebastiaan.silos.R;
import com.sebastiaan.silos.ui.barcode.BarcodeSelectProductActivity;
import com.sebastiaan.silos.ui.product.ProductsActivity;
import com.sebastiaan.silos.ui.storage.StorageEditActivity;
import com.sebastiaan.silos.ui.supplier.SuppliersActivity;

//https://proandroiddev.com/enter-animation-using-recyclerview-and-layoutanimation-part-1-list-75a874a5d213

//mAdapter.notifyItemInserted(mItems.size() - 1);
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button new_order = findViewById(R.id.main_new_order_btn); //TODO;

        new_order.setOnClickListener(v -> {
            Intent intent = new Intent(this, StorageEditActivity.class);
            intent.putExtra("IN", true);
            startActivity(intent);
        });

        Button storage_out = findViewById(R.id.main_storage_out_btn);
        storage_out.setOnClickListener(v -> {
            Intent intent = new Intent(this, StorageEditActivity.class);
            intent.putExtra("IN", false);
            startActivity(intent);
        });

        Button supplierButton = findViewById(R.id.main_suppliers_btn);
        supplierButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SuppliersActivity.class);
            startActivity(intent);
        });

        Button productButton = findViewById(R.id.main_products_btn);
        productButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductsActivity.class);
            startActivity(intent);
        });

        Button barcodebutton = findViewById(R.id.main_barcodes_btn);
        barcodebutton.setOnClickListener(hallo -> {
            Intent intent = new Intent(this, BarcodeSelectProductActivity.class);
            startActivity(intent);
        });
    }
}

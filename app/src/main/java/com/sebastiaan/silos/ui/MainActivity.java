package com.sebastiaan.silos.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.ui.product.ProductsActivity;
import com.sebastiaan.silos.ui.supplier.SuppliersActivity;

//https://proandroiddev.com/enter-animation-using-recyclerview-and-layoutanimation-part-1-list-75a874a5d213

//mAdapter.notifyItemInserted(mItems.size() - 1);
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button new_order = findViewById(R.id.main_new_order_btn);//TODO: implement ordering and send user there

        Button storage_in = findViewById(R.id.main_storage_in_btn);//TODO: implement storage holdings and send user there
        Button storage_out = findViewById(R.id.main_storage_out_btn);//TODO: implement storage holdings and send user there

        Button new_supplier = findViewById(R.id.main_suppliers_btn);
        new_supplier.setOnClickListener(v -> {
            Intent intent = new Intent(this, SuppliersActivity.class);
            startActivity(intent);
        });

        Button new_product = findViewById(R.id.main_products_btn);
        new_product.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductsActivity.class);
            startActivity(intent);
        });

    }
}

package com.sebastiaan.silos.ui.adapters.viewholders.product;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.async.helper.supplier_productHelper;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.ui.adapters.supplier.supplierAdapterBase;
import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;
import com.sebastiaan.silos.ui.adapters.viewholders.viewHolderClickCallback;

import java.util.ArrayList;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class productBaseViewHolder extends baseViewHolder<product> {
    public static final @LayoutRes int layoutResource = R.layout.product_list_item;
    private long id;

    private TextView productName, productDescription;
    private RelativeLayout detailView;

    private RecyclerView productSupplierView;

    private supplier_productHelper supplier_productHelper;
    private supplierAdapterBase adapter;
    private boolean suppliersReceived;

    public productBaseViewHolder(@NonNull View itemView, supplier_productHelper supplier_productHelper, viewHolderClickCallback clickCallback) {
        super(itemView);
        this.supplier_productHelper = supplier_productHelper;
        suppliersReceived = false;

        productName = itemView.findViewById(R.id.product_list_name);
        ImageButton expandDetailButton = itemView.findViewById(R.id.product_list_expand_collapse);
        productDescription = itemView.findViewById(R.id.product_list_detail_description);
        productSupplierView = itemView.findViewById(R.id.product_list_detail_supplierView);
        detailView = itemView.findViewById(R.id.product_list_detailview);

        detailView.setVisibility(View.GONE);

        expandDetailButton.setOnClickListener(v -> {
            if (detailView.getVisibility() == View.GONE) {
                if (!suppliersReceived)
                    prepareList(itemView);
                expandDetailButton.setBackgroundResource(R.drawable.ic_arrow_up);
                detailView.setVisibility(View.VISIBLE);
            } else {
                expandDetailButton.setBackgroundResource(R.drawable.ic_arrow_down);
                detailView.setVisibility(View.GONE);
            }
        });

        itemView.setOnLongClickListener(v -> clickCallback.onClick(v, true, getAdapterPosition()));
        itemView.setOnClickListener(v -> clickCallback.onClick(v, false, getAdapterPosition()));
    }

    private void prepareList(View itemView) {
        supplier_productHelper.getForProduct(id, result -> {
            productSupplierView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            adapter = new supplierAdapterBase(new ArrayList<>(result), null);
            productSupplierView.setAdapter(adapter);
            productSupplierView.addItemDecoration(new DividerItemDecoration(itemView.getContext(), LinearLayoutManager.VERTICAL));
            suppliersReceived = true;
            Log.e("TEST", "Ik heb gevonden voor ID="+id+": " + result.size());
        });
    }

    @Override
    public void set(product product) {
        id = product.getProductID();
        productName.setText(product.getProductName());
        productDescription.setText(product.getProductDescription());
    }

    @Override
    public product get() {
        return new product(productName.getText().toString(), productDescription.getText().toString());
    }
}

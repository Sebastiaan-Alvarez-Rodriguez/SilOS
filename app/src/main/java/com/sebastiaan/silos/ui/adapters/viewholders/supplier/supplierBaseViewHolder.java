package com.sebastiaan.silos.ui.adapters.viewholders.supplier;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sebastiaan.silos.R;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * Handles basic supplier in listview (No edit, not checkable)
 * You need to give this class an inflated R.layout.supplier_list_item
 */
public class supplierBaseViewHolder extends baseViewHolder<supplier> {
    public static final @LayoutRes int layoutResource = R.layout.supplier_list_item;

    private TextView supplierName, supplierCity, supplierPostalCode, supplierStreet, supplierHouseNumber, supplierSite, supplierEmail, supplierPhone;
    private RelativeLayout detailView;


    public supplierBaseViewHolder(@NonNull View itemView) {
        super(itemView);

        supplierName = itemView.findViewById(R.id.supplier_list_name);
        ImageButton expandDetailButton = itemView.findViewById(R.id.supplier_list_expand_collapse_view);
        supplierCity = itemView.findViewById(R.id.supplier_list_detail_city);
        supplierPostalCode = itemView.findViewById(R.id.supplier_list_detail_postal_code);
        supplierStreet = itemView.findViewById(R.id.supplier_list_detail_street);
        supplierHouseNumber = itemView.findViewById(R.id.supplier_list_detail_housenumber);
        supplierSite = itemView.findViewById(R.id.supplier_list_detail_site);
        supplierEmail = itemView.findViewById(R.id.supplier_list_detail_email);
        supplierPhone = itemView.findViewById(R.id.supplier_list_detail_phone);

        detailView = itemView.findViewById(R.id.supplier_list_detail_view);

        detailView.setVisibility(View.GONE);


        expandDetailButton.setOnClickListener(v -> {
            if (detailView.getVisibility() == View.GONE) {
                expandDetailButton.setBackgroundResource(R.drawable.ic_arrow_up);
                detailView.setVisibility(View.VISIBLE);
            } else {
                expandDetailButton.setBackgroundResource(R.drawable.ic_arrow_down);
                detailView.setVisibility(View.GONE);
            }
        });

    }
    @Override
    public void set(supplier supplier) {
        supplierName.setText(supplier.getName());
        supplierCity.setText(supplier.getCity());
        supplierPostalCode.setText(supplier.getPostalcode());
        supplierStreet.setText(supplier.getStreetname());
        supplierHouseNumber.setText(supplier.getHousenumber());
        supplierSite.setText(supplier.getWebsite());
        supplierEmail.setText(supplier.getEmailaddress());
        supplierPhone.setText(supplier.getPhonenumber());
    }

    @Override
    public supplier get() {
        return new supplier(
                supplierName.getText().toString(),
                supplierStreet.getText().toString(),
                supplierHouseNumber.getText().toString(),
                supplierCity.getText().toString(),
                supplierPostalCode.getText().toString(),
                supplierPhone.getText().toString(),
                supplierEmail.getText().toString(),
                supplierSite.getText().toString());
    }
}
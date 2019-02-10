package com.sebastiaan.silos.ui.adapters.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class baseViewHolder<T> extends RecyclerView.ViewHolder {
    public baseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void set(T t);

    public abstract T get();
}

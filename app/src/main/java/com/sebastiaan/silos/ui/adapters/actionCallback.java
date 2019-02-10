package com.sebastiaan.silos.ui.adapters;

import android.view.View;

public interface actionCallback<T> {
    boolean onItemClick(View v, T object);
    boolean onItemLongClick(View v, T object);
    void onEmptyItemSelection();
}

package com.sebastiaan.silos.ui.adapters.interfaces;

import android.view.View;

public interface clickCallback<T> {
    boolean onItemClick(View v, T object);
    boolean onItemLongClick(View v, T object);
}

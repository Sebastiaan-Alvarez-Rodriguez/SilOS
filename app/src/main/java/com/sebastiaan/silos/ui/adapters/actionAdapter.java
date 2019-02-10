package com.sebastiaan.silos.ui.adapters;

import android.view.View;

import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;
import com.sebastiaan.silos.ui.adapters.viewholders.viewHolderClickCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class actionAdapter<T extends baseViewHolder<U>, U> extends baseAdapter<T, U> implements viewHolderClickCallback {
    private boolean multiSelect = false;

    protected actionCallback<U> callback;
    protected HashSet<Integer> selectedItems = new HashSet<>();

    protected int selectedColor;

    public actionAdapter(List<U> list, actionCallback<U> callback) {
        super(list);
        this.callback = callback;
    }

    public List<U> getSelected() {
        List<U> returnlist = new ArrayList<>();
        for (int index : selectedItems) {
            returnlist.add(list.get(index));
        }
        return returnlist;
    }

    public void setSelectedColor(int argb_color) {
        selectedColor = argb_color;
    }

    protected abstract void selectItem(View v, int itempos);

    public void finishActionMode() {
        multiSelect = false;
        selectedItems = new HashSet<>();
    }

    private boolean onSingleClick(View v, int position) {
        boolean result = callback.onItemClick(v, list.get(position));
        if (multiSelect)
            selectItem(v, position);
        return result;
    }

    private boolean onLongClick(View v, int position) {
        boolean result = callback.onItemLongClick(v, list.get(position));
        multiSelect = true;
        selectItem(v, position);
        return result;
    }

    @Override
    public boolean onClick(View v, boolean longclicked, int position) {
        return longclicked ? onLongClick(v, position) : onSingleClick(v, position);
    }
}

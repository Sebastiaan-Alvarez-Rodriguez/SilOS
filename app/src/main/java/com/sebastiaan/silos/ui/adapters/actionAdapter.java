package com.sebastiaan.silos.ui.adapters;

import android.view.View;

import com.sebastiaan.silos.ui.adapters.interfaces.actionCallback;
import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import androidx.annotation.Nullable;

public abstract class actionAdapter<T extends baseViewHolder<U>, U> extends baseAdapter<T, U> {
    private boolean multiSelect = false;

    protected HashSet<Integer> selectedItems = new HashSet<>();
    protected actionCallback actionCallback;

    protected int selectedColor;

    public actionAdapter(List<U> list, @Nullable clickCallback<U> callback, @Nullable actionCallback clickCallback) {
        super(list, callback);
        this.actionCallback = clickCallback;
    }

    public List<U> getSelected() {//TODO: test this
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

    @Override
    public boolean onClick(View v, boolean longclicked, int position) {
        return longclicked ? onLongClick(v, position) : onSingleClick(v, position);
    }

    private boolean onSingleClick(View v, int position) {
        if (clickCallback != null) {
            boolean result = clickCallback.onItemClick(v, list.get(position));
            if (multiSelect)
                selectItem(v, position);
            return result;
        }
        return false;
    }

    private boolean onLongClick(View v, int position) {
        if (clickCallback != null) {
            boolean result = clickCallback.onItemLongClick(v, list.get(position));
            multiSelect = true;
            selectItem(v, position);
            return result;
        }
        return false;
    }

}

package com.sebastiaan.silos.ui.adapters;

import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class checkableAdapter<T extends baseViewHolder<U>, U> extends baseAdapter<T, U> {
    private Set<U> selectedItems;

    public checkableAdapter(List<U> list, @Nullable clickCallback<U> clickCallback) {
        super(list, clickCallback);
        selectedItems = new HashSet<>();
    }

    public checkableAdapter(List<U> list, @Nullable clickCallback<U> clickCallback, Set<U> enabledList) {
        super(list, clickCallback);
        selectedItems = enabledList;
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public Set<U> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(Set<U> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public void addSelectedItem(U item) {
        selectedItems.add(item);
    }

    public void removeSelectedItem(U item) {
        selectedItems.remove(item);
    }

    public boolean containsSelectedItem(U item) {
        return selectedItems.contains(item);
    }
    
    protected void onCheckedChanged(U item, boolean isChecked) {
        if (isChecked)
            selectedItems.add(item);
        else
            selectedItems.remove(item);
    }

    @Override
    public void onBindViewHolder(@NonNull T viewholder, int position) {
        U ref = list.get(position);
        viewholder.set(ref);
    }
}

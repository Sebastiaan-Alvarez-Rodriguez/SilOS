package com.sebastiaan.silos.ui.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;
import com.sebastiaan.silos.ui.adapters.viewholders.viewHolderClickCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public abstract class baseAdapter<T extends baseViewHolder<U>, U> extends RecyclerView.Adapter<T> implements viewHolderClickCallback {
    protected List<U> list;
    protected clickCallback<U> clickCallback;

    public baseAdapter(List<U> list, @Nullable clickCallback<U> clickCallback) {
        this.list = list;
        this.clickCallback = clickCallback;
    }

    public void itemAdded(U item) {
        list.add(item);
        notifyItemInserted(list.size()-1);
    }

    public void itemOverriden(U item) {
        int found = list.indexOf(item);

        if (found == -1)
            throw new RuntimeException("overrided entity not found");
        list.set(found, item);

        notifyItemChanged(found);
    }

    @NonNull
    @Override
    public abstract T onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType);

    @Override
    public void onBindViewHolder(@NonNull T viewholder, int position) {
        U ref = list.get(position);
        viewholder.set(ref);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onClick(View v, boolean longclicked, int position) {
        return longclicked ? onLongClick(v, position) : onSingleClick(v, position);
    }

    private boolean onSingleClick(View v, int position) {
        if (clickCallback != null)
            return clickCallback.onItemClick(v, list.get(position));
        return false;
    }

    private boolean onLongClick(View v, int position) {
        if (clickCallback != null)
            return clickCallback.onItemLongClick(v, list.get(position));
        return false;
    }
}

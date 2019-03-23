package com.sebastiaan.silos.ui.adapters;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.sebastiaan.silos.ui.adapters.interfaces.clickCallback;
import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;
import com.sebastiaan.silos.ui.adapters.viewholders.viewHolderClickCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public abstract class baseAdapter<T extends baseViewHolder<U>, U> extends RecyclerView.Adapter<T> implements viewHolderClickCallback {
    protected List<U> list;
    protected clickCallback<U> clickCallback;

    public baseAdapter(List<U> list, @Nullable clickCallback<U> clickCallback) {
        this.list = list;
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public abstract T onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType);

    @SuppressWarnings("ConstantConditions")
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

    public Observer<List<U>> getObserver() {
        return l -> {
            Log.d("TESTTTTTT", "Observer geeft aan dat data veranderd is");
            list = l;
            notifyDataSetChanged();
        };
    }
}

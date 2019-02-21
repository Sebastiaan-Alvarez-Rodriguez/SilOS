package com.sebastiaan.silos.ui.adapters;

import android.view.ViewGroup;

import com.sebastiaan.silos.ui.adapters.viewholders.baseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class baseAdapter<T extends baseViewHolder<U>, U> extends RecyclerView.Adapter<T> {
    protected List<U> list;

    public baseAdapter(List<U> list) {
        this.list = list;
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
}

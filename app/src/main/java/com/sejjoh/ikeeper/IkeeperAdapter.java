package com.sejjoh.ikeeper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * created by joseph mulingwa kithome on
 */
public class IkeeperAdapter extends RecyclerView.Adapter<IkeeperAdapter.IkeepViewHolder> {
    List<iKeeperEntity> mEntityList = new ArrayList<>();
    private onItemClickListener mListener;

    @NonNull
    @Override
    public IkeeperAdapter.IkeepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ikeeper_item, parent, false);
        return new IkeepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IkeeperAdapter.IkeepViewHolder holder, int position) {
        iKeeperEntity currntiKeeper = mEntityList.get(position);
        holder.txtTitle.setText(currntiKeeper.getTitle());
        holder.txtDescription.setText(currntiKeeper.getDescription());
        holder.txtPriority.setText(String.valueOf(currntiKeeper.getPriority()));

    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    public void setIkeeper(List<iKeeperEntity> ikeeper) {
        this.mEntityList = ikeeper;
        notifyDataSetChanged();
        Log.d(TAG, "setIkeeper: new data...");
    }

    public iKeeperEntity getNoteAt(int position) {
        return mEntityList.get(position);
    }

    public class IkeepViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription, txtPriority;

        public IkeepViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.description);
            txtPriority = itemView.findViewById(R.id.tv_priority);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mEntityList.get(position));
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(iKeeperEntity iKeeperEntity);
    }

    public void setOnItemSelectedListener(onItemClickListener listener) {
        this.mListener = listener;
    }
}

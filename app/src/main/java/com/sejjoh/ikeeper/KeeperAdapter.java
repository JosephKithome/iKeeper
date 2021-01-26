package com.sejjoh.ikeeper;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * created by joseph mulingwa kithome on
 * 19.01.2021
 */
public class KeeperAdapter extends ListAdapter<KeeperEntity, KeeperAdapter.IkeepViewHolder> {
    private onItemClickListener mListener;

    protected KeeperAdapter() {
        super(DIFF_CALLBACK);
    }
    private  static  final DiffUtil.ItemCallback<KeeperEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<KeeperEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull KeeperEntity oldItem, @NonNull KeeperEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull KeeperEntity oldItem, @NonNull KeeperEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription())&&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public KeeperAdapter.IkeepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ikeeper_item, parent, false);
        return new IkeepViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull KeeperAdapter.IkeepViewHolder holder, int position) {
        KeeperEntity currntiKeeper = getItem(position);
        holder.txtTitle.setText(currntiKeeper.getTitle());
        holder.txtDescription.setText(currntiKeeper.getDescription());
        holder.txtPriority.setText(String.valueOf(currntiKeeper.getPriority()));

       int pos =getItem(position).getPriority();
        {
            if (pos <=5){
            holder.itemView.setBackgroundColor(R.color.black_900);
            }
        }
    }
    public KeeperEntity getNoteAt(int position) {
        return getItem(position);
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
                    mListener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(KeeperEntity KeeperEntity);
    }

    public void setOnItemSelectedListener(onItemClickListener listener) {
        this.mListener = listener;
    }
}

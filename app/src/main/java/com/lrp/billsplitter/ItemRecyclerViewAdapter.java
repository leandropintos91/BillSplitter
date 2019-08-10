package com.lrp.billsplitter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.lrp.billsplitter.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {

    public BillDetailActivity activity;
    List<Item> itemList;
    private int currentItem = 0;

    public ItemRecyclerViewAdapter(BillDetailActivity activity) {
        this.activity = activity;
        this.itemList = BillSplitterApp.getInstance().getItemListOfCurrentParticipant();
        this.currentItem = BillSplitterApp.getInstance().getCurrentItemSelectedIndex();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_view_holder, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.setClickListener(activity);
        holder.setPosition(position);
        holder.descriptionTextView.setText(itemList.get(position).getTitle());
        holder.priceTextView.setText(itemList.get(position).getPrice().toString());
        if(currentItem == position) {
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.selected_view_holder_background));
        } else {
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.view_holder_background));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public void updateItemsList() {
        itemList = BillSplitterApp.getInstance().getItemListOfCurrentParticipant();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        BillDetailActivity activity;

        int position;

        @BindView(R.id.item_name_text_view)
        TextView descriptionTextView;

        @BindView(R.id.item_price_text_view)
        TextView priceTextView;

        @BindView(R.id.item_view_holder_delete)
        ImageButton deleteButton;

        @BindView(R.id.item_view_holder_layout)
        LinearLayout layout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> activity.onItemClicked(position));
        }

        public void setClickListener(BillDetailActivity activity) {
            this.activity = activity;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @OnClick(R.id.item_view_holder_delete)
        public void onItemDeleteButtonClicked() {
            activity.onDeleteItem(position);
        }
    }
}


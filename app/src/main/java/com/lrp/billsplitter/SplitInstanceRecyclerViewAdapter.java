package com.lrp.billsplitter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lrp.billsplitter.model.SplitInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplitInstanceRecyclerViewAdapter extends RecyclerView.Adapter<SplitInstanceViewHolder> {

    private MainActivity activity;
    List<SplitInstance> instanceList;

    private final View.OnClickListener onClickListener = view -> {
        activity.onOpenInstance(view);
    };


    public SplitInstanceRecyclerViewAdapter(MainActivity activity, List<SplitInstance> instanceList) {
        this.instanceList = instanceList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SplitInstanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_instance_title_view_holder, parent, false);
        view.setOnClickListener(onClickListener);
        return new SplitInstanceViewHolder(activity, view);
    }

    @Override
    public void onBindViewHolder(@NonNull SplitInstanceViewHolder holder, int position) {
        holder.titleTextView.setText(instanceList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return instanceList.size();
    }
}

class SplitInstanceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.instance_name_text_view)
    TextView titleTextView;

    @BindView(R.id.instance_list_delete_button)
    ImageButton deleteButton;

    MainActivity activity;

    public SplitInstanceViewHolder(MainActivity activity, @NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.activity = activity;
    }

    @OnClick(R.id.instance_list_delete_button)
    public void onDeleteButtonClicked() {
        activity.onDeleteSplitInstance(getAdapterPosition());
    }
}

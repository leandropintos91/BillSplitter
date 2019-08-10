package com.lrp.billsplitter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.lrp.billsplitter.model.Participant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ParticipantRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantRecyclerViewAdapter.ParticipantViewHolder> {

    public BillDetailActivity activity;
    List<Participant> participantList;
    private int currentParticipant = 0;

    public ParticipantRecyclerViewAdapter(BillDetailActivity activity, List<Participant> participantList, int currentParticipant) {
        this.activity = activity;
        this.participantList = participantList;
        this.currentParticipant = currentParticipant;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_participant_view_holder, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        holder.setClickListener(activity);
        holder.setPosition(position);
        holder.nameTextView.setText(participantList.get(position).getName());
        if(currentParticipant == position) {
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.selected_view_holder_background));
        } else {
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.view_holder_background));
        }
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public void setCurrentParticipant(int currentParticipant) {
        this.currentParticipant = currentParticipant;
    }

    public static class ParticipantViewHolder extends RecyclerView.ViewHolder {

        BillDetailActivity activity;

        int position;

        @BindView(R.id.participant_name_text_view)
        TextView nameTextView;

        @BindView(R.id.participant_view_holder_layout)
        ConstraintLayout layout;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> activity.onParticipantClicked(position));
        }

        public void setClickListener(BillDetailActivity activity) {
            this.activity = activity;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @OnClick(R.id.participant_view_holder_delete_button)
        public void onDeleteParticipantButtonClicked() {
            activity.onDeleteParticipant(position);
        }
    }
}


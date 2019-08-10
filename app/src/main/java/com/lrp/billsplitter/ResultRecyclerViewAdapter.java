package com.lrp.billsplitter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lrp.billsplitter.model.SplitInstance;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.ParticipantResultViewHolder> {

    private SplitInstance splitInstance;
    private Activity activity;

    public ResultRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
        this.splitInstance = BillSplitterApp.getInstance().getCurrentSplitInstance();
    }

    @NonNull
    @Override
    public ParticipantResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_participant_result_view_holder, parent, false);
        return new ParticipantResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantResultViewHolder holder, int position) {
        holder.participantResultName.setText(splitInstance.getParticipantList().get(position).getName());
        String status;
        if(splitInstance.getParticipantList().get(position).getToPay() != 0) {
            status = activity.getString(R.string.to_pay, splitInstance.getParticipantList().get(position).getToPay());
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.background_to_pay));
        } else if (splitInstance.getParticipantList().get(position).getToReceive() != 0) {
            status = activity.getString(R.string.to_receive, splitInstance.getParticipantList().get(position).getToReceive());
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.background_to_receive));
        } else {
            status = " - ";
        }
        holder.participantResultStatus.setText(status);
    }

    @Override
    public int getItemCount() {
        return splitInstance.getParticipantList().size();
    }

    public class ParticipantResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.participant_result_name)
        TextView participantResultName;

        @BindView(R.id.participant_result_status)
        TextView participantResultStatus;

        @BindView(R.id.result_layout)
        LinearLayout layout;

        public ParticipantResultViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

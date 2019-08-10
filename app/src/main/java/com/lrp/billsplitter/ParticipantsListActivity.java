package com.lrp.billsplitter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lrp.billsplitter.model.Participant;

import org.apache.commons.collections4.CollectionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ParticipantsListActivity extends AppCompatActivity {

    @BindView(R.id.participant_list_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_list);
        ButterKnife.bind(this);
        BillSplitterApp.getInstance().setCurrentParticipantSelected(0);
        createRecyclerView();
    }

    private void createRecyclerView() {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        recyclerView.setAdapter(new ParticipantRecyclerViewAdapter(this,
//                BillSplitterApp.getInstance().getCurrentSplitInstance().getParticipantList(),
//                BillSplitterApp.getInstance().getCurrentParticipantSelectedIndex()));
    }

    @OnClick(R.id.button_new_participant)
    public void onNewParticipantClick() {
        Intent intent = new Intent(this, NewParticipantActivity.class);
        startActivityForResult(intent, Constants.NEW_PARTICIPANT_ACTIVITY_REQUEST_CODE);
    }

    @OnClick(R.id.run_calculation_button)
    public void onRunCalculationButtonClicked() {
        BillSplitterApp.getInstance().getCurrentSplitInstance().splitBill();
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    public void createNewParticipant(String name, Double paid) {
        if(BillSplitterApp.getInstance().getCurrentSplitInstance() != null && CollectionUtils.find(BillSplitterApp.getInstance().getCurrentSplitInstance().getParticipantList(),(object -> object.getName().equals(name))) == null) {
            Participant participant = new Participant();
            participant.setName(name);
            participant.setPaid(paid);
            BillSplitterApp.getInstance().getCurrentSplitInstance().getParticipantList().add(participant);

        } else {
            Toast.makeText(this,R.string.existing_instance_name_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.NEW_PARTICIPANT_ACTIVITY_REQUEST_CODE) {
            createNewParticipant((String)data.getExtras().get(Constants.NEW_PARTICIPANT_NAME)
            ,(Double)data.getExtras().get(Constants.NEW_PARTICIPANT_PAID));
        }
    }
}

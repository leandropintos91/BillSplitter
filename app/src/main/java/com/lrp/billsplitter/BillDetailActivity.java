package com.lrp.billsplitter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillDetailActivity extends AppCompatActivity {

    @BindView(R.id.participans_recycler_view)
    RecyclerView participantsRecyclerView;
    private ParticipantRecyclerViewAdapter participantRecyclerViewAdapter;

    @BindView(R.id.items_recycler_view)
    RecyclerView itemsRecyclerView;
    private ItemRecyclerViewAdapter itemsRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        ButterKnife.bind(this);
        createParticipantsRecyclerView();
        createItemsRecyclerView();
    }

    private void createItemsRecyclerView() {
        itemsRecyclerView.setHasFixedSize(false);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        itemsRecyclerViewAdapter = new ItemRecyclerViewAdapter(this);
        itemsRecyclerView.setAdapter(itemsRecyclerViewAdapter);
    }

    private void createParticipantsRecyclerView() {
        participantsRecyclerView.setHasFixedSize(false);
        participantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        participantRecyclerViewAdapter = new ParticipantRecyclerViewAdapter(this,
                BillSplitterApp.getInstance().getCurrentSplitInstance().getParticipantList(),
                BillSplitterApp.getInstance().getCurrentParticipantSelectedIndex());
        participantsRecyclerView.setAdapter(participantRecyclerViewAdapter);
    }

    @OnClick(R.id.bill_detail_new_participant_button)
    public void onNewParticipantButtonClicked() {
        Intent intent = new Intent(this, NewParticipantActivity.class);
        startActivityForResult(intent, Constants.NEW_PARTICIPANT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.NEW_PARTICIPANT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            BillSplitterApp.getInstance().newParticipant((String)data.getExtras().get(Constants.NEW_PARTICIPANT_NAME));
            participantRecyclerViewAdapter.setCurrentParticipant(BillSplitterApp.getInstance().getCurrentParticipantSelectedIndex());
            participantsRecyclerView.getAdapter().notifyDataSetChanged();
            itemsRecyclerViewAdapter.updateItemsList();
            itemsRecyclerViewAdapter.notifyDataSetChanged();
        } else if(requestCode == Constants.NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            BillSplitterApp.getInstance().addNewItem((String)data.getExtras().get(Constants.NEW_ITEM_DESCRIPTION),
                    data.getExtras().getDouble(Constants.NEW_ITEM_PRICE));
            itemsRecyclerViewAdapter.updateItemsList();
            itemsRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public void onParticipantClicked(int position) {
        BillSplitterApp.getInstance().setCurrentParticipantSelected(position);
        participantRecyclerViewAdapter.setCurrentParticipant(BillSplitterApp.getInstance().getCurrentParticipantSelectedIndex());
        participantsRecyclerView.getAdapter().notifyDataSetChanged();
        itemsRecyclerViewAdapter.updateItemsList();
        itemsRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @OnClick(R.id.bill_detail_new_item_button)
    public void onNewItemButtonClicked() {
        Intent intent = new Intent(this, NewItemActivity.class);
        startActivityForResult(intent, Constants.NEW_ITEM_ACTIVITY_REQUEST_CODE);
    }

    public void onItemClicked(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_instance_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calculate_menu_option:
                showResult();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showResult() {
        BillSplitterApp.getInstance().splitBill();
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    public void onDeleteItem(int position) {
        BillSplitterApp.getInstance().deleteItem(position);
        itemsRecyclerViewAdapter.updateItemsList();
        itemsRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void onDeleteParticipant(int position) {
        BillSplitterApp.getInstance().deleteParticipant(position);

        participantRecyclerViewAdapter.setCurrentParticipant(BillSplitterApp.getInstance().getCurrentParticipantSelectedIndex());
        participantsRecyclerView.getAdapter().notifyDataSetChanged();
        itemsRecyclerViewAdapter.updateItemsList();
        itemsRecyclerViewAdapter.notifyDataSetChanged();
    }
}

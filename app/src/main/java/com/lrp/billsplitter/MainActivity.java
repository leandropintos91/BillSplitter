package com.lrp.billsplitter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.collections4.CollectionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.button_new_instance)
    Button buttonNewInstance;

    @BindView(R.id.button_delete_instance)
    Button buttonDeleteInstance;

    @BindView(R.id.instance_list_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createRecyclerView();
    }

    private void createRecyclerView() {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SplitInstanceRecyclerViewAdapter(this, BillSplitterApp.getInstance().getSplitInstanceList()));
    }

    @OnClick(R.id.button_new_instance)
    public void onToastButtonClick() {
        DialogFactory.newInstanceNameDialog(this).show();
    }

    public void onNewInstance(String instanceName) {

        if(CollectionUtils.find(BillSplitterApp.getInstance().getSplitInstanceList(), object -> object.getTitle().equals(instanceName)) == null) {
            //TODO refactorizar esta chanchada para acceder desde un solo lugar
            BillSplitterApp.getInstance().newSplitInstance(instanceName);
            goToDetails();
        } else {
            Toast.makeText(this,R.string.existing_instance_name_error, Toast.LENGTH_LONG).show();
        }

    }

    private void goToDetails() {
        Intent intent = new Intent(this, BillDetailActivity.class);
        this.startActivity(intent);
    }

    public void onDeleteSplitInstance(int position) {
        BillSplitterApp.getInstance().deleteSplitInstance(position);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void onOpenInstance(View view) {
        BillSplitterApp.getInstance().setCurrentSplitInstance(recyclerView.getChildLayoutPosition(view));
        goToDetails();
    }
}

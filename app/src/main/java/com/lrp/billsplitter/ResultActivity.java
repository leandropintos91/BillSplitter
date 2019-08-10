package com.lrp.billsplitter;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.result_recycler_view)
    RecyclerView resultRecyclerView;

    @BindView(R.id.result_total_text_view)
    TextView totalTextView;

    @BindView(R.id.result_total_per_capita_text_view)
    TextView totalPerCapitaTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        createRecyclerView();
        totalTextView.setText(getString(R.string.total, BillSplitterApp.getInstance().getCurrentSplitInstance().getTotal()));
        totalPerCapitaTextView.setText(getString(R.string.total_per_capita, BillSplitterApp.getInstance().getCurrentSplitInstance().getTotalPerCapita()));
    }

    private void createRecyclerView() {
        resultRecyclerView.setHasFixedSize(true);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        resultRecyclerView.setAdapter(new ResultRecyclerViewAdapter(this));
    }
}

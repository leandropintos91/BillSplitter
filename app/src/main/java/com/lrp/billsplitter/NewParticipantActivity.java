package com.lrp.billsplitter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewParticipantActivity extends AppCompatActivity {

    @BindView(R.id.new_participant_name)
    EditText nameEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_participant);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.new_participant_button)
    public void onNewParticipantButtonClick() {
        //TODO comprobar acá que no me manden fruta
        String newName = nameEditText.getText().toString();
        if(newName != null && !newName.isEmpty()) {
            Intent intent = new Intent(this, ParticipantsListActivity.class);
            intent.putExtra(Constants.NEW_PARTICIPANT_NAME, newName);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            new AlertDialog.Builder(this).setTitle(R.string.must_fill_form).create().show();
        }
    }
}

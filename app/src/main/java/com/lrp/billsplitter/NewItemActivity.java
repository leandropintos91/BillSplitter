package com.lrp.billsplitter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewItemActivity extends AppCompatActivity {

    @BindView(R.id.new_item_name)
    EditText descriptionEditText;

    @BindView(R.id.new_item_price)
    EditText priceEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.new_item_button)
    public void onNewParticipantButtonClick() {
        //TODO comprobar acá que no me manden fruta
        String newName = descriptionEditText.getText().toString();
        String newPrice = priceEditText.getText().toString();

        if(newName != null && !newName.isEmpty() && newPrice != null && !newPrice.isEmpty()) {
            Intent intent = new Intent(this, BillDetailActivity.class);
            intent.putExtra(Constants.NEW_ITEM_DESCRIPTION, newName);
            intent.putExtra(Constants.NEW_ITEM_PRICE, Double.parseDouble(newPrice));
            setResult(RESULT_OK, intent);
            finish();
        } else {
            new AlertDialog.Builder(this).setTitle(R.string.must_fill_form).create().show();
        }

    }
}

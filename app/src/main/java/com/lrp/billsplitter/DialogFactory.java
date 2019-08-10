package com.lrp.billsplitter;

import android.app.AlertDialog;
import android.widget.EditText;

public class DialogFactory {

    public static AlertDialog newInstanceNameDialog(MainActivity context) {
        EditText editText = new EditText(context);
        return new AlertDialog.Builder(context)
                .setTitle(R.string.new_instance_name_title)
                .setView(editText)
                .setPositiveButton(R.string.create, (dialogInterface, i) -> {
                    context.onNewInstance(editText.getText().toString());
                    dialogInterface.dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
    }

//    public static DialogInterface newParticipantDialog(ParticipantsListActivity participantsListActivity) {
//        AlertDialog dialogBuilder = new AlertDialog.Builder(participantsListActivity)
//                .setView(participantsListActivity.getLayoutInflater().inflate(R.layout.activity_new_item, null))
//                .setPositiveButton(R.string.create, ((dialogInterface, i) -> {
//                    dialogInterface.;
//                }))
//                .setNegativeButton(R.string.cancel, ((dialogInterface, i) -> dialogInterface.dismiss()));
//        dialogBuilder.create();
//    }
}

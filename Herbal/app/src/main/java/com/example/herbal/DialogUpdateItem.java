package com.example.herbal;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Алатиэль on 18.08.2017.
 */

public class DialogUpdateItem extends DialogFragment implements DialogInterface.OnClickListener {


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("изменить").setPositiveButton("yes", this)
                .setNegativeButton("no", this)
                .setNeutralButton("mb", this)
                .setMessage("текст сообщения");
        return adb.create();
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        int i = 0;
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                i = R.string.yes;
                break;
            case Dialog.BUTTON_NEGATIVE:
                i = R.string.no;
                break;
            case Dialog.BUTTON_NEUTRAL:
                i = R.string.maybe;
                break;
        }
        if (i > 0)
            Log.d("mLog", "Dialog 2: " + getResources().getString(i));
    }
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("mLog", "Dialog 2: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("mLog", "Dialog 2: onCancel");
    }
}

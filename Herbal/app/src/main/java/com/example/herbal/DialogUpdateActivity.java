package com.example.herbal;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class DialogUpdateActivity extends DialogFragment implements View.OnClickListener {

    String myInt;
    Intent toInfoClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_update_activity, null);
        v.findViewById(R.id.btnYes).setOnClickListener(this);
        v.findViewById(R.id.btnNo).setOnClickListener(this);

        toInfoClass = new Intent(getActivity(), ThemListActivity.class);

        return v;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNo:
                break;
            case R.id.btnYes:
                Update();
                break;
        }
        dismiss();
    }
    private void Update()
    {
        toInfoClass.putExtra("adapterMessage","nanananananana");
        startActivity(toInfoClass);
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("mLog", "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("mLog", "Dialog 1: onCancel");
    }
}

package com.example.herbal;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Toast;

public class DialogUpdateActivity extends DialogFragment implements View.OnClickListener {

    public interface onSomeEventListener {
        public void someEvent(String s);
    }

    onSomeEventListener someEventListener;


    private EditText inputTheme;
    Intent toInfoClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_update_activity, null);
        v.findViewById(R.id.btnYes).setOnClickListener(this);
        v.findViewById(R.id.btnNo).setOnClickListener(this);

        inputTheme = (EditText) v.findViewById(R.id.editTextNewTheme);
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    private void Update()
    {
        String name = inputTheme.getText().toString();
        Log.d("mLog", "name string = '" + name + "'");
        if (!name.equals("") && name != null){
            /*toInfoClass.putExtra("adapterMessage", name);
            startActivity(toInfoClass);*/
            someEventListener.someEvent(name);
        }else{
            Toast.makeText(getActivity(), "поле не заполнено", Toast.LENGTH_SHORT).show();
        }
       
        
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

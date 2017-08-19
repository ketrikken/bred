package com.example.herbal;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Алатиэль on 19.08.2017.
 */

public class DialogCreateActivity extends DialogFragment implements View.OnClickListener  {


    public interface onCreateItemEventListener {
        public void createItem(String s);
    }

    onCreateItemEventListener someEventListener;

    private EditText inputTheme;
    Intent toInfoClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Сохранить?");
        View v = inflater.inflate(R.layout.dialog_add_new_theme, null);
        v.findViewById(R.id.btnYesCreate).setOnClickListener(this);
        v.findViewById(R.id.btnCancel).setOnClickListener(this);

        inputTheme = (EditText) v.findViewById(R.id.editTextAddNewTheme);
        toInfoClass = new Intent(getActivity(), ThemListActivity.class);

        return v;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onCreateItemEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancel:
                break;
            case R.id.btnYesCreate:
                Update();
                break;
        }
        dismiss();
    }
    private void Update()
    {
        String name = inputTheme.getText().toString();
        if (!name.equals("") && name != null){
            someEventListener.createItem(name);
        }else{
            Toast.makeText(getActivity(), "поле не заполнено", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}

package com.example.mauri_r95.mimascota10.Vista;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mauri_r95.mimascota10.R;

/**
 * Created by Mauri_R95 on 09-12-2017.
 */

public class DialogContactoMas extends DialogFragment  {

    private TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setCancelable(false);
        View dialog = inflater.inflate(R.layout.dialog_contacto_mas, null);
        textView = (TextView)dialog.findViewById(R.id.textView3);
        getDialog().setTitle("Informacion del dueño");
        return dialog;
    }


    public void show(FragmentManager manager, String contacto) {
    }
}

package com.example.mauri_r95.mimascota10;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Mauri_R95 on 04-10-2017.
 * clase que carga la informacion del dueño de la mascota
 *
 * @author Mauri_R95
 * @since 1.0
 * @version 1.1 Cambios hechos
 */



public class NumeroFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numero, null);
        setCancelable(false);

        return view;
    }

    public void show(FragmentManager manager, String numero) {
    }
}

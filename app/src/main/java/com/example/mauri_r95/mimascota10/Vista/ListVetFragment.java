package com.example.mauri_r95.mimascota10.Vista;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mauri_r95.mimascota10.AdapterVet;
import com.example.mauri_r95.mimascota10.FirebaseReference;
import com.example.mauri_r95.mimascota10.Modelo.Ubicacion;
import com.example.mauri_r95.mimascota10.Modelo.Veterinario;
import com.example.mauri_r95.mimascota10.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListVetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListVetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListVetFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Ubicacion ub;
    RecyclerView recyclerView;
    List<Veterinario> veterinarios;
    AdapterVet adapterVet;
    LatLng here;


    private FirebaseDatabase database;
    private DatabaseReference reference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListVetFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListVetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListVetFragment newInstance(String param1, String param2) {
        ListVetFragment fragment = new ListVetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View viewListVet = inflater.inflate(R.layout.fragment_list_vet, container, false);
        ub = new Ubicacion(getContext());
        recyclerView = (RecyclerView)viewListVet.findViewById(R.id.recycler_vet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        veterinarios = new ArrayList<>();
        adapterVet = new AdapterVet(veterinarios);
        recyclerView.setAdapter(adapterVet);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        here = new LatLng(ub.getLatitude(), ub.getLongitude());
        //here = new LatLng(-33.021469,-71.48585800000001 );
        reference.child(FirebaseReference.ref_veterinarios).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                veterinarios.removeAll(veterinarios);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Veterinario veterinario = ds.getValue(Veterinario.class);
                    LatLng puntoVet = new LatLng(Double.parseDouble(veterinario.getLat()), Double.parseDouble(veterinario.getLng()));
                    veterinario.setDistancia(calcularDis(here, puntoVet));
                    if(veterinario.getDistancia() < 50000) {
                        veterinarios.add(veterinario);
                    }
                }
                Collections.sort(veterinarios);
                adapterVet.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapterVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Veterinario veterinario = veterinarios.get(recyclerView.getChildAdapterPosition(v));
                Intent intent = new Intent(getActivity(), InfoVetActivity.class);
                intent.putExtra("vet", veterinario);
                getActivity().startActivity(intent);
            }
        });

        return viewListVet;
    }

    public  int calcularDis(LatLng here, LatLng other ){
        Location hereL = new Location("here");
        hereL.setLatitude(here.latitude);
        hereL.setLongitude(here.longitude);
        Location otherL = new Location("other");
        otherL.setLatitude(other.latitude);
        otherL.setLongitude(other.longitude);
        Float distance = hereL.distanceTo(otherL);
        int dis = Math.round(distance);
        return dis;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

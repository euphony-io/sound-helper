package com.eutophia.sound_helper;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragment extends Fragment {
    InfoActivity infoActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        infoActivity = (InfoActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        infoActivity = null;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_page,container,false);

        return rootView;
    }
}
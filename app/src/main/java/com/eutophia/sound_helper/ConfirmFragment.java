package com.eutophia.sound_helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ConfirmFragment extends Fragment {
    private Button confirmBtn;
    private String entireInfo = "";
    Fragment mainFragment;
    private Person person;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainFragment = (PageFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_main);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainFragment = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_confirm,container,false);
        confirmBtn = (Button)rootView.findViewById(R.id.confirm);

        return rootView;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                InfoViewModel viewModel = new ViewModelProvider(requireActivity()).get(InfoViewModel.class);

                viewModel.getCurrentInfo().observe(getViewLifecycleOwner(), p -> {
                    person = p; });
                entireInfo += person.getName() + person.getTel() + person.getBirthOfDate() + person.getDiseaseName();
                alert.setTitle("Do you want to submit it?").setMessage("\n" + entireInfo);
                alert.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getParentFragment().getActivity(), TransmitActivity.class);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        entireInfo = "";
                    }
                });
                alert.show();
            }
        });
    }
}
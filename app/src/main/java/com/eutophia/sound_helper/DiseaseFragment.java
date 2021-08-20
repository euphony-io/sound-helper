package com.eutophia.sound_helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class DiseaseFragment extends Fragment {
    final boolean IN = true;
    final boolean OTHER = false;
    Fragment mainFragment;
    private Spinner spinner;
    final CharSequence[] disease_list = {
            "Diabetes", "Asthma", "Dementia", "Visual impairment", "Hearing impairment", "None", "Other"
    };
    ArrayAdapter<CharSequence> list;
    private String diseaseInfo = "";
    private Person person = new Person();
    private EditText writeDisease;

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

    public void showAlert(String title, String message, EditText text,
                          boolean isIn, InfoViewModel model){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle(title);
        if(isIn)
            alert.setMessage(message);
        else
            alert.setView(text);
        alert.setPositiveButton("enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface,int i){
                if(isIn)
                    diseaseInfo += "chronic disease: " + message + "\n";
                else
                    diseaseInfo += "chronic disease: " + text.getText().toString() + "\n";
                model.getCurrentInfo().observe(getViewLifecycleOwner(), p -> {
                    person = p;
                });
                person.setDiseaseName(diseaseInfo);
                model.setCurrentInfo(person);
            }
        });
        alert.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_disease,container,false);
        list = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, disease_list);
        spinner = (Spinner) rootView.findViewById(R.id.sp);
        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(list);
        spinner.setSelection(0, false);
        return rootView;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        InfoViewModel viewModel = new ViewModelProvider(requireActivity()).get(InfoViewModel.class);
        super.onViewCreated(view, savedInstanceState);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diseaseInfo = "";
                if (disease_list[position].toString().equals("Other")) {
                    writeDisease = new EditText(getActivity());
                    writeDisease.setHeight(200);
                    showAlert(getString(R.string.askDisease), "", writeDisease, OTHER, viewModel);
                }
                else
                    showAlert(getString(R.string.isRightDisease), "\n" + disease_list[position].toString(), writeDisease, IN, viewModel);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });
    }
}
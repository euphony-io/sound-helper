package com.eutophia.sound_helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class InfoFragment extends Fragment {
    final String NAME = "NAME";
    final String TEL = "TEL";
    PageFragment pageFragment;
    InfoViewModel viewModel;
    private EditText name, tel;
    private Button btn1, btn2;
    String nameInfo = "", telInfo = "";
    Person person = new Person();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pageFragment = (PageFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_main);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        pageFragment = null;
    }

    public void showAlert(String title, String message, String type){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(title).setMessage(message);

        alert.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(type == "NAME"){
                    nameInfo = "";
                    nameInfo += "Name: " + name.getText().toString() + "\n";
                    person.setName(nameInfo);
                }
                else{
                    telInfo = "";
                    telInfo += "Emergency contact number: " + tel.getText().toString() + "\n";
                    person.setTel(telInfo);
                    viewModel.setCurrentInfo(person);
                }
            }
        });
        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(type == "NAME"){
                    nameInfo = "";
                    name.setText("");
                }
                else{
                    telInfo = "";
                    tel.setText("");
                }
            }
        });
        alert.show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_info,container,false);
        viewModel = new ViewModelProvider(requireActivity()).get(InfoViewModel.class);

        name = (EditText)rootView.findViewById(R.id.name_editText);
        tel = (EditText)rootView.findViewById(R.id.tel_editText);
        btn1 = (Button) rootView.findViewById(R.id.confirm_name_button);
        btn2 = (Button) rootView.findViewById(R.id.confirm_tel_button);

        return rootView;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(InfoViewModel.class);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert(getString(R.string.checkName), "\n" + name.getText().toString(), NAME);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert(getString(R.string.checkTel), "\n" + tel.getText().toString(), TEL);
            }
        });
    }
}
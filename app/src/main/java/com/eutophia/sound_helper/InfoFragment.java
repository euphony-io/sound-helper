package com.eutophia.sound_helper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class InfoFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    InfoActivity infoActivity;

    private TextView birth;
    private EditText disease, name, tel;
    private Button dateBtn, confirmBtn;
    private Spinner spinner;

    Calendar calendar = Calendar.getInstance();

    private String nameInfo = "", telInfo = "", dateInfo = "", diseaseInfo = "", entireInfo = "";
    private Person person = new Person();
    final CharSequence[] disease_list = {
            "Diabetes", "Asthma", "Dementia", "Visual impairment", "Hearing impairment", "None", "Other"
    };
    ArrayAdapter<CharSequence> list;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_info,container,false);
        setUI(rootView);

        return rootView;
    }

    private void setUI(View rootView){
        dateBtn = (Button)rootView.findViewById(R.id.birth_button);
        confirmBtn = (Button)rootView.findViewById(R.id.confirm_button);
        name = (EditText)rootView.findViewById(R.id.name_editText);
        tel = (EditText)rootView.findViewById(R.id.tel_editText);
        birth = (TextView) rootView.findViewById(R.id.dateOfBirth_textView);
        spinner = (Spinner) rootView.findViewById(R.id.select_disease_spinner);

        list = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, disease_list);
        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dateBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);

        spinner.setAdapter(list);
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(this);
    }
    public void setInfo(String type){
        if(type.equals("name") == true){
            nameInfo = "" + name.getText().toString();
            person.setName(nameInfo);
        }
        else if(type.equals("tel") == true){
            telInfo = "";
            telInfo = "" + tel.getText().toString();
            person.setTel(telInfo);
        }
        else
            return;
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.birth_button) {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    dateInfo = "" + (month + 1) + "/" + dayOfMonth + "/" + year;
                    birth.setText((month + 1) + "/" + dayOfMonth + "/" + year);

                    person.setBirthOfDate(dateInfo);
                }
            };
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
        else if(view.getId() == R.id.confirm_button){
            setInfo("name");
            setInfo("tel");

            showAlert(confirmBtn);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        diseaseInfo = "";
        if (disease_list[position].toString().equals("Other")) {
            disease = new EditText(getActivity());
            disease.setHeight(200);
            showAlert(spinner);
        }
        else
            diseaseInfo = "" + disease_list[position].toString();
        person.setDiseaseName(diseaseInfo);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent){
    }

    public void showAlert(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        if(view.getId() == R.id.select_disease_spinner) {
            alert.setTitle(getString(R.string.askDisease));
            alert.setView(disease);
            alert.setPositiveButton("enter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    diseaseInfo += "chronic disease: " + disease.getText().toString() + "\n";
                    person.setDiseaseName(diseaseInfo);
                }
            });
        }
        else{
            entireInfo = "Name : " + person.getName() +  "\nTel : " + person.getTel() +
                    "\nBirth : " + person.getBirthOfDate() + "\nDisease : " + person.getDiseaseName();
            alert.setTitle(getString(R.string.submit)).setMessage("\n" + entireInfo);
            alert.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 데이터 입력 완료된 부분
                    Intent intent = new Intent(getActivity(), TransmitActivity.class);
                    startActivity(intent);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Person");
                    myRef.setValue(person);

                    getActivity().finish();
                }
            });
            alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    entireInfo = "";
                }
            });
        }
        alert.show();
    }
}
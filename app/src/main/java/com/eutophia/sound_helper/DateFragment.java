package com.eutophia.sound_helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DateFragment extends Fragment {
    private String dateInfo = "";
    private TextView birth;
    DatePickerDialog.OnDateSetListener date;
    Calendar calendar = Calendar.getInstance();
    private Button dateBtn;
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


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_date,container,false);
        dateBtn = (Button)rootView.findViewById(R.id.editBirth);
        birth = (TextView) rootView.findViewById(R.id.dateOfBirth);

        return rootView;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InfoViewModel viewModel = new ViewModelProvider(requireActivity()).get(InfoViewModel.class);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth){
                        dateInfo = "";
                        dateInfo += "Date of Birth: " + (month + 1) + "/" + dayOfMonth + "/" + year + "\n";
                        birth.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                        viewModel.getCurrentInfo().observe(getViewLifecycleOwner(), p ->
                        { person = p; });
                        person.setBirthOfDate(dateInfo);
                        viewModel.setCurrentInfo(person);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }
}
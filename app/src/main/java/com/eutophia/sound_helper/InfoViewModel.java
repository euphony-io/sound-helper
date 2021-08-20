package com.eutophia.sound_helper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InfoViewModel extends ViewModel {
    private MutableLiveData<Person> currentInfo = new MutableLiveData<>();

    public void setCurrentInfo(Person info){
        currentInfo.setValue(info);
    }

    public MutableLiveData<Person> getCurrentInfo() {
        if (currentInfo == null) {
            currentInfo = new MutableLiveData<Person>();
        }
        return currentInfo;
    }
}

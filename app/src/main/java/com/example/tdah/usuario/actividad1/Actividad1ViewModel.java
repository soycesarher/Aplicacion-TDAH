package com.example.tdah.usuario.actividad1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Actividad1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Actividad1ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
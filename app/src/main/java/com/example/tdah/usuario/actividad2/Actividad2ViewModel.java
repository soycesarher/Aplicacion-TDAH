package com.example.tdah.usuario.actividad2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Actividad2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Actividad2ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
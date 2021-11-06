package com.example.tdah.usuario.actividad3;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class Actividad3ViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public Actividad3ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

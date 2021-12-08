package com.example.tdah.psicologo.ayudaPsicologo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AyudaPsicologoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AyudaPsicologoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ayuda");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
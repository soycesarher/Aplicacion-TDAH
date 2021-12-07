package com.example.tdah.psicologo.ayudaPsicologo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ayudaPsicologoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ayudaPsicologoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ayuda");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
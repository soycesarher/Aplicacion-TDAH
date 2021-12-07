package com.example.tdah.psicologo.cuentaPsicologo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class cuentaPsicologoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public cuentaPsicologoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Cuenta");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.example.tdah.psicologo.cuentaPsicologo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CuentaPsicologoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CuentaPsicologoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Cuenta");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
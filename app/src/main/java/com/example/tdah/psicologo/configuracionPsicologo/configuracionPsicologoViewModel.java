package com.example.tdah.psicologo.configuracionPsicologo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class configuracionPsicologoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public configuracionPsicologoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Configuracion");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
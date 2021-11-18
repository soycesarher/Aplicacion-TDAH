package com.example.tdah.psicologo.configuracionPsicologo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class configuracionPsicologoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public configuracionPsicologoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
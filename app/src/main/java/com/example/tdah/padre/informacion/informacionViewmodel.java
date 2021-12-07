package com.example.tdah.padre.informacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class informacionViewmodel extends ViewModel {
    private MutableLiveData<String> mText;

    public informacionViewmodel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

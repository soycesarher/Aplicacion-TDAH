/**
 *Clase: Vista modal de la actividad 2.
 *
 * @author: TDAH Móvil
 */
package com.example.tdah.usuario.actividad2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Actividad2ViewModel extends ViewModel {

    private MutableLiveData<String> mutablelivedata_mText;

    /**
     * Contructor que inicializa el modelo.
     */
    public Actividad2ViewModel() {
        mutablelivedata_mText = new MutableLiveData<>();
        mutablelivedata_mText.setValue("This is slideshow fragment");
    }

    /**
     * Retorna arreglo de propiedades del modelo de la actividad.
     * @return Retorna arreglo de propiedades del modelo de la actividad.
     */
    public LiveData<String> getText()
    {
        return mutablelivedata_mText;
    }
}
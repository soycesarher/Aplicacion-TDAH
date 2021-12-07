package com.example.tdah.padre.informacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;

public class informacionFragment extends Fragment {

    private informacionViewmodel informacion_viewmodel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informacion_viewmodel =
                new ViewModelProvider(this).get(informacionViewmodel.class);
        View root = inflater.inflate(R.layout.fragment_informacion, container,false);
        return root;
    }

}

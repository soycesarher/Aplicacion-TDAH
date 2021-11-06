package com.example.tdah.usuario.actividad3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;

public class Actividad3Fragment extends Fragment{

    private Actividad3ViewModel Actividad3ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Actividad3ViewModel =
                new ViewModelProvider(this).get(Actividad3ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividad2, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        Actividad3ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}

package com.example.tdah.usuario.actividad2;

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

public class Actividad2Fragment extends Fragment {

    private Actividad2ViewModel Actividad2ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Actividad2ViewModel =
                new ViewModelProvider(this).get(Actividad2ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividad2, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        Actividad2ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
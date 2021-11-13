package com.example.tdah.usuario.actividad1;

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

public class Actividad1Fragment extends Fragment {

    private Actividad1ViewModel Actividad1ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Actividad1ViewModel =
                new ViewModelProvider(this).get(Actividad1ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividad1, container, false);
        final TextView textView = root.findViewById(R.id.text_actividad1);
        Actividad1ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                textView.setText(s);
            }
        });
        return root;
    }
}
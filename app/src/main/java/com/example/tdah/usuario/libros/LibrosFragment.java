package com.example.tdah.usuario.libros;

import androidx.fragment.app.Fragment;
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
import com.example.tdah.usuario.cuenta.CuentaViewModel;


public class LibrosFragment extends Fragment {

    private LibrosViewModel librosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        librosViewModel =
                new ViewModelProvider(this).get(LibrosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_libros, container, false);
        final TextView textView = root.findViewById(R.id.text_libros);
        librosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}

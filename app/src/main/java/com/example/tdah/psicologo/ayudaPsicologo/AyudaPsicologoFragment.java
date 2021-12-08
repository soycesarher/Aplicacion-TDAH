package com.example.tdah.psicologo.ayudaPsicologo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.R;
import com.example.tdah.databinding.FragmentAyudaPsicologoBinding;

public class AyudaPsicologoFragment extends Fragment {

    private AyudaPsicologoViewModel ayudaPsicologoViewModel;
    private FragmentAyudaPsicologoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ayudaPsicologoViewModel =
                new ViewModelProvider(this).get(AyudaPsicologoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ayuda_psicologo, container,false);
        ayudaPsicologoViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
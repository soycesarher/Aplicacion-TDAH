package com.example.tdah.psicologo.cuentaPsicologo;

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

import com.example.tdah.databinding.FragmentCuentaPsicologoBinding;
import com.example.tdah.databinding.FragmentCuentaPsicologoBinding;

public class cuentaPsicologoFragment extends Fragment {

    private cuentaPsicologoViewModel cuentaPsicologoViewModel;
    private FragmentCuentaPsicologoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cuentaPsicologoViewModel =
                new ViewModelProvider(this).get(cuentaPsicologoViewModel.class);

        binding = FragmentCuentaPsicologoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.txtNombrePsicologo;

        cuentaPsicologoViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
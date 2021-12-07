package com.example.tdah.psicologo.cuentaPsicologo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.databinding.FragmentCuentaPsicologoBinding;

public class cuentaPsicologoFragment extends Fragment {
    private EditText txt_nombre_psicologo_cuenta, txt_apellido_paterno_psicologo_cuenta,
                    txt_apellido_materno_psicologo_cuenta, txt_direccion_consultorio_psicologo_cuenta,
                    txt_correo_psicologo_cuenta,txt_telefono_psicologo_cuenta,txt_cedula_psicologo_cuenta;
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
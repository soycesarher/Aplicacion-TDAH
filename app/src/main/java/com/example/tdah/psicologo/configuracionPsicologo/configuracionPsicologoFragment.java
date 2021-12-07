package com.example.tdah.psicologo.configuracionPsicologo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.MainActivity;
import com.example.tdah.R;
import com.example.tdah.databinding.FragmentConfiguracionPsicologoBinding;
import com.google.firebase.auth.FirebaseAuth;

public class configuracionPsicologoFragment extends Fragment {
    private com.example.tdah.usuario.ajustes.AjustesViewModel AjustesViewModel;
    private Button btn_cerrar_sesion;
    private FirebaseAuth mAuth;
    private configuracionPsicologoViewModel configuracionPsicologoViewModel;
    private FragmentConfiguracionPsicologoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        configuracionPsicologoViewModel =
                new ViewModelProvider(this).get(configuracionPsicologoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_configuracion_psicologo, container,false);

        mAuth = FirebaseAuth.getInstance();
        btn_cerrar_sesion = (Button) root.findViewById(R.id.btn_cerrar_sesion_psico);

        configuracionPsicologoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        btn_cerrar_sesion.setOnClickListener(v -> {
            mAuth.signOut();
            ir_a_main(inflater,container);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void ir_a_main(LayoutInflater inflater, ViewGroup container) {
        AjustesViewModel =
                new ViewModelProvider(this).get(com.example.tdah.usuario.ajustes.AjustesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);
        startActivity(new Intent(root.getContext(), MainActivity.class));
        requireActivity().finish();
    }
}
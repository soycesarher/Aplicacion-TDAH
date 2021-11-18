package com.example.tdah.psicologo.configuracionPsicologo;

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

import com.example.tdah.databinding.FragmentConfiguracionPsicologoBinding;

public class configuracionPsicologoFragment extends Fragment {

    private configuracionPsicologoViewModel configuracionPsicologoViewModel;
    private FragmentConfiguracionPsicologoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        configuracionPsicologoViewModel =
                new ViewModelProvider(this).get(configuracionPsicologoViewModel.class);

        binding = FragmentConfiguracionPsicologoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textView4;
        configuracionPsicologoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
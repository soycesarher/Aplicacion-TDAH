package com.example.tdah.padre.psicologos;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.tdah.ListaPsicologos;

public class psicologosFragment extends Fragment {
    private psicologosViewModel psicologos_view_model;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        startActivity(new Intent(getContext(), ListaPsicologos.class));
        getActivity().finish();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}

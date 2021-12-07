package com.example.tdah.padre.mapas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.tdah.MapaCentros;

public class mapasFragment extends Fragment {
    private mapasViewModel mapasviewModel;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        startActivity(new Intent(getContext(), MapaCentros.class));
        getActivity().finish();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}

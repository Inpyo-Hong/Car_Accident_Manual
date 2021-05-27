package com.cookandroid.mobile_proj_honginpyo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag6 extends Fragment {
    private View view;

    public static Frag6 newinstance(){
        Frag6 frag6 = new Frag6();
        return frag6;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.frag_6, container, false);

       return view;
    }
}

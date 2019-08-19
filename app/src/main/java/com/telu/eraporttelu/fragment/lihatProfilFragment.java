package com.telu.eraporttelu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class lihatProfilFragment extends Fragment {

    private static final String TAG = "lihatProfilFragment";
    private LinearLayout layoutGantiUsername, layoutGantuPassword;
    private TextView textGantiUsername, textGantiPassword;
    private static Context context;


    public lihatProfilFragment() {

    }

    public static lihatProfilFragment newInstance(Context c){
        lihatProfilFragment fragment = new lihatProfilFragment();
        context = c;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

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
import android.widget.Toast;

import com.telu.eraporttelu.R;

public class pengaturanFragment extends Fragment {

    private static final String TAG = "pengaturanFragment";
    private static Context context;
    private TextView textGantiUsername, textGantiPassword;
    private LinearLayout layoutGantiUsername, layoutGantiPassword;

    public pengaturanFragment() {

    }

    public static pengaturanFragment newInstance(Context c){
        pengaturanFragment fragment = new pengaturanFragment();
        context = c;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pengaturanView = inflater.inflate(R.layout.fragment_pengaturan, container, false);

        layoutGantiUsername = pengaturanView.findViewById(R.id.layout_pengaturan_gantiUsername);
        textGantiUsername = pengaturanView.findViewById(R.id.text_pengaturan_gantiUsername);
        layoutGantiPassword = pengaturanView.findViewById(R.id.layout_pengaturan_gantiPassword);
        textGantiPassword = pengaturanView.findViewById(R.id.text_pengaturan_gantiPassword);

        layoutGantiUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Check Ganti Username", Toast.LENGTH_SHORT).show();
            }
        });

        layoutGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Check Ganti Password", Toast.LENGTH_SHORT).show();
            }
        });
        return pengaturanView;
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

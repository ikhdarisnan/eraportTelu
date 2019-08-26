package com.telu.eraporttelu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telu.eraporttelu.R;

public class lihatProfilGuruFragment extends Fragment {

    private static final String TAG = "lihatProfilGuruFragment";
    private static Context context;
    private TextView namaGuru, nipGuru, nisnSiswa, namaPanggilanSiswa, alamatSiswa, ttlSiswa, kontakSiswa, kelasSiswa;
    private TextView namaWaliSiswa, kontakWaliSiswa, namaOrtuSiswa, kontakOrtuSiswa;

    public lihatProfilGuruFragment() {

    }

    public static lihatProfilGuruFragment newInstance(Context c){
        lihatProfilGuruFragment fragment = new lihatProfilGuruFragment();
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

        View lihatProfileView = inflater.inflate(R.layout.fragment_profile_akun_guru,container,false);
        namaGuru = lihatProfileView.findViewById(R.id.text_profil_namaGuru);
        nipGuru = lihatProfileView.findViewById(R.id.text_profil_nipGuru);
//        nisnSiswa = lihatProfileView.findViewById(R.id.text_profil_nisNasionalSiswa);
//        namaPanggilanSiswa = lihatProfileView.findViewById(R.id.text_profil_namaPanggilan);
//        alamatSiswa = lihatProfileView.findViewById(R.id.text_profil_alamat);
//        ttlSiswa = lihatProfileView.findViewById(R.id.text_profil_ttl);
//        kontakSiswa = lihatProfileView.findViewById(R.id.text_profil_KontakMurid);
//        kelasSiswa = lihatProfileView.findViewById(R.id.text_profil_Kelas);
//
//        namaWaliSiswa = lihatProfileView.findViewById(R.id.text_profil_waliKelas);
//        kontakWaliSiswa = lihatProfileView.findViewById(R.id.text_profil_kontakWaliKelas);
//        namaOrtuSiswa = lihatProfileView.findViewById(R.id.text_profil_namaOrtu);
//        kontakOrtuSiswa = lihatProfileView.findViewById(R.id.text_profil_kontakOrtu);


        return lihatProfileView;
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

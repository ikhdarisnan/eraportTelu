package com.telu.eraporttelu.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.telu.eraporttelu.R;
import com.telu.eraporttelu.model.modelSiswa;
import com.telu.eraporttelu.response.loadGuru;
import com.telu.eraporttelu.response.loadSiswa;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lihatProfilSiswaFragment extends Fragment {

    private static final String TAG = "lihatProfilSiswaFragment";
    private static Context context;
    private ProgressBar pd;
    private TextView namaSiswa, nisSiswa, nisnSiswa, namaPanggilanSiswa, alamatSiswa, ttlSiswa, kontakSiswa, kelasSiswa;
    private TextView namaWaliSiswa, kontakWaliSiswa, namaOrtuSiswa, kontakOrtuSiswa, semester;
    private ArrayList<modelSiswa> listDataDiriSiswa;
    private String NIS;
    APIInterface mApiInterface;

    public lihatProfilSiswaFragment() {

    }

    public static lihatProfilSiswaFragment newInstance(Context c){
        lihatProfilSiswaFragment fragment = new lihatProfilSiswaFragment();
        context = c;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listDataDiriSiswa = new ArrayList<>();

        mApiInterface = APIClient.getClient().create(APIInterface.class);

        SharedPreferences preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("DATALOGIN", Context.MODE_PRIVATE);
        NIS = preferences.getString("USERNAME", null);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View lihatProfileView = inflater.inflate(R.layout.fragment_profile_akun_siswa,container,false);
        pd = lihatProfileView.findViewById(R.id.pb_profilSiswa);
        namaSiswa = lihatProfileView.findViewById(R.id.text_profil_namaSiswa);
        nisSiswa = lihatProfileView.findViewById(R.id.text_profil_nisSiswa);
        nisnSiswa = lihatProfileView.findViewById(R.id.text_profil_nisNasionalSiswa);
        namaPanggilanSiswa = lihatProfileView.findViewById(R.id.text_profil_namaPanggilan);
        alamatSiswa = lihatProfileView.findViewById(R.id.text_profil_alamat);
        ttlSiswa = lihatProfileView.findViewById(R.id.text_profil_ttl);
        kontakSiswa = lihatProfileView.findViewById(R.id.text_profil_KontakMurid);
        kelasSiswa = lihatProfileView.findViewById(R.id.text_profil_Kelas);

        namaWaliSiswa = lihatProfileView.findViewById(R.id.text_profil_waliKelas);
        kontakWaliSiswa = lihatProfileView.findViewById(R.id.text_profil_kontakWaliKelas);
        namaOrtuSiswa = lihatProfileView.findViewById(R.id.text_profil_namaOrtu);
        kontakOrtuSiswa = lihatProfileView.findViewById(R.id.text_profil_kontakOrtu);

        onLoadProfileGuru(NIS);
        return lihatProfileView;
    }

    private void onLoadProfileGuru(String NIS){
        pd.setIndeterminate(true);
        Call<loadSiswa> loadSiswaProfileCall = mApiInterface.getDataSiswa(NIS);
        loadSiswaProfileCall.enqueue(new Callback<loadSiswa>() {
            @Override
            public void onResponse(Call<loadSiswa> call, Response<loadSiswa> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size() > 0){
                        for (int i=0; i<response.body().getData().size(); i++){
                            listDataDiriSiswa.add(response.body().getData().get(i));

                            namaSiswa.setText(listDataDiriSiswa.get(i).getNamaSiswa());
                            nisSiswa.setText(listDataDiriSiswa.get(i).getNISSiswa());
                            alamatSiswa.setText(listDataDiriSiswa.get(i).getAlamatSiswa());
                            kelasSiswa.setText(listDataDiriSiswa.get(i).getNamaKelas());
                            namaPanggilanSiswa.setText(listDataDiriSiswa.get(i).getNamaPanggilan());
                            ttlSiswa.setText(listDataDiriSiswa.get(i).getTtlSiswa());
                            kontakSiswa.setText(listDataDiriSiswa.get(i).getKontakSiswa());
//                            semester.setText(listDataDiriSiswa.get(i).getSemesterSiswa());
                            namaWaliSiswa.setText(listDataDiriSiswa.get(i).getWaliKelasSiswa());
                            kontakWaliSiswa.setText(listDataDiriSiswa.get(i).getKontakWaliKelasSiswa());
                            namaOrtuSiswa.setText(listDataDiriSiswa.get(i).getNamaOrtuSiswa());
                            kontakOrtuSiswa.setText(listDataDiriSiswa.get(i).getKontakOrtuSiswa());
                            pd.setIndeterminate(false);

                        }
                    }else {
                        pd.setIndeterminate(false);
                        Toast.makeText(context, "Gagal: Data Diri tidak berhasil ditampung", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pd.setIndeterminate(false);
                    Toast.makeText(context, "Error 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadSiswa> call, Throwable t) {
                pd.setIndeterminate(false);
                Toast.makeText(context, "Gagal: Harap periksa jaringan internet ", Toast.LENGTH_SHORT).show();
            }
        });
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

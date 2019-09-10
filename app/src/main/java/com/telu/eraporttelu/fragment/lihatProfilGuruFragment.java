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
import android.widget.TextView;
import android.widget.Toast;

import com.telu.eraporttelu.R;
import com.telu.eraporttelu.model.modelDataGuru;
import com.telu.eraporttelu.response.loadGuru;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lihatProfilGuruFragment extends Fragment {

    private static final String TAG = "lihatProfilGuruFragment";
    private static Context context;
    private ArrayList<modelDataGuru> listDataDiriGuru;
    private TextView namaGuru, nipGuru, namaPanggilanGuru, alamatGuru, ttlGuru, kontakGuru, waliMuridGuru, mapelGuru;
    private ProgressDialog pd;

    private String NIP;
    APIInterface mApiInterface;

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
        pd = new ProgressDialog(context);

        SharedPreferences preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("GURU:DATADIRI", Context.MODE_PRIVATE);
        NIP = preferences.getString("NIPGuru", null);

        Bundle bundle = new Bundle();
        bundle.getSerializable("DataDiriGuru");
        listDataDiriGuru = new ArrayList<>();
        
        mApiInterface = APIClient.getClient().create(APIInterface.class);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View lihatProfileView = inflater.inflate(R.layout.fragment_profile_akun_guru,container,false);
        namaGuru = lihatProfileView.findViewById(R.id.text_profil_namaGuru);
        nipGuru = lihatProfileView.findViewById(R.id.text_profil_nipGuru);
        namaPanggilanGuru = lihatProfileView.findViewById(R.id.text_profilGuru_namaPanggilan);
        alamatGuru = lihatProfileView.findViewById(R.id.text_profilGuru_alamat);
        ttlGuru = lihatProfileView.findViewById(R.id.text_profilGuru_ttl);
        kontakGuru = lihatProfileView.findViewById(R.id.text_profilGuru_Kontak);
        waliMuridGuru = lihatProfileView.findViewById(R.id.text_profilGuru_Kelas);
        mapelGuru = lihatProfileView.findViewById(R.id.text_profil_KontakMurid);

        onLoadProfileGuru(NIP);
        return lihatProfileView;
    }

    private void onLoadProfileGuru(String NIP){
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        Call<loadGuru> loadGuruProfileCall = mApiInterface.getDataGuru(NIP);
        loadGuruProfileCall.enqueue(new Callback<loadGuru>() {
            @Override
            public void onResponse(Call<loadGuru> call, Response<loadGuru> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size() > 0){
                        for (int i=0; i<response.body().getData().size(); i++){
                            pd.cancel();
                            listDataDiriGuru.add(response.body().getData().get(i));

                            namaGuru.setText(listDataDiriGuru.get(i).getNamaGuru());
                            nipGuru.setText(listDataDiriGuru.get(i).getNIPGuru());
                            alamatGuru.setText(listDataDiriGuru.get(i).getAlamatGuru());
                            namaPanggilanGuru.setText(listDataDiriGuru.get(i).getNamaPanggilan());
                            ttlGuru.setText(listDataDiriGuru.get(i).getTempatTanggalLahir());
                            kontakGuru.setText(listDataDiriGuru.get(i).getKontakGuru());
                            waliMuridGuru.setText(listDataDiriGuru.get(i).getIdKelas());
//                            mapelGuru.setText(listDataDiriGuru.get(i).get;

                        }
                    }else {
                        pd.cancel();
                        Toast.makeText(context, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pd.cancel();
                    Toast.makeText(context, "Error 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadGuru> call, Throwable t) {
                pd.cancel();
                Toast.makeText(context, "Error: "+ t.toString(), Toast.LENGTH_SHORT).show();
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

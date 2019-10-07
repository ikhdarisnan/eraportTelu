package com.telu.eraporttelu.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.telu.eraporttelu.R;
import com.telu.eraporttelu.adapter.siswaAdapter;
import com.telu.eraporttelu.model.modelDataKelas;
import com.telu.eraporttelu.model.modelDataMapel;
import com.telu.eraporttelu.model.modelDataTA;
import com.telu.eraporttelu.model.modelSiswa;
import com.telu.eraporttelu.response.loadKelas;
import com.telu.eraporttelu.response.loadMapel;
import com.telu.eraporttelu.response.loadSiswa;
import com.telu.eraporttelu.response.loadTA;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class inputNilaiFragment extends Fragment {
    private static final String TAG = "inputNilaiFragment";
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private siswaAdapter siswaAdapter;
    private RecyclerView rvDaftarSiswa;
    private Spinner spinnerKelas;
    private Spinner spinnerTA;
    private Spinner spinnerMapel;
    private ProgressBar pd;

    private ArrayAdapter<String> spinnerKelasAdapter;
    private ArrayAdapter<String> spinnerMapelAdapter;
    private ArrayAdapter<String> spinnerTAAdapter;

    private ArrayList<String> arraySpinnerSemester;
    private ArrayList<modelDataMapel> listDataMapel;
    private ArrayList<modelDataKelas> listDataKelas;
    private ArrayList<modelDataTA> listDataTa;
    private ArrayList<modelSiswa> listDataSiswa;

    private String kelasSelected, TASelected, semesterSelected, mapelSelected, NIP;

    private APIInterface mApiInterface;

    public inputNilaiFragment() {

    }

    public static inputNilaiFragment newInstance(Context c){
        inputNilaiFragment fragment = new inputNilaiFragment();
        mContext = c;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        SharedPreferences preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("GURU:DATADIRI", Context.MODE_PRIVATE);
        NIP = preferences.getString("NIPGuru", null);

        listDataKelas = new ArrayList<>();
        listDataMapel = new ArrayList<>();
        listDataTa = new ArrayList<>();
        listDataSiswa = new ArrayList<>();

        arraySpinnerSemester = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inputNilaiView = inflater.inflate(R.layout.fragment_input_nilai, container, false);
        spinnerKelas = inputNilaiView.findViewById(R.id.spinner_input_pilihKelas);
        spinnerTA = inputNilaiView.findViewById(R.id.spinner_input_pilihTA);
        Spinner spinnerSemester = inputNilaiView.findViewById(R.id.spinner_input_pilihSemester);
        spinnerMapel = inputNilaiView.findViewById(R.id.spinner_input_pilihMapel);
        rvDaftarSiswa = inputNilaiView.findViewById(R.id.rv_input_daftarSiswa);
        pd = inputNilaiView.findViewById(R.id.pb_inputNilai);

        loadDaftarKelas(NIP);
        loadDaftarMapel(NIP);
        loadDaftarTA();

        //Spinner Semester
        arraySpinnerSemester.add("GANJIL");
        arraySpinnerSemester.add("GENAP");
        ArrayAdapter<String> spinnerSemesterAdapter = new ArrayAdapter<>(mContext, R.layout.layout_simple_spinner_item, arraySpinnerSemester);
        spinnerSemesterAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
        spinnerSemester.setAdapter(spinnerSemesterAdapter);
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterSelected = parent.getItemAtPosition(position).toString();
                loadDaftarSiswaKelas(kelasSelected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return inputNilaiView;
    }


    private void loadDaftarKelas(String NIPGuru){
        pd.setIndeterminate(true);
        Call<loadKelas> getAllKelasbyGuru = mApiInterface.getDataKelas(NIPGuru);
        getAllKelasbyGuru.enqueue(new Callback<loadKelas>() {
            @Override
            public void onResponse(Call<loadKelas> call, Response<loadKelas> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size()>0){
                        for (int i =0 ; i<response.body().getData().size(); i++){

                            listDataKelas.add(response.body().getData().get(i));

                            //Spinner Kelas
                            final List<String> arrayTempKelas = new ArrayList<>();
                            for (int j =0; j<listDataKelas.size();j++){
                                String namaKelas = listDataKelas.get(j).getNamaKelas();
                                arrayTempKelas.add(namaKelas);
                            }

                            spinnerKelasAdapter = new ArrayAdapter<>(mContext,R.layout.layout_simple_spinner_item, arrayTempKelas);
                            spinnerKelasAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
                            spinnerKelas.setAdapter(spinnerKelasAdapter);
                            spinnerKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    kelasSelected = parent.getItemAtPosition(position).toString();
                                    loadDaftarSiswaKelas(kelasSelected);
                                    Log.d(TAG, "onItemSelected: Item changed " + kelasSelected);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            pd.setIndeterminate(false);
                        }
                    }else{
                        pd.setIndeterminate(false);
                        Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    pd.setIndeterminate(false);
                    Toast.makeText(mContext, "Error1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadKelas> call, Throwable t) {
                pd.setIndeterminate(false);
                Toast.makeText(mContext, "Error: "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDaftarMapel(String NIPGuru){
        pd.setIndeterminate(true);
        Call<loadMapel> getAllMapelbyGuru = mApiInterface.getDataMapel(NIPGuru);
        getAllMapelbyGuru.enqueue(new Callback<loadMapel>() {
            @Override
            public void onResponse(Call<loadMapel> call, Response<loadMapel> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size()>0){
                        for (int i=0; i<response.body().getData().size();i++){
                            listDataMapel.add(response.body().getData().get(i));

                            //Spinner Mapel
                            final List<String> arrayTempMapel= new ArrayList<>();
                            for (int j =0; j<listDataMapel.size();j++){
                                String namaKelas = listDataMapel.get(j).getNamaMapel();
                                arrayTempMapel.add(namaKelas);
                            }

                            spinnerMapelAdapter = new ArrayAdapter<>(mContext,R.layout.layout_simple_spinner_item, arrayTempMapel);
                            spinnerMapelAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
                            spinnerMapel.setAdapter(spinnerMapelAdapter);
                            spinnerMapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    mapelSelected = parent.getItemAtPosition(position).toString();
                                    loadDaftarSiswaKelas(kelasSelected);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            pd.setIndeterminate(false);
                        }
                    }else{
                        pd.setIndeterminate(false);
                        Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    pd.setIndeterminate(false);
                    Toast.makeText(mContext, "Error 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadMapel> call, Throwable t) {
                pd.setIndeterminate(false);
                Toast.makeText(mContext, "Error "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadDaftarTA(){
        pd.setIndeterminate(true);
        Call<loadTA> getAllTaCall = mApiInterface.getAllDataTA();
        getAllTaCall.enqueue(new Callback<loadTA>() {
            @Override
            public void onResponse(Call<loadTA> call, Response<loadTA> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size()>0){
                        for (int i =0;i<response.body().getData().size();i++){
                            listDataTa.add(response.body().getData().get(i));

                            final List<String> arrayTempTa = new ArrayList<>();
                            for (int j = 0; j<listDataTa.size();j++){
                                if (listDataTa.get(j).getStatusTA().equals("1")){
                                    String TA = listDataTa.get(j).getNamaTA();
                                    arrayTempTa.add(TA);
                                }
                            }

                            spinnerTAAdapter = new ArrayAdapter<>(mContext,R.layout.layout_simple_spinner_item, arrayTempTa);
                            spinnerTAAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
                            spinnerTA.setAdapter(spinnerTAAdapter);
                            spinnerTA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    TASelected = parent.getItemAtPosition(position).toString();
                                    loadDaftarSiswaKelas(kelasSelected);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            pd.setIndeterminate(false);
                        }
                    }else {
                        pd.setIndeterminate(false);
                        Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pd.setIndeterminate(false);
                    Toast.makeText(mContext, "Error 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadTA> call, Throwable t) {
                pd.setIndeterminate(false);
                Toast.makeText(mContext, "Error 2: "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDaftarSiswaKelas(String kelas){
        pd.setIndeterminate(true);
        Call<loadSiswa> getSiswaCall = mApiInterface.getSiswa(kelas);
        getSiswaCall.enqueue(new Callback<loadSiswa>() {
            @Override
            public void onResponse(Call<loadSiswa> call, Response<loadSiswa> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size()>0){
                        for (int i=0; i<response.body().getData().size();i++){
                            listDataSiswa.add(response.body().getData().get(i));
//                            mapelSelected = spinnerMapel.getSelectedItem().toString();
//                            kelasSelected = spinnerKelas.getSelectedItem().toString();
//                            semesterSelected = spinnerSemester.getSelectedItem().toString();
//                            TASelected = spinnerTA.getSelectedItem().toString();

                            siswaAdapter = new siswaAdapter(kelasSelected,TASelected,semesterSelected, mapelSelected, mContext,listDataSiswa,listDataMapel);
                            siswaAdapter.notifyDataSetChanged();
                            rvDaftarSiswa.setAdapter(siswaAdapter);
                            rvDaftarSiswa.setLayoutManager(new LinearLayoutManager(mContext));

                            pd.setIndeterminate(false);
                        }
                        listDataSiswa = new ArrayList<>();
                    }else {
                        pd.setIndeterminate(false);
                        Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pd.setIndeterminate(false);
                    Toast.makeText(mContext, "Gagal. Data tidak sesuai", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadSiswa> call, Throwable t) {
                pd.setIndeterminate(false);
//                Toast.makeText(mContext, "Error 2: "+t.toString(), Toast.LENGTH_SHORT).show();
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

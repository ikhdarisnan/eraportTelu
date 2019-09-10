package com.telu.eraporttelu.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.telu.eraporttelu.R;
import com.telu.eraporttelu.adapter.siswaAdapter;
import com.telu.eraporttelu.model.modelDataKelas;
import com.telu.eraporttelu.model.modelDataMapel;
import com.telu.eraporttelu.model.modelSiswa;
import com.telu.eraporttelu.response.loadKelas;
import com.telu.eraporttelu.response.loadMapel;
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

    private Spinner spinnerKelas, spinnerTA, spinnerSemester, spinnerMapel;
    private ProgressDialog pd;

    private ArrayAdapter<String> spinnerKelasAdapter, spinnerMapelAdapter, spinnerTAAdapter, spinnerSemesterAdapter;

    private ArrayList<String> arraySpinnerTA, arraySpinnerSemester;
    private ArrayList<modelDataMapel> arraySpinnerMapel;
    private ArrayList<modelDataKelas> listDataKelas;

    private ArrayList<modelSiswa> listSiswa;
    public String kelasSelected, TASelected, semesterSelected, mapelSelected, NIP;

    APIInterface mApiInterface;

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
        pd = new ProgressDialog(mContext);

        SharedPreferences preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("GURU:DATADIRI", Context.MODE_PRIVATE);
        NIP = preferences.getString("NIPGuru", null);

        listDataKelas = new ArrayList<>();
        arraySpinnerMapel = new ArrayList<>();

        arraySpinnerTA = new ArrayList<>();
        arraySpinnerSemester = new ArrayList<>();

        listSiswa = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inputNilaiView = inflater.inflate(R.layout.fragment_input_nilai, container, false);
        spinnerKelas = inputNilaiView.findViewById(R.id.spinner_input_pilihKelas);
        spinnerTA = inputNilaiView.findViewById(R.id.spinner_input_pilihTA);
        spinnerSemester = inputNilaiView.findViewById(R.id.spinner_input_pilihSemester);
        spinnerMapel = inputNilaiView.findViewById(R.id.spinner_input_pilihMapel);
        RecyclerView rvDaftarSiswa = inputNilaiView.findViewById(R.id.rv_input_daftarSiswa);

        loadDaftarKelas(NIP);
        loadDaftarMapel(NIP);

        //Spinner TA
        arraySpinnerTA.add("2017/2018");
        arraySpinnerTA.add("2018/2019");
        arraySpinnerTA.add("2019/2020");

        spinnerTAAdapter = new ArrayAdapter<>(mContext,R.layout.layout_simple_spinner_item, arraySpinnerTA);
        spinnerTAAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
        spinnerTA.setAdapter(spinnerTAAdapter);
        //TASelected = spinnerTA.getSelectedItem().toString();
        spinnerTA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TASelected = parent.getItemAtPosition(position).toString();
                siswaAdapter.notifyItemChanged(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner Semester
        arraySpinnerSemester.add("GANJIL");
        arraySpinnerSemester.add("GENAP");

        spinnerSemesterAdapter = new ArrayAdapter<>(mContext,R.layout.layout_simple_spinner_item, arraySpinnerSemester);
        spinnerSemesterAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
        spinnerSemester.setAdapter(spinnerSemesterAdapter);
        String semesterSelected = spinnerSemester.getSelectedItem().toString();



        modelSiswa s1 = new modelSiswa("0128562737","Johny Alexander");
        modelSiswa s2 = new modelSiswa("0128562738","Anita Dwi Anira");
        modelSiswa s3 = new modelSiswa("0128562740","Tobing Smith");
        modelSiswa s4 = new modelSiswa("0128562737","Johny Alexander");
        modelSiswa s5 = new modelSiswa("0128562738","Anita Dwi Anira");
        modelSiswa s6 = new modelSiswa("0128562740","Tobing Smith");
        modelSiswa s7 = new modelSiswa("0128562737","Johny Alexander");
        modelSiswa s8 = new modelSiswa("0128562738","Anita Dwi Anira");
        modelSiswa s9 = new modelSiswa("0128562740","Tobing Smith");

        listSiswa.add(s1);listSiswa.add(s2);listSiswa.add(s3);listSiswa.add(s4);listSiswa.add(s5);listSiswa.add(s6);listSiswa.add(s7);listSiswa.add(s8);listSiswa.add(s9);

        siswaAdapter = new siswaAdapter(kelasSelected,TASelected,semesterSelected,mapelSelected,mContext,listSiswa);
        siswaAdapter.notifyDataSetChanged();
        rvDaftarSiswa.setAdapter(siswaAdapter);
        rvDaftarSiswa.setLayoutManager(new LinearLayoutManager(mContext));

        return inputNilaiView;
    }


    private void loadDaftarKelas(String NIPGuru){
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        Call<loadKelas> getAllKelasbyGuru = mApiInterface.getDataKelas(NIPGuru);
        getAllKelasbyGuru.enqueue(new Callback<loadKelas>() {
            @Override
            public void onResponse(Call<loadKelas> call, Response<loadKelas> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size()>0){
                        for (int i =0 ; i<response.body().getData().size(); i++){
                            pd.cancel();
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
                                    siswaAdapter.notifyItemChanged(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }else{
                        pd.cancel();
                        Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    pd.cancel();
                    Toast.makeText(mContext, "Error1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadKelas> call, Throwable t) {
                pd.cancel();
                Toast.makeText(mContext, "Error: "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDaftarMapel(String NIPGuru){
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        Call<loadMapel> getAllMapelbyGuru = mApiInterface.getDataMapel(NIPGuru);
        getAllMapelbyGuru.enqueue(new Callback<loadMapel>() {
            @Override
            public void onResponse(Call<loadMapel> call, Response<loadMapel> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size()>0){
                        for (int i=0; i<response.body().getData().size();i++){
                            pd.cancel();
                            arraySpinnerMapel.add(response.body().getData().get(i));

                            //Spinner Mapel
                            final List<String> arrayTempMapel= new ArrayList<>();
                            for (int j =0; j<arraySpinnerMapel.size();j++){
                                String namaKelas = arraySpinnerMapel.get(j).getNamaMapel();
                                arrayTempMapel.add(namaKelas);
                            }

                            spinnerMapelAdapter = new ArrayAdapter<>(mContext,R.layout.layout_simple_spinner_item, arrayTempMapel);
                            spinnerMapelAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
                            spinnerMapel.setAdapter(spinnerMapelAdapter);
                            String mapelSelected = spinnerMapel.getSelectedItem().toString();
                        }
                    }else{
                        pd.cancel();
                        Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    pd.cancel();
                    Toast.makeText(mContext, "Error 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadMapel> call, Throwable t) {
                pd.cancel();
                Toast.makeText(mContext, "Error "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadDaftarTA(){

    }

    private void loadDaftarSiswaKelas(String kelas){

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

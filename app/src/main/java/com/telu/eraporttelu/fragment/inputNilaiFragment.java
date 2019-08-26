package com.telu.eraporttelu.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.telu.eraporttelu.R;
import com.telu.eraporttelu.adapter.siswaAdapter;
import com.telu.eraporttelu.model.modelSiswa;

import java.util.ArrayList;
import java.util.List;

public class inputNilaiFragment extends Fragment {
    private static final String TAG = "inputNilaiFragment";
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    public Spinner spinnerKelas, spinnerTA, spinnerSemester, spinnerMapel;
    public ArrayAdapter<String> spinnerKelasAdapter, spinnerTAAdapter, spinnerSemesterAdapter, spinnerMapelAdapter;
    private siswaAdapter siswaAdapter;


    private ArrayList<String> arraySpinnerKelas, arraySpinnerTA, arraySpinnerSemester, arraySpinnerMapel;
    private ArrayList<modelSiswa> listSiswa;
    public String kelasSelected, TASelected, semesterSelected, mapelSelected;

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
        arraySpinnerKelas = new ArrayList<>();
        arraySpinnerTA = new ArrayList<>();
        arraySpinnerSemester = new ArrayList<>();
        arraySpinnerMapel = new ArrayList<>();
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

        arraySpinnerKelas.add("1A");
        arraySpinnerKelas.add("1B");
        arraySpinnerKelas.add("2A");
        arraySpinnerKelas.add("2B");
        arraySpinnerKelas.add("3A");
        arraySpinnerKelas.add("3B");

        spinnerKelasAdapter = new ArrayAdapter<>(mContext,R.layout.layout_simple_spinner_item, arraySpinnerKelas);
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

        arraySpinnerSemester.add("GANJIL");
        arraySpinnerSemester.add("GENAP");

        spinnerSemesterAdapter = new ArrayAdapter<>(mContext,R.layout.layout_simple_spinner_item, arraySpinnerSemester);
        spinnerSemesterAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
        spinnerSemester.setAdapter(spinnerSemesterAdapter);
        String semesterSelected = spinnerSemester.getSelectedItem().toString();

        arraySpinnerMapel.add("Pendidikan Kewarganegaraan");
        arraySpinnerMapel.add("Agama");
        arraySpinnerMapel.add("Bahasa Indonesia");
        arraySpinnerMapel.add("Matematika");
        arraySpinnerMapel.add("IPA");

        spinnerMapelAdapter = new ArrayAdapter<>(mContext,R.layout.layout_simple_spinner_item, arraySpinnerMapel);
        spinnerMapelAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown_item);
        spinnerMapel.setAdapter(spinnerMapelAdapter);
        String mapelSelected = spinnerMapel.getSelectedItem().toString();

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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}

package com.telu.eraporttelu.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.telu.eraporttelu.R;
import com.telu.eraporttelu.model.modelDataTA;
import com.telu.eraporttelu.model.modelNilai;
import com.telu.eraporttelu.response.loadNilai;
import com.telu.eraporttelu.response.loadTA;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lihatNilaiFragment extends Fragment {

    private static final String TAG = "lihatNilaiFragment";
    private static Context mContext;

    private String TASelected, semesterSelected, NISSiswa;
    private int kkm, TotNilai, avgNilai;

    private Spinner spinnerTA, spinnerSemester;
    private ArrayAdapter<String> spinnerTAAdapter;

    private TextView nilaiAgama, kkmAgama, deskAgama, nilaiPkn, kkmPkn, deskPkn ,nilaiBindo ,kkmBindo, deskBindo, nilaiMtk ,kkmMtk, deskMtk, nilaiIpa ,kkmIpa, deskIpa,
            nilaiIps, kkmIps, deskIps, nilaiSenBud, kkmSenBud, deskSenBud, nilaiPenjas, kkmPenjas, deskPenjas, kkmBsunda, nilaiBsunda,deskBSunda;
    private TextView jmlhNilai, rataNilai;

    private ProgressBar pd;

    private ArrayList<modelDataTA> listTahunAjaran;
    private ArrayList<modelNilai> listAllNilai;
    private ArrayList<Integer> listFinNilai;

    private APIInterface mApiInterface;

    public lihatNilaiFragment() {

    }

    public static lihatNilaiFragment newInstance(Context c){
        lihatNilaiFragment fragment = new lihatNilaiFragment();
        mContext = c;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApiInterface = APIClient.getClient().create(APIInterface.class);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("DATALOGIN",Context.MODE_PRIVATE);
        NISSiswa = sharedPreferences.getString("USERNAME", null);

        listTahunAjaran = new ArrayList<>();
        listAllNilai = new ArrayList<>();
        listFinNilai = new ArrayList<>();
        kkm = 70;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View lihatNilaiView = inflater.inflate(R.layout.fragment_lihat_nilai,container,false);

        pd = lihatNilaiView.findViewById(R.id.pb_lihatNilai);
//        TableLayout tableLayout1 = lihatNilaiView.findViewById(R.id.table_1);
//        TableLayout tableLayout2 = lihatNilaiView.findViewById(R.id.table_2);

        spinnerTA = lihatNilaiView.findViewById(R.id.spinner_lihatNilai_spTA);
        onLoadTahunAjaran();
        spinnerSemester = lihatNilaiView.findViewById(R.id.spinner_lihatNilai_spSemester);
        onLoadSemester();

        Button btnCari = lihatNilaiView.findViewById(R.id.btn_lihatNilai_cari);

        nilaiAgama = lihatNilaiView.findViewById(R.id.text_table_nilaiAgama);
        kkmAgama = lihatNilaiView.findViewById(R.id.text_table_kkmAgama);
        kkmAgama.setText(String.valueOf(kkm));
        deskAgama = lihatNilaiView.findViewById(R.id.text_table_deskAgama);

        nilaiPkn = lihatNilaiView.findViewById(R.id.text_table_nilaiPkn);
        kkmPkn = lihatNilaiView.findViewById(R.id.text_table_kkmPkn);
        kkmPkn.setText(String.valueOf(kkm));
        deskPkn = lihatNilaiView.findViewById(R.id.text_table_deskPkn);

        nilaiBindo = lihatNilaiView.findViewById(R.id.text_table_nilaiBindo);
        kkmBindo = lihatNilaiView.findViewById(R.id.text_table_kkmBindo);
        kkmBindo.setText(String.valueOf(kkm));
        deskBindo = lihatNilaiView.findViewById(R.id.text_table_deskBindo);

        nilaiMtk = lihatNilaiView.findViewById(R.id.text_table_nilaiMtk);
        kkmMtk = lihatNilaiView.findViewById(R.id.text_table_kkmMtk);
        kkmMtk.setText(String.valueOf(kkm));
        deskMtk = lihatNilaiView.findViewById(R.id.text_table_deskMtk);

        nilaiIpa = lihatNilaiView.findViewById(R.id.text_table_nilaiIpa);
        kkmIpa = lihatNilaiView.findViewById(R.id.text_table_kkmIpa);
        kkmIpa.setText(String.valueOf(kkm));
        deskIpa = lihatNilaiView.findViewById(R.id.text_table_deskIpa);

        nilaiIps = lihatNilaiView.findViewById(R.id.text_table_nilaiIps);
        kkmIps = lihatNilaiView.findViewById(R.id.text_table_kkmIps);
        kkmIps.setText(String.valueOf(kkm));
        deskIps = lihatNilaiView.findViewById(R.id.text_table_deskIps);

        nilaiSenBud = lihatNilaiView.findViewById(R.id.text_table_nilaiSenbud);
        kkmSenBud = lihatNilaiView.findViewById(R.id.text_table_kkmSenbud);
        kkmSenBud.setText(String.valueOf(kkm));
        deskSenBud = lihatNilaiView.findViewById(R.id.text_table_deskSenbud);

        nilaiPenjas = lihatNilaiView.findViewById(R.id.text_table_nilaiPenjas);
        kkmPenjas = lihatNilaiView.findViewById(R.id.text_table_kkmPenjas);
        kkmPenjas.setText(String.valueOf(kkm));
        deskPenjas = lihatNilaiView.findViewById(R.id.text_table_deskPenjas);

        nilaiBsunda = lihatNilaiView.findViewById(R.id.text_table_nilaiBunda);
        kkmBsunda = lihatNilaiView.findViewById(R.id.text_table_kkmBunda);
        kkmBsunda.setText(String.valueOf(kkm));
        deskBSunda = lihatNilaiView.findViewById(R.id.text_table_deskBunda);

        jmlhNilai = lihatNilaiView.findViewById(R.id.text_table_jmlhNilai);
        rataNilai = lihatNilaiView.findViewById(R.id.text_table_rata2);

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadAllNilai();
            }
        });

        return lihatNilaiView;

    }

    private void onGetNilaiByMapel(){
        int uas,uts,uh1,uh2,uh3,uh4,uh5;
        String terlampaui = "TERLAMPAUI";
        String tidakTerlampaui = "TIDAK TERLAMPAUI";
        String cukup = "CUKUP";
        for (int i=0; i<listAllNilai.size(); i++){
            int nilaiAkhir;
            switch (listAllNilai.get(i).getIdMapel()) {
                case "1":
                    uas = Integer.parseInt(listAllNilai.get(i).getUAS());
                    uts = Integer.parseInt(listAllNilai.get(i).getUTS());
                    uh1 = Integer.parseInt(listAllNilai.get(i).getUH1());
                    uh2 = Integer.parseInt(listAllNilai.get(i).getUH2());
                    uh3 = Integer.parseInt(listAllNilai.get(i).getUH3());
                    uh4 = Integer.parseInt(listAllNilai.get(i).getUH4());
                    uh5 = Integer.parseInt(listAllNilai.get(i).getUH5());

                    nilaiAkhir = calculateNilaiAkhir(uts, uas, uh1, uh2, uh3, uh4, uh5);
                    listFinNilai.add(nilaiAkhir);
                    nilaiAgama.setText(String.valueOf(nilaiAkhir));
                    if (nilaiAkhir > kkm) {
                        deskAgama.setText(terlampaui);
                    } else if (nilaiAkhir == kkm) {
                        deskAgama.setText(cukup);
                    } else {
                        deskAgama.setText(tidakTerlampaui);
                    }

                    break;
                case "2":
                    uas = Integer.parseInt(listAllNilai.get(i).getUAS());
                    uts = Integer.parseInt(listAllNilai.get(i).getUTS());
                    uh1 = Integer.parseInt(listAllNilai.get(i).getUH1());
                    uh2 = Integer.parseInt(listAllNilai.get(i).getUH2());
                    uh3 = Integer.parseInt(listAllNilai.get(i).getUH3());
                    uh4 = Integer.parseInt(listAllNilai.get(i).getUH4());
                    uh5 = Integer.parseInt(listAllNilai.get(i).getUH5());

                    nilaiAkhir = calculateNilaiAkhir(uts, uas, uh1, uh2, uh3, uh4, uh5);
                    listFinNilai.add(nilaiAkhir);
                    nilaiPkn.setText(String.valueOf(nilaiAkhir));

                    if (nilaiAkhir > kkm) {
                        deskPkn.setText(terlampaui);
                    } else if (nilaiAkhir == kkm) {
                        deskPkn.setText(cukup);
                    } else {
                        deskPkn.setText(tidakTerlampaui);
                    }
                    break;
                case "3":
                    uas = Integer.parseInt(listAllNilai.get(i).getUAS());
                    uts = Integer.parseInt(listAllNilai.get(i).getUTS());
                    uh1 = Integer.parseInt(listAllNilai.get(i).getUH1());
                    uh2 = Integer.parseInt(listAllNilai.get(i).getUH2());
                    uh3 = Integer.parseInt(listAllNilai.get(i).getUH3());
                    uh4 = Integer.parseInt(listAllNilai.get(i).getUH4());
                    uh5 = Integer.parseInt(listAllNilai.get(i).getUH5());

                    nilaiAkhir = calculateNilaiAkhir(uts, uas, uh1, uh2, uh3, uh4, uh5);
                    listFinNilai.add(nilaiAkhir);
                    nilaiBindo.setText(String.valueOf(nilaiAkhir));

                    if (nilaiAkhir > kkm) {
                        deskBindo.setText(terlampaui);
                    } else if (nilaiAkhir == kkm) {
                        deskBindo.setText(cukup);
                    } else {
                        deskBindo.setText(tidakTerlampaui);
                    }
                    break;
                case "4":
                    uas = Integer.parseInt(listAllNilai.get(i).getUAS());
                    uts = Integer.parseInt(listAllNilai.get(i).getUTS());
                    uh1 = Integer.parseInt(listAllNilai.get(i).getUH1());
                    uh2 = Integer.parseInt(listAllNilai.get(i).getUH2());
                    uh3 = Integer.parseInt(listAllNilai.get(i).getUH3());
                    uh4 = Integer.parseInt(listAllNilai.get(i).getUH4());
                    uh5 = Integer.parseInt(listAllNilai.get(i).getUH5());

                    nilaiAkhir = calculateNilaiAkhir(uts, uas, uh1, uh2, uh3, uh4, uh5);
                    listFinNilai.add(nilaiAkhir);
                    nilaiMtk.setText(String.valueOf(nilaiAkhir));

                    if (nilaiAkhir > kkm) {
                        deskMtk.setText(terlampaui);
                    } else if (nilaiAkhir == kkm) {
                        deskMtk.setText(cukup);
                    } else {
                        deskMtk.setText(tidakTerlampaui);
                    }
                    break;
                case "5":
                    uas = Integer.parseInt(listAllNilai.get(i).getUAS());
                    uts = Integer.parseInt(listAllNilai.get(i).getUTS());
                    uh1 = Integer.parseInt(listAllNilai.get(i).getUH1());
                    uh2 = Integer.parseInt(listAllNilai.get(i).getUH2());
                    uh3 = Integer.parseInt(listAllNilai.get(i).getUH3());
                    uh4 = Integer.parseInt(listAllNilai.get(i).getUH4());
                    uh5 = Integer.parseInt(listAllNilai.get(i).getUH5());

                    nilaiAkhir = calculateNilaiAkhir(uts, uas, uh1, uh2, uh3, uh4, uh5);
                    listFinNilai.add(nilaiAkhir);
                    nilaiIpa.setText(String.valueOf(nilaiAkhir));

                    if (nilaiAkhir > kkm) {
                        deskIpa.setText(terlampaui);
                    } else if (nilaiAkhir == kkm) {
                        deskIpa.setText(cukup);
                    } else {
                        deskIpa.setText(tidakTerlampaui);
                    }

                    break;
                case "6":
                    uas = Integer.parseInt(listAllNilai.get(i).getUAS());
                    uts = Integer.parseInt(listAllNilai.get(i).getUTS());
                    uh1 = Integer.parseInt(listAllNilai.get(i).getUH1());
                    uh2 = Integer.parseInt(listAllNilai.get(i).getUH2());
                    uh3 = Integer.parseInt(listAllNilai.get(i).getUH3());
                    uh4 = Integer.parseInt(listAllNilai.get(i).getUH4());
                    uh5 = Integer.parseInt(listAllNilai.get(i).getUH5());

                    nilaiAkhir = calculateNilaiAkhir(uts, uas, uh1, uh2, uh3, uh4, uh5);
                    listFinNilai.add(nilaiAkhir);
                    nilaiIps.setText(String.valueOf(nilaiAkhir));

                    if (nilaiAkhir > kkm) {
                        deskIps.setText(terlampaui);
                    } else if (nilaiAkhir == kkm) {
                        deskIps.setText(cukup);
                    } else {
                        deskIps.setText(tidakTerlampaui);
                    }
                    break;
                case "7":
                    uas = Integer.parseInt(listAllNilai.get(i).getUAS());
                    uts = Integer.parseInt(listAllNilai.get(i).getUTS());
                    uh1 = Integer.parseInt(listAllNilai.get(i).getUH1());
                    uh2 = Integer.parseInt(listAllNilai.get(i).getUH2());
                    uh3 = Integer.parseInt(listAllNilai.get(i).getUH3());
                    uh4 = Integer.parseInt(listAllNilai.get(i).getUH4());
                    uh5 = Integer.parseInt(listAllNilai.get(i).getUH5());

                    nilaiAkhir = calculateNilaiAkhir(uts, uas, uh1, uh2, uh3, uh4, uh5);
                    listFinNilai.add(nilaiAkhir);
                    nilaiSenBud.setText(String.valueOf(nilaiAkhir));

                    if (nilaiAkhir > kkm) {
                        deskSenBud.setText(terlampaui);
                    } else if (nilaiAkhir == kkm) {
                        deskSenBud.setText(cukup);
                    } else {
                        deskSenBud.setText(tidakTerlampaui);
                    }
                    break;
                case "8":
                    uas = Integer.parseInt(listAllNilai.get(i).getUAS());
                    uts = Integer.parseInt(listAllNilai.get(i).getUTS());
                    uh1 = Integer.parseInt(listAllNilai.get(i).getUH1());
                    uh2 = Integer.parseInt(listAllNilai.get(i).getUH2());
                    uh3 = Integer.parseInt(listAllNilai.get(i).getUH3());
                    uh4 = Integer.parseInt(listAllNilai.get(i).getUH4());
                    uh5 = Integer.parseInt(listAllNilai.get(i).getUH5());

                    nilaiAkhir = calculateNilaiAkhir(uts, uas, uh1, uh2, uh3, uh4, uh5);
                    listFinNilai.add(nilaiAkhir);
                    nilaiPenjas.setText(String.valueOf(nilaiAkhir));

                    if (nilaiAkhir > kkm) {
                        deskPenjas.setText(terlampaui);
                    } else if (nilaiAkhir == kkm) {
                        deskPenjas.setText(cukup);
                    } else {
                        deskPenjas.setText(tidakTerlampaui);
                    }
                    break;
                case "9":
                    uas = Integer.parseInt(listAllNilai.get(i).getUAS());
                    uts = Integer.parseInt(listAllNilai.get(i).getUTS());
                    uh1 = Integer.parseInt(listAllNilai.get(i).getUH1());
                    uh2 = Integer.parseInt(listAllNilai.get(i).getUH2());
                    uh3 = Integer.parseInt(listAllNilai.get(i).getUH3());
                    uh4 = Integer.parseInt(listAllNilai.get(i).getUH4());
                    uh5 = Integer.parseInt(listAllNilai.get(i).getUH5());

                    nilaiAkhir = calculateNilaiAkhir(uts, uas, uh1, uh2, uh3, uh4, uh5);
                    listFinNilai.add(nilaiAkhir);
                    nilaiBsunda.setText(String.valueOf(nilaiAkhir));

                    if (nilaiAkhir > kkm) {
                        deskBSunda.setText(terlampaui);
                    } else if (nilaiAkhir == kkm) {
                        deskBSunda.setText(cukup);
                    } else {
                        deskBSunda.setText(tidakTerlampaui);
                    }
                    break;
            }
        }
        onCalculateSumAvg();
    }
    private void onLoadAllNilai(){
        pd.setIndeterminate(true);
        Call<loadNilai> getAllNilai = mApiInterface.getNilaiSiswaBySemTA(NISSiswa,semesterSelected,TASelected);
        getAllNilai.enqueue(new Callback<loadNilai>() {
            @Override
            public void onResponse(Call<loadNilai> call, Response<loadNilai> response) {
                if (response.isSuccessful()){
                    listAllNilai.addAll(response.body().getBundleData());
                    onGetNilaiByMapel();
                    listAllNilai=new ArrayList<>();
                }else {
                    Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadNilai> call, Throwable t) {
                Toast.makeText(mContext, "Error: "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onLoadTahunAjaran(){
        pd.setIndeterminate(true);
        Call<loadTA> getAllTaCall = mApiInterface.getAllDataTA();
        getAllTaCall.enqueue(new Callback<loadTA>() {
            @Override
            public void onResponse(Call<loadTA> call, Response<loadTA> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size()>0){
                        for (int i =0;i<response.body().getData().size();i++){
                            listTahunAjaran.add(response.body().getData().get(i));

                            final List<String> arrayTempTa = new ArrayList<>();
                            for (int j = 0; j<listTahunAjaran.size();j++){
                                if (listTahunAjaran.get(j).getStatusTA().equals("1")){
                                    String TA = listTahunAjaran.get(j).getNamaTA();
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

                                    nilaiSenBud.setText("-");
                                    deskSenBud.setText("-");
                                    nilaiAgama.setText("-");
                                    deskAgama.setText("-");
                                    nilaiBindo.setText("-");
                                    deskBindo.setText("-");
                                    nilaiBsunda.setText("-");
                                    deskBSunda.setText("-");
                                    nilaiIpa.setText("-");
                                    deskIpa.setText("-");
                                    nilaiIps.setText("-");
                                    deskIps.setText("-");
                                    nilaiMtk.setText("-");
                                    deskMtk.setText("-");
                                    nilaiPenjas.setText("-");
                                    deskPenjas.setText("-");
                                    nilaiPkn.setText("-");
                                    deskPkn.setText("-");
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

    private void onLoadSemester(){
        ArrayList<String> arraySpinnerSemester;
        arraySpinnerSemester = new ArrayList<>();

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

                nilaiAgama.setText("-");
                deskAgama.setText("-");
                nilaiBindo.setText("-");
                deskBindo.setText("-");
                nilaiBsunda.setText("-");
                deskBSunda.setText("-");
                nilaiIpa.setText("-");
                deskIpa.setText("-");
                nilaiIps.setText("-");
                deskIps.setText("-");
                nilaiMtk.setText("-");
                deskMtk.setText("-");
                nilaiPenjas.setText("-");
                deskPenjas.setText("-");
                nilaiPkn.setText("-");
                deskPkn.setText("-");
                nilaiSenBud.setText("-");
                deskSenBud.setText("-");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private int calculateNilaiAkhir(int uts, int uas, int uh1, int uh2, int uh3, int uh4, int uh5){
        int finUts, finUas, finUh, finalNilaiAkhir;
        int limiter=5;

        finUas = ((uas*40)/100);
        finUts = ((uts*30)/100);

        if (uh4==-1&&uh5==-1){
            limiter =3;
            finUh = ((uh1+uh2+uh3)/limiter);
        }else if (uh5==-1){
            limiter=4;
            finUh = ((uh1+uh2+uh3+uh4)/limiter);
        }else {
            finUh = (((uh1+uh2+uh3+uh4+uh5)/limiter)*30)/100;
        }

        finalNilaiAkhir = ((finUas+finUts+finUh));

        return finalNilaiAkhir;
    }

    private void onCalculateSumAvg(){
        if (listFinNilai!= null){
            int jumlah = listFinNilai.size();
            TotNilai = 0;
            for (int i=0; i<jumlah;i++){
                TotNilai = TotNilai + listFinNilai.get(i);
            }
            jmlhNilai.setText(String.valueOf(TotNilai));

            if (listFinNilai.size()==0){
                avgNilai = 0;
            }else {
                avgNilai = TotNilai/listFinNilai.size();
            }
            rataNilai.setText(String.valueOf(avgNilai));
            listFinNilai = new ArrayList<>();
            Toast.makeText(mContext, "Data Nilai Berhasil didapatkan", Toast.LENGTH_LONG).show();
        }
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

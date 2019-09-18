package com.telu.eraporttelu.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.telu.eraporttelu.R;
import com.telu.eraporttelu.model.modelDataKelas;
import com.telu.eraporttelu.model.modelDataMapel;
import com.telu.eraporttelu.model.modelNilai;
import com.telu.eraporttelu.model.modelSiswa;
import com.telu.eraporttelu.response.loadNilai;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class siswaAdapter extends RecyclerView.Adapter<siswaAdapter.ViewHolder> {

    private static final String TAG = "siswaAdapter";
    private String kelas, TA, semester, mapel, NIPGuru, NISSiswa;
    private String idMapel;
    private int onResultUpdate;
    private Context context;
    private ProgressBar pd;
    private EditText nilaiUASDialog, nilaiUTSDialog, nilaiUH1Dialog, nilaiUH2Dialog, nilaiUH3Dialog, nilaiUH4Dialog, nilaiUH5Dialog;
    private ArrayList<modelSiswa> listSiswa;
    private ArrayList<modelDataMapel> listMapel;
    private ArrayList<modelNilai> listNilai;

    private APIInterface mApiInterface;

    public siswaAdapter(String kelas, String TA, String semester, String mapel, Context context, ArrayList<modelSiswa> listSiswa, ArrayList<modelDataMapel> listMapel) {
        this.kelas = kelas;
        this.TA = TA;
        this.semester = semester;
        this.mapel = mapel;
        this.context = context;
        this.listSiswa = listSiswa;
        this.listMapel = listMapel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_siswa, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        SharedPreferences preferences = Objects.requireNonNull(context).getSharedPreferences("GURU:DATADIRI", Context.MODE_PRIVATE);
        NIPGuru = preferences.getString("NIPGuru", null);

        listNilai = new ArrayList<>();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.namaSiswa.setText(listSiswa.get(position).getNamaSiswa());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String nama = listSiswa.get(position).getNamaSiswa();
                openInputNilaiDialog(position);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaSiswa;
        ImageView btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.namaSiswa = itemView.findViewById(R.id.text_input_namaSiswa);
            this.btnEdit = itemView.findViewById(R.id.btn_input_editNilai);
        }
    }

    @Override
    public int getItemCount() {
        return listSiswa.size();
    }

    private void openInputNilaiDialog(int pos) {
        Button btnOkDilaog, btnCancelDialog;
        TextView namaSiswaDialog, kelasSiswaDialog, TASiswaDialog, SemesterSiswaDialog, mapelSiswaDialog;

        onResultUpdate = 0;

        mApiInterface = APIClient.getClient().create(APIInterface.class);

        final Dialog inputNilai = new Dialog(context);
        inputNilai.setCancelable(false);
        inputNilai.setCanceledOnTouchOutside(false);

        inputNilai.setContentView(R.layout.dialog_input_nilai);
        pd = inputNilai.findViewById(R.id.pb_inputNilai_dialog);
        namaSiswaDialog = inputNilai.findViewById(R.id.text_dialog_namaSiswa);
        kelasSiswaDialog = inputNilai.findViewById(R.id.text_dialog_kelasSiswa);
        TASiswaDialog = inputNilai.findViewById(R.id.text_dialog_TASiswa);
        SemesterSiswaDialog = inputNilai.findViewById(R.id.text_dialog_SemesterSiswa);
        mapelSiswaDialog = inputNilai.findViewById(R.id.text_dialog_mapelSiswa);
        nilaiUASDialog = inputNilai.findViewById(R.id.et_dialog_inputUas);
        nilaiUTSDialog = inputNilai.findViewById(R.id.et_dialog_inputUts);
        nilaiUH1Dialog = inputNilai.findViewById(R.id.et_dialog_inputUh1);
        nilaiUH2Dialog = inputNilai.findViewById(R.id.et_dialog_inputUh2);
        nilaiUH3Dialog = inputNilai.findViewById(R.id.et_dialog_inputUh3);
        nilaiUH4Dialog = inputNilai.findViewById(R.id.et_dialog_inputUh4);
        nilaiUH5Dialog = inputNilai.findViewById(R.id.et_dialog_inputUh5);
        btnOkDilaog = inputNilai.findViewById(R.id.btn_dialog_ok);
        btnCancelDialog = inputNilai.findViewById(R.id.btn_dialog_cancel);

        namaSiswaDialog.setText(listSiswa.get(pos).getNamaSiswa());
        kelasSiswaDialog.setText(kelas);
        TASiswaDialog.setText(TA);
        SemesterSiswaDialog.setText(semester);
        mapelSiswaDialog.setText(mapel);

        for (int i = 0; i < listSiswa.size(); i++) {
            if (listSiswa.get(i).getNamaSiswa().equals(listSiswa.get(pos).getNamaSiswa())) {
                NISSiswa = listSiswa.get(i).getNISSiswa();
            }
        }

        for (int i = 0; i < listMapel.size(); i++) {
            if (listMapel.get(i).getNamaMapel().equals(mapel)) {
                idMapel = listMapel.get(i).getIdMapel();
            }
        }

        getNilai(NIPGuru, NISSiswa, idMapel);

        btnOkDilaog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onResultUpdate == 1) {
                    validateUpdateNilai();
                } else {
                    validateNilai();
                }

            }
        });

        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Batal")
                        .setMessage("Apakah anda yakin batal menginputkan nilai?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                inputNilai.dismiss();
                            }
                        })
                        .setNegativeButton("Tidak", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        inputNilai.show();
        inputNilai.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void validateNilai() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Validasi Input Nilai")
                .setMessage("Apakah anda yakin data yang diinputkan sudah benar ?")
                .setNegativeButton("Belum", null)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postNilai();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void validateUpdateNilai() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Validasi Input Nilai")
                .setMessage("Apakah anda yakin data yang diinputkan sudah benar ?")
                .setNegativeButton("Belum", null)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateNilai();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getNilai(String NIPGuru, String NISSiswa, String idMapel) {
        pd.setIndeterminate(true);
        Call<loadNilai> getNilaiPlaceHolderCall = mApiInterface.getNilaiByParams(NIPGuru, NISSiswa, idMapel);
        getNilaiPlaceHolderCall.enqueue(new Callback<loadNilai>() {
            @Override
            public void onResponse(Call<loadNilai> call, Response<loadNilai> response) {
                if (response.isSuccessful()) {

                    if (response.body().getBundleData().size() > 0) {
                        onResultUpdate = 1;
                        for (int i = 0; i < response.body().getBundleData().size(); i++) {
                            listNilai.add(response.body().getBundleData().get(i));
                            nilaiUASDialog.setText(response.body().getBundleData().get(i).getUAS());
                            nilaiUTSDialog.setText(response.body().getBundleData().get(i).getUTS());
                            nilaiUH1Dialog.setText(response.body().getBundleData().get(i).getUH1());
                            nilaiUH2Dialog.setText(response.body().getBundleData().get(i).getUH2());
                            nilaiUH3Dialog.setText(response.body().getBundleData().get(i).getUH3());
                            nilaiUH4Dialog.setText(response.body().getBundleData().get(i).getUH4());
                            nilaiUH5Dialog.setText(response.body().getBundleData().get(i).getUH5());
                            pd.setIndeterminate(false);
                        }
                    } else {
                        pd.setIndeterminate(false);
                    }
                } else {
                    pd.setIndeterminate(false);
                    Toast.makeText(context, "Gagal: Nilai sebelumnya tidak berhasil ditampung", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<loadNilai> call, Throwable t) {
                Toast.makeText(context, "Gagal: Harap periksa jaringan internet ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postNilai() {
        pd.setIndeterminate(true);
        String uts, uas, uh1, uh2, uh3, uh4, uh5;
        uts = nilaiUTSDialog.getText().toString();
        uas = nilaiUASDialog.getText().toString();
        uh1 = nilaiUH1Dialog.getText().toString();
        uh2 = nilaiUH2Dialog.getText().toString();
        uh3 = nilaiUH3Dialog.getText().toString();
        uh4 = nilaiUH4Dialog.getText().toString();
        uh5 = nilaiUH5Dialog.getText().toString();
        final modelNilai thisNilai = new modelNilai(null, uts, uas, uh1, uh2, uh3, uh4, uh5, NISSiswa, NIPGuru, idMapel, semester);
        Call<loadNilai> postNilaiSiswa = mApiInterface.postNilai(thisNilai);
        postNilaiSiswa.enqueue(new Callback<loadNilai>() {
            @Override
            public void onResponse(Call<loadNilai> call, Response<loadNilai> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Data Nilai Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    pd.setIndeterminate(false);
                } else {
                    pd.setIndeterminate(false);
                    Toast.makeText(context, "Gagal: Penambahan Nilai tidak berhasil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadNilai> call, Throwable t) {
                pd.setIndeterminate(false);
                Toast.makeText(context, "Gagal: Harap periksa jaringan internet ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateNilai() {
        pd.setIndeterminate(true);
        String idNilai, uts, uas, uh1, uh2, uh3, uh4, uh5;

        if (listNilai.size() > 0) {
            for (int i = 0; i < listNilai.size(); i++) {
                idNilai = listNilai.get(i).getIdNilai();
            }
        }
        idNilai = listNilai.get(0).getIdNilai();
        uts = nilaiUTSDialog.getText().toString();
        uas = nilaiUASDialog.getText().toString();
        uh1 = nilaiUH1Dialog.getText().toString();
        uh2 = nilaiUH2Dialog.getText().toString();
        uh3 = nilaiUH3Dialog.getText().toString();
        uh4 = nilaiUH4Dialog.getText().toString();
        uh5 = nilaiUH5Dialog.getText().toString();

//        final modelNilai thisNilaiUpd = new modelNilai(idNilai ,uts, uas, uh1, uh2, uh3, uh4, uh5, NISSiswa, NIPGuru, idMapel, semester);
        Call<loadNilai> putNilaiSiswa = mApiInterface.putNilai(idNilai, uas, uts, uh1, uh2, uh3, uh4, uh5, NISSiswa, NIPGuru, idMapel, semester);
        putNilaiSiswa.enqueue(new Callback<loadNilai>() {
            @Override
            public void onResponse(Call<loadNilai> call, Response<loadNilai> response) {
                if (response.isSuccessful()) {
                    ArrayList<modelNilai> tes = response.body().getBundleData();

                    Toast.makeText(context, "Data Nilai Berhasil diperbarui", Toast.LENGTH_SHORT).show();

                    pd.setIndeterminate(false);
                } else {
                    Toast.makeText(context, "Gagal: Penambahan Nilai tidak berhasil", Toast.LENGTH_SHORT).show();
                    pd.setIndeterminate(false);
                }
            }

            @Override
            public void onFailure(Call<loadNilai> call, Throwable t) {
                Toast.makeText(context, "Gagal: Harap periksa jaringan internet ", Toast.LENGTH_SHORT).show();
                pd.setIndeterminate(false);
            }
        });
    }
}

package com.telu.eraporttelu.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.telu.eraporttelu.R;
import com.telu.eraporttelu.model.modelNilai;
import com.telu.eraporttelu.model.modelSiswa;
import com.telu.eraporttelu.response.loadNilai;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class siswaAdapter extends RecyclerView.Adapter<siswaAdapter.ViewHolder> {

    private static final String TAG = "siswaAdapter";
    private String kelas, TA, semester, mapel, NIPGuru,NISSiswa;
    private TextView namaSiswaDialog,kelasSiswaDialog, TASiswaDialog, SemesterSiswaDialog, mapelSiswaDialog ;
    private Context context;
    private ProgressDialog pd;
    private EditText nilaiUASDialog, nilaiUTSDialog, nilaiUH1Dialog, nilaiUH2Dialog,nilaiUH3Dialog,nilaiUH4Dialog,nilaiUH5Dialog;
    private ArrayList<modelSiswa> listSiswa;

    APIInterface mApiInterface;

    public siswaAdapter(String kelas, String TA, String semester, String mapel, Context context, ArrayList<modelSiswa> listSiswa) {
        this.kelas = kelas;
        this.TA = TA;
        this.semester = semester;
        this.mapel = mapel;
        this.context = context;
        this.listSiswa = listSiswa;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_siswa,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.namaSiswa.setText(listSiswa.get(position).getNama());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = listSiswa.get(position).getNama();
                openInputNilaiDialog(nama);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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

    private void openInputNilaiDialog(String nama){
        Button btnOkDilaog,btnCancelDialog;
        pd = new ProgressDialog(context);
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        final Dialog inputNilai = new Dialog(context);
        inputNilai.setCancelable(false);
        inputNilai.setCanceledOnTouchOutside(false);

        inputNilai.setContentView(R.layout.dialog_input_nilai);
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

        namaSiswaDialog.setText(nama);
        kelasSiswaDialog.setText(kelas);
        TASiswaDialog.setText(TA);
        SemesterSiswaDialog.setText(semester);
        mapelSiswaDialog.setText(mapel);
        NIPGuru = "11223344";
        NISSiswa = "22";
        mapel = "2";
        semester = "GANJIL";

        btnOkDilaog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Button Ok Test Working", Toast.LENGTH_SHORT).show();
                postNilai();
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
                        .setNegativeButton("Tidak",null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        inputNilai.show();
        inputNilai.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void postNilai(){
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        String uts,uas,uh1,uh2,uh3,uh4,uh5;
        uts = nilaiUTSDialog.getText().toString();
        uas = nilaiUASDialog.getText().toString();
        uh1 = nilaiUH1Dialog.getText().toString();
        uh2 = nilaiUH2Dialog.getText().toString();
        uh3 = nilaiUH3Dialog.getText().toString();
        uh4 = nilaiUH4Dialog.getText().toString();
        uh5 = nilaiUH5Dialog.getText().toString();
        final modelNilai thisNilai = new modelNilai(uts,uas,uh1,uh2,uh3,uh4,uh5,NISSiswa,NIPGuru,mapel,semester);
        Call<loadNilai> postNilaiSiswa = mApiInterface.postNilai(thisNilai);
        postNilaiSiswa.enqueue(new Callback<loadNilai>() {
            @Override
            public void onResponse(Call<loadNilai> call, Response<loadNilai> response) {
                if (response.isSuccessful()){
                    pd.cancel();
                    Toast.makeText(context, "message: "+response.body().getMessage() , Toast.LENGTH_SHORT).show();
                }else {
                    pd.cancel();
                    Toast.makeText(context, "Error 1", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<loadNilai> call, Throwable t) {
                pd.cancel();
                Toast.makeText(context, "Error 2: "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

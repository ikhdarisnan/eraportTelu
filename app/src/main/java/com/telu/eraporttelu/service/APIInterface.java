package com.telu.eraporttelu.service;

import com.telu.eraporttelu.model.modelNilai;
import com.telu.eraporttelu.response.loadGuru;
import com.telu.eraporttelu.response.loadKelas;
import com.telu.eraporttelu.response.loadLogin;
import com.telu.eraporttelu.response.loadMapel;
import com.telu.eraporttelu.response.loadNilai;
import com.telu.eraporttelu.response.loadSiswa;
import com.telu.eraporttelu.response.loadTA;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("Guru/{NIPGuru}")
    Call<loadGuru> getDataGuru(@Path("NIPGuru")String NIPGuru);

    @GET("Guru/kelas/{NIPGuru}")
    Call<loadKelas> getDataKelas(@Path("NIPGuru")String NIPGuru);

    @GET("Guru/mapel/{NIPGuru}")
    Call<loadMapel> getDataMapel(@Path("NIPGuru")String NIPGuru);

    @GET("Ta")
    Call<loadTA> getAllDataTA();

    @GET("Nilai/siswaGuru")
    Call<loadNilai> getNilaiByParams(
            @Query("NIPGuru") String NIPGuru,
            @Query("NISSiswa") String NISSiswa,
            @Query("idMapel") String idMapel
            );

    @POST("Nilai/siswaGuru")
    Call<loadNilai> postNilai(@Body modelNilai objNilai);

    @GET("Siswa/kelas/{namaKelas}")
    Call<loadSiswa> getSiswa(@Path("namaKelas")String namaKelas);

    @GET("Login")
    Call<loadLogin> onCallLogin(
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("Siswa/{NISSiswa}")
    Call<loadSiswa> getDataSiswa(@Path("NISSiswa")String NISSiswa);
}

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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @FormUrlEncoded
    @PUT("Nilai")
    Call<loadNilai> putNilai(
            @Field("idNilai") String idNilai,
            @Field("UAS") String uas,
            @Field("UTS") String uts,
            @Field("UH1") String uh1,
            @Field("UH2") String uh2,
            @Field("UH3") String uh3,
            @Field("UH4") String uh4,
            @Field("UH5") String uh5,
            @Field("NISSiswa") String nisSiswa,
            @Field("NIPGuru") String NIPGuru,
            @Field("idMapel") String idMapel,
            @Field("semester") String semester
    );
}

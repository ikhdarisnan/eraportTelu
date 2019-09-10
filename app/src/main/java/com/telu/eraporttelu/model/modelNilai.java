package com.telu.eraporttelu.model;

public class modelNilai {

    private String idNilai;
    private String UTS;
    private String UAS;
    private String UH1;
    private String UH2;
    private String UH3;
    private String UH4;
    private String UH5;
    private String NISSiswa;
    private String NIPGuru;
    private String idMapel;
    private String semester;

    public modelNilai(String idNilai, String UTS, String UAS, String UH1, String UH2, String UH3, String UH4, String UH5, String NISSiswa, String NIPGuru, String idMapel, String semester) {
        this.idNilai = idNilai;
        this.UTS = UTS;
        this.UAS = UAS;
        this.UH1 = UH1;
        this.UH2 = UH2;
        this.UH3 = UH3;
        this.UH4 = UH4;
        this.UH5 = UH5;
        this.NISSiswa = NISSiswa;
        this.NIPGuru = NIPGuru;
        this.idMapel = idMapel;
        this.semester = semester;
    }

    public String getIdNilai() {
        return idNilai;
    }

    public void setIdNilai(String idNilai) {
        this.idNilai = idNilai;
    }

    public String getUTS() {
        return UTS;
    }

    public void setUTS(String UTS) {
        this.UTS = UTS;
    }

    public String getUAS() {
        return UAS;
    }

    public void setUAS(String UAS) {
        this.UAS = UAS;
    }

    public String getUH1() {
        return UH1;
    }

    public void setUH1(String UH1) {
        this.UH1 = UH1;
    }

    public String getUH2() {
        return UH2;
    }

    public void setUH2(String UH2) {
        this.UH2 = UH2;
    }

    public String getUH3() {
        return UH3;
    }

    public void setUH3(String UH3) {
        this.UH3 = UH3;
    }

    public String getUH4() {
        return UH4;
    }

    public void setUH4(String UH4) {
        this.UH4 = UH4;
    }

    public String getUH5() {
        return UH5;
    }

    public void setUH5(String UH5) {
        this.UH5 = UH5;
    }

    public String getNISSiswa() {
        return NISSiswa;
    }

    public void setNISSiswa(String NISSiswa) {
        this.NISSiswa = NISSiswa;
    }

    public String getNIPGuru() {
        return NIPGuru;
    }

    public void setNIPGuru(String NIPGuru) {
        this.NIPGuru = NIPGuru;
    }

    public String getIdMapel() {
        return idMapel;
    }

    public void setIdMapel(String idMapel) {
        this.idMapel = idMapel;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}

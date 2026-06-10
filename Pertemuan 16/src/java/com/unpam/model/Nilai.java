package com.unpam.model;

public class Nilai {

    private String nim;
    private String nama;
    private String kodeMatkul;
    private String namaMatkul;
    private double tugas;
    private double uts;
    private double uas;
    private double nilaiAkhir;
    private String grade;

    // Constructor
    public Nilai() {

    }

    // Getter dan Setter NIM
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    // Getter dan Setter Nama
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter dan Setter Kode Matkul
    public String getKodeMatkul() {
        return kodeMatkul;
    }

    public void setKodeMatkul(String kodeMatkul) {
        this.kodeMatkul = kodeMatkul;
    }

    // Getter dan Setter Nama Matkul
    public String getNamaMatkul() {
        return namaMatkul;
    }

    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }

    // Getter dan Setter Tugas
    public double getTugas() {
        return tugas;
    }

    public void setTugas(double tugas) {
        this.tugas = tugas;
    }

    // Getter dan Setter UTS
    public double getUts() {
        return uts;
    }

    public void setUts(double uts) {
        this.uts = uts;
    }

    // Getter dan Setter UAS
    public double getUas() {
        return uas;
    }

    public void setUas(double uas) {
        this.uas = uas;
    }

    // Getter dan Setter Nilai Akhir
    public double getNilaiAkhir() {
        return nilaiAkhir;
    }

    public void setNilaiAkhir(double nilaiAkhir) {
        this.nilaiAkhir = nilaiAkhir;
    }

    // Getter dan Setter Grade
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
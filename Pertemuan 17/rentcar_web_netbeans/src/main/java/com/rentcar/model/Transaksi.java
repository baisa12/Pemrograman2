package com.rentcar.model;

import java.sql.Date;

public class Transaksi {
    private int idTransaksi;
    private int idMobil;
    private int idCustomer;
    private String platNomor;
    private String merk;
    private String modelMobil;
    private String namaCustomer;
    private Date tanggalSewa;
    private Date tanggalRencanaKembali;
    private Date tanggalKembali;
    private double totalBiaya;
    private double denda;
    private String status;

    public Transaksi() {
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getIdMobil() {
        return idMobil;
    }

    public void setIdMobil(int idMobil) {
        this.idMobil = idMobil;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModelMobil() {
        return modelMobil;
    }

    public void setModelMobil(String modelMobil) {
        this.modelMobil = modelMobil;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public Date getTanggalSewa() {
        return tanggalSewa;
    }

    public void setTanggalSewa(Date tanggalSewa) {
        this.tanggalSewa = tanggalSewa;
    }

    public Date getTanggalRencanaKembali() {
        return tanggalRencanaKembali;
    }

    public void setTanggalRencanaKembali(Date tanggalRencanaKembali) {
        this.tanggalRencanaKembali = tanggalRencanaKembali;
    }

    public Date getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(double totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public double getDenda() {
        return denda;
    }

    public void setDenda(double denda) {
        this.denda = denda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.rentcar.model;

public class Customer {
    private int idCustomer;
    private String nama;
    private String noKtp;
    private String noHp;
    private String alamat;

    public Customer() {
    }

    public Customer(int idCustomer, String nama, String noKtp, String noHp, String alamat) {
        this.idCustomer = idCustomer;
        this.nama = nama;
        this.noKtp = noKtp;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}

package com.unpam.view;

public class PesanDialog {

    public void tampilkanPesan(String pesan, String judul) {
        System.out.println(judul + ": " + pesan);
    }

    public int tampilkanPilihan(String pesan, String judul, Object[] pilihan) {
        System.out.println(judul + ": " + pesan);
        return 0;
    }
}
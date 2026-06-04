package manajemenproduk;

public class Produk {
    String nama;
    int harga;

    public Produk(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    @Override
    public String toString() {
        return nama + " - Rp." + harga;
    }
}
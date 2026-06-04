package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MataKuliah {

    private String kodeMataKuliah;
    private String namaMataKuliah;
    private int jumlahSKS;
    private String pesan;
    private Object[][] list;

    private final Koneksi koneksi = new Koneksi();

    public String getKodeMataKuliah() {
        return kodeMataKuliah;
    }

    public void setKodeMataKuliah(String kodeMataKuliah) {
        this.kodeMataKuliah = kodeMataKuliah;
    }

    public String getNamaMataKuliah() {
        return namaMataKuliah;
    }

    public void setNamaMataKuliah(String namaMataKuliah) {
        this.namaMataKuliah = namaMataKuliah;
    }

    public int getJumlahSKS() {
        return jumlahSKS;
    }

    public void setJumlahSKS(int jumlahSKS) {
        this.jumlahSKS = jumlahSKS;
    }

    public String getPesan() {
        return pesan;
    }

    public Object[][] getList() {
        return list;
    }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "INSERT INTO tbmatakuliah(kodematakuliah, namamatakuliah, jumlahsks) "
                        + "VALUES (?, ?, ?) "
                        + "ON DUPLICATE KEY UPDATE "
                        + "namamatakuliah = VALUES(namamatakuliah), "
                        + "jumlahsks = VALUES(jumlahsks)";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, kodeMataKuliah);
                ps.setString(2, namaMataKuliah);
                ps.setInt(3, jumlahSKS);

                ps.executeUpdate();

                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat menyimpan data mata kuliah\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    public boolean baca(String kodeMataKuliah) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "SELECT kodematakuliah, namamatakuliah, jumlahsks "
                        + "FROM tbmatakuliah WHERE kodematakuliah = ?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, kodeMataKuliah);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    this.kodeMataKuliah = rs.getString("kodematakuliah");
                    this.namaMataKuliah = rs.getString("namamatakuliah");
                    this.jumlahSKS = rs.getInt("jumlahsks");
                } else {
                    adaKesalahan = true;
                    pesan = "Data mata kuliah tidak ditemukan";
                }

                rs.close();
                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca data mata kuliah\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    public boolean hapus(String kodeMataKuliah) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "DELETE FROM tbmatakuliah WHERE kodematakuliah = ?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, kodeMataKuliah);

                int jumlah = ps.executeUpdate();

                if (jumlah < 1) {
                    adaKesalahan = true;
                    pesan = "Data mata kuliah tidak ditemukan";
                }

                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat menghapus data mata kuliah\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    public boolean bacaData(int mulai, int jumlah) {
        boolean adaKesalahan = false;
        Connection connection;
        list = new Object[0][0];

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "SELECT kodematakuliah, namamatakuliah, jumlahsks FROM tbmatakuliah "
                        + "ORDER BY kodematakuliah LIMIT " + mulai + ", " + jumlah;

                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                ArrayList<Object[]> data = new ArrayList<Object[]>();

                while (rs.next()) {
                    Object[] row = new Object[3];
                    row[0] = rs.getString("kodematakuliah");
                    row[1] = rs.getString("namamatakuliah");
                    row[2] = rs.getInt("jumlahsks");
                    data.add(row);
                }

                list = new Object[data.size()][3];

                for (int i = 0; i < data.size(); i++) {
                    list[i] = data.get(i);
                }

                rs.close();
                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca daftar mata kuliah\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
}
package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mahasiswa {

    private String nim;
    private String nama;
    private int semester;
    private String kelas;
    private String pesan;
    private Object[][] list;

    private final Koneksi koneksi = new Koneksi();

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
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
                sql = "INSERT INTO tbmahasiswa(nim, nama, semester, kelas) "
                        + "VALUES (?, ?, ?, ?) "
                        + "ON DUPLICATE KEY UPDATE "
                        + "nama = VALUES(nama), "
                        + "semester = VALUES(semester), "
                        + "kelas = VALUES(kelas)";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nim);
                ps.setString(2, nama);
                ps.setInt(3, semester);
                ps.setString(4, kelas);

                ps.executeUpdate();

                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat menyimpan data mahasiswa\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    public boolean baca(String nim) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "SELECT nim, nama, semester, kelas FROM tbmahasiswa WHERE nim = ?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nim);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    this.nim = rs.getString("nim");
                    this.nama = rs.getString("nama");
                    this.semester = rs.getInt("semester");
                    this.kelas = rs.getString("kelas");
                } else {
                    adaKesalahan = true;
                    pesan = "Data mahasiswa tidak ditemukan";
                }

                rs.close();
                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca data mahasiswa\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    public boolean hapus(String nim) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "DELETE FROM tbmahasiswa WHERE nim = ?";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nim);

                int jumlah = ps.executeUpdate();

                if (jumlah < 1) {
                    adaKesalahan = true;
                    pesan = "Data mahasiswa tidak ditemukan";
                }

                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat menghapus data mahasiswa\n" + ex + "\n" + sql;
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
                sql = "SELECT nim, nama, semester, kelas FROM tbmahasiswa "
                        + "ORDER BY nim LIMIT " + mulai + ", " + jumlah;

                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                ArrayList<Object[]> data = new ArrayList<Object[]>();

                while (rs.next()) {
                    Object[] row = new Object[4];
                    row[0] = rs.getString("nim");
                    row[1] = rs.getString("nama");
                    row[2] = rs.getInt("semester");
                    row[3] = rs.getString("kelas");
                    data.add(row);
                }

                list = new Object[data.size()][4];

                for (int i = 0; i < data.size(); i++) {
                    list[i] = data.get(i);
                }

                rs.close();
                ps.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca daftar mahasiswa\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
}
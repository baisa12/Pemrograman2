package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Nilai {

    private String nim;
    private String nama;
    private int semester;
    private String kelas;

    private String kodeMataKuliah;
    private String namaMataKuliah;
    private int jumlahSKS;

    private int nilaiTugas;
    private int nilaiUTS;
    private int nilaiUAS;

    private String pesan;

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

    public int getSemester() {
        return semester;
    }

    public String getKelas() {
        return kelas;
    }

    public String getKodeMataKuliah() {
        return kodeMataKuliah;
    }

    public void setKodeMataKuliah(String kodeMataKuliah) {
        this.kodeMataKuliah = kodeMataKuliah;
    }

    public String getNamaMataKuliah() {
        return namaMataKuliah;
    }

    public int getJumlahSKS() {
        return jumlahSKS;
    }

    public int getNilaiTugas() {
        return nilaiTugas;
    }

    public void setNilaiTugas(int nilaiTugas) {
        this.nilaiTugas = nilaiTugas;
    }

    public int getNilaiUTS() {
        return nilaiUTS;
    }

    public void setNilaiUTS(int nilaiUTS) {
        this.nilaiUTS = nilaiUTS;
    }

    public int getNilaiUAS() {
        return nilaiUAS;
    }

    public void setNilaiUAS(int nilaiUAS) {
        this.nilaiUAS = nilaiUAS;
    }

    public String getPesan() {
        return pesan;
    }

    public boolean bacaMahasiswa(String nim) {
        boolean adaKesalahan = false;
        Connection connection;

        this.nim = "";
        this.nama = "";
        this.semester = 0;
        this.kelas = "";

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "SELECT nim, nama, semester, kelas FROM tbmahasiswa WHERE nim = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nim);

                ResultSet rset = preparedStatement.executeQuery();

                if (rset.next()) {
                    this.nim = rset.getString("nim");
                    this.nama = rset.getString("nama");
                    this.semester = rset.getInt("semester");
                    this.kelas = rset.getString("kelas");
                } else {
                    adaKesalahan = true;
                    pesan = "Data mahasiswa dengan NIM " + nim + " tidak ditemukan";
                }

                rset.close();
                preparedStatement.close();
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

    public boolean bacaMataKuliah(String kodeMataKuliah) {
        boolean adaKesalahan = false;
        Connection connection;

        this.kodeMataKuliah = "";
        this.namaMataKuliah = "";
        this.jumlahSKS = 0;

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "SELECT kodematakuliah, namamatakuliah, jumlahsks "
                        + "FROM tbmatakuliah WHERE kodematakuliah = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, kodeMataKuliah);

                ResultSet rset = preparedStatement.executeQuery();

                if (rset.next()) {
                    this.kodeMataKuliah = rset.getString("kodematakuliah");
                    this.namaMataKuliah = rset.getString("namamatakuliah");
                    this.jumlahSKS = rset.getInt("jumlahsks");
                } else {
                    adaKesalahan = true;
                    pesan = "Data mata kuliah dengan kode " + kodeMataKuliah + " tidak ditemukan";
                }

                rset.close();
                preparedStatement.close();
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

    public boolean bacaNilai(String nim, String kodeMataKuliah) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "SELECT m.nim, m.nama, m.semester, m.kelas, "
                        + "mk.kodematakuliah, mk.namamatakuliah, mk.jumlahsks, "
                        + "n.nilaitugas, n.nilaiuts, n.nilaiuas "
                        + "FROM tbnilai n "
                        + "INNER JOIN tbmahasiswa m ON n.nim = m.nim "
                        + "INNER JOIN tbmatakuliah mk ON n.kodematakuliah = mk.kodematakuliah "
                        + "WHERE n.nim = ? AND n.kodematakuliah = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nim);
                preparedStatement.setString(2, kodeMataKuliah);

                ResultSet rset = preparedStatement.executeQuery();

                if (rset.next()) {
                    this.nim = rset.getString("nim");
                    this.nama = rset.getString("nama");
                    this.semester = rset.getInt("semester");
                    this.kelas = rset.getString("kelas");
                    this.kodeMataKuliah = rset.getString("kodematakuliah");
                    this.namaMataKuliah = rset.getString("namamatakuliah");
                    this.jumlahSKS = rset.getInt("jumlahsks");
                    this.nilaiTugas = rset.getInt("nilaitugas");
                    this.nilaiUTS = rset.getInt("nilaiuts");
                    this.nilaiUAS = rset.getInt("nilaiuas");
                } else {
                    adaKesalahan = true;
                    pesan = "Data nilai tidak ditemukan";
                }

                rset.close();
                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca data nilai\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "INSERT INTO tbnilai(nim, kodematakuliah, nilaitugas, nilaiuts, nilaiuas) "
                        + "VALUES (?, ?, ?, ?, ?) "
                        + "ON DUPLICATE KEY UPDATE "
                        + "nilaitugas = VALUES(nilaitugas), "
                        + "nilaiuts = VALUES(nilaiuts), "
                        + "nilaiuas = VALUES(nilaiuas)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nim);
                preparedStatement.setString(2, kodeMataKuliah);
                preparedStatement.setInt(3, nilaiTugas);
                preparedStatement.setInt(4, nilaiUTS);
                preparedStatement.setInt(5, nilaiUAS);

                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat menyimpan data nilai\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    public boolean hapus(String nim, String kodeMataKuliah) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String sql = "";

            try {
                sql = "DELETE FROM tbnilai WHERE nim = ? AND kodematakuliah = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nim);
                preparedStatement.setString(2, kodeMataKuliah);

                int hasil = preparedStatement.executeUpdate();

                if (hasil < 1) {
                    adaKesalahan = true;
                    pesan = "Data nilai tidak ditemukan";
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat menghapus data nilai\n" + ex + "\n" + sql;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
}
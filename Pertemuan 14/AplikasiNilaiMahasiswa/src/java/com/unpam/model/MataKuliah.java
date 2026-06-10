package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MataKuliah {

    private String kodeMataKuliah;
    private String namaMataKuliah;
    private int jumlahSks;
    private String pesan = "";
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

    public int getJumlahSks() {
        return jumlahSks;
    }

    public void setJumlahSks(int jumlahSks) {
        this.jumlahSks = jumlahSks;
    }

    public String getPesan() {
        return pesan;
    }

    public Object[][] getList() {
        return list;
    }

    public void setList(Object[][] list) {
        this.list = list;
    }

    public boolean simpan() {
        boolean berhasil = false;
        Connection connection = koneksi.getConnection();

        if (connection == null) {
            pesan = "Tidak dapat melakukan koneksi ke server. " + koneksi.getPesanKesalahan();
            return false;
        }

        String cekSql = "SELECT kode_mata_kuliah FROM tbmatakuliah WHERE kode_mata_kuliah = ?";
        String insertSql = "INSERT INTO tbmatakuliah(kode_mata_kuliah, nama_mata_kuliah, jumlah_sks) VALUES (?, ?, ?)";
        String updateSql = "UPDATE tbmatakuliah SET nama_mata_kuliah = ?, jumlah_sks = ? WHERE kode_mata_kuliah = ?";

        try (PreparedStatement cekStatement = connection.prepareStatement(cekSql)) {
            cekStatement.setString(1, kodeMataKuliah);

            try (ResultSet resultSet = cekStatement.executeQuery()) {
                if (resultSet.next()) {
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                        updateStatement.setString(1, namaMataKuliah);
                        updateStatement.setInt(2, jumlahSks);
                        updateStatement.setString(3, kodeMataKuliah);

                        berhasil = updateStatement.executeUpdate() > 0;
                    }
                } else {
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                        insertStatement.setString(1, kodeMataKuliah);
                        insertStatement.setString(2, namaMataKuliah);
                        insertStatement.setInt(3, jumlahSks);

                        berhasil = insertStatement.executeUpdate() > 0;
                    }
                }
            }

            if (!berhasil) {
                pesan = "Gagal menyimpan data mata kuliah.";
            }
        } catch (Exception ex) {
            pesan = "Tidak dapat menyimpan data mata kuliah: " + ex.getMessage();
            berhasil = false;
        } finally {
            try {
                connection.close();
            } catch (Exception ignored) {
            }
        }

        return berhasil;
    }

    public boolean baca(String kodeDicari) {
        boolean ditemukan = false;
        Connection connection = koneksi.getConnection();

        if (connection == null) {
            pesan = "Tidak dapat melakukan koneksi ke server. " + koneksi.getPesanKesalahan();
            return false;
        }

        String sql = "SELECT kode_mata_kuliah, nama_mata_kuliah, jumlah_sks FROM tbmatakuliah WHERE kode_mata_kuliah = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, kodeDicari);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    kodeMataKuliah = resultSet.getString("kode_mata_kuliah");
                    namaMataKuliah = resultSet.getString("nama_mata_kuliah");
                    jumlahSks = resultSet.getInt("jumlah_sks");
                    ditemukan = true;
                } else {
                    pesan = "Data mata kuliah dengan kode " + kodeDicari + " tidak ditemukan.";
                }
            }
        } catch (Exception ex) {
            pesan = "Tidak dapat membaca data mata kuliah: " + ex.getMessage();
            ditemukan = false;
        } finally {
            try {
                connection.close();
            } catch (Exception ignored) {
            }
        }

        return ditemukan;
    }

    public boolean hapus(String kodeHapus) {
        boolean berhasil = false;
        Connection connection = koneksi.getConnection();

        if (connection == null) {
            pesan = "Tidak dapat melakukan koneksi ke server. " + koneksi.getPesanKesalahan();
            return false;
        }

        String sql = "DELETE FROM tbmatakuliah WHERE kode_mata_kuliah = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, kodeHapus);
            berhasil = preparedStatement.executeUpdate() > 0;

            if (!berhasil) {
                pesan = "Data mata kuliah tidak ditemukan atau gagal dihapus.";
            }
        } catch (Exception ex) {
            pesan = "Tidak dapat menghapus data mata kuliah: " + ex.getMessage();
            berhasil = false;
        } finally {
            try {
                connection.close();
            } catch (Exception ignored) {
            }
        }

        return berhasil;
    }

    public boolean bacaData() {
        boolean berhasil = false;
        Connection connection = koneksi.getConnection();

        if (connection == null) {
            pesan = "Tidak dapat melakukan koneksi ke server. " + koneksi.getPesanKesalahan();
            return false;
        }

        String sql = "SELECT kode_mata_kuliah, nama_mata_kuliah, jumlah_sks FROM tbmatakuliah ORDER BY kode_mata_kuliah";
        List<Object[]> data = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getString("kode_mata_kuliah");
                row[1] = resultSet.getString("nama_mata_kuliah");
                row[2] = resultSet.getInt("jumlah_sks");
                data.add(row);
            }

            list = data.toArray(new Object[0][0]);
            berhasil = true;
        } catch (Exception ex) {
            pesan = "Tidak dapat membaca daftar mata kuliah: " + ex.getMessage();
            berhasil = false;
        } finally {
            try {
                connection.close();
            } catch (Exception ignored) {
            }
        }

        return berhasil;
    }
}
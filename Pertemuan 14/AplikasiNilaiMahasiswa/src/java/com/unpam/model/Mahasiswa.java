package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Mahasiswa {

    private String nim;
    private String nama;
    private String kelas;
    private String password;
    private int semester;
    private String pesan = "";
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

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

        String cekSql = "SELECT nim FROM tbmahasiswa WHERE nim = ?";
        String insertSql = "INSERT INTO tbmahasiswa(nim, nama, semester, kelas, password) VALUES (?, ?, ?, ?, ?)";
        String updateSql = "UPDATE tbmahasiswa SET nama = ?, semester = ?, kelas = ?, password = ? WHERE nim = ?";

        try (
                PreparedStatement cekStatement = connection.prepareStatement(cekSql)
        ) {
            cekStatement.setString(1, nim);

            try (ResultSet resultSet = cekStatement.executeQuery()) {
                if (resultSet.next()) {
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                        updateStatement.setString(1, nama);
                        updateStatement.setInt(2, semester);
                        updateStatement.setString(3, kelas);
                        updateStatement.setString(4, password);
                        updateStatement.setString(5, nim);

                        berhasil = updateStatement.executeUpdate() > 0;
                    }
                } else {
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                        insertStatement.setString(1, nim);
                        insertStatement.setString(2, nama);
                        insertStatement.setInt(3, semester);
                        insertStatement.setString(4, kelas);
                        insertStatement.setString(5, password);

                        berhasil = insertStatement.executeUpdate() > 0;
                    }
                }
            }

            if (!berhasil) {
                pesan = "Gagal menyimpan data mahasiswa.";
            }
        } catch (Exception ex) {
            pesan = "Tidak dapat menyimpan data mahasiswa: " + ex.getMessage();
            berhasil = false;
        } finally {
            try {
                connection.close();
            } catch (Exception ignored) {
            }
        }

        return berhasil;
    }

    public boolean baca(String nimDicari) {
        boolean ditemukan = false;
        Connection connection = koneksi.getConnection();

        if (connection == null) {
            pesan = "Tidak dapat melakukan koneksi ke server. " + koneksi.getPesanKesalahan();
            return false;
        }

        String sql = "SELECT nim, nama, semester, kelas, password FROM tbmahasiswa WHERE nim = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nimDicari);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    nim = resultSet.getString("nim");
                    nama = resultSet.getString("nama");
                    semester = resultSet.getInt("semester");
                    kelas = resultSet.getString("kelas");
                    password = resultSet.getString("password");
                    ditemukan = true;
                } else {
                    pesan = "Data mahasiswa dengan NIM " + nimDicari + " tidak ditemukan.";
                }
            }
        } catch (Exception ex) {
            pesan = "Tidak dapat membaca data mahasiswa: " + ex.getMessage();
            ditemukan = false;
        } finally {
            try {
                connection.close();
            } catch (Exception ignored) {
            }
        }

        return ditemukan;
    }

    public boolean hapus(String nimHapus) {
        boolean berhasil = false;
        Connection connection = koneksi.getConnection();

        if (connection == null) {
            pesan = "Tidak dapat melakukan koneksi ke server. " + koneksi.getPesanKesalahan();
            return false;
        }

        String sql = "DELETE FROM tbmahasiswa WHERE nim = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nimHapus);
            berhasil = preparedStatement.executeUpdate() > 0;

            if (!berhasil) {
                pesan = "Data mahasiswa tidak ditemukan atau gagal dihapus.";
            }
        } catch (Exception ex) {
            pesan = "Tidak dapat menghapus data mahasiswa: " + ex.getMessage();
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

        String sql = "SELECT nim, nama, semester, kelas FROM tbmahasiswa ORDER BY nim";
        List<Object[]> data = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getString("nim");
                row[1] = resultSet.getString("nama");
                row[2] = resultSet.getInt("semester");
                row[3] = resultSet.getString("kelas");
                data.add(row);
            }

            list = data.toArray(new Object[0][0]);
            berhasil = true;
        } catch (Exception ex) {
            pesan = "Tidak dapat membaca daftar mahasiswa: " + ex.getMessage();
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
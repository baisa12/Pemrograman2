package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE = "jdbc:mysql://localhost:3306/dbaplikasipenilaianmahasiswa"
            + "?useSSL=false"
            + "&serverTimezone=Asia/Jakarta"
            + "&allowPublicKeyRetrieval=true";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private String pesanKesalahan = "";

    public String getPesanKesalahan() {
        return pesanKesalahan;
    }

    public Connection getConnection() {
        pesanKesalahan = "";

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(DATABASE, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            pesanKesalahan = "JDBC Driver tidak ditemukan atau rusak: " + ex.getMessage();
        } catch (SQLException ex) {
            pesanKesalahan = "Koneksi ke database gagal: " + ex.getMessage();
        }

        return null;
    }
}
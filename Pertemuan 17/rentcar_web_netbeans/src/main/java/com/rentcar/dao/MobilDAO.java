package com.rentcar.dao;

import com.rentcar.config.DatabaseConnection;
import com.rentcar.model.Mobil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MobilDAO {

    public List<Mobil> getAllMobil() throws SQLException {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil ORDER BY id_mobil DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    public List<Mobil> getMobilTersedia() throws SQLException {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil WHERE status = 'TERSEDIA' ORDER BY merk ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    public Mobil getMobilById(int idMobil) throws SQLException {
        String sql = "SELECT * FROM mobil WHERE id_mobil = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMobil);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }
        return null;
    }

    public void insertMobil(Mobil mobil) throws SQLException {
        String sql = "INSERT INTO mobil (plat_nomor, merk, model, tahun, harga_sewa, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobil.getPlatNomor());
            ps.setString(2, mobil.getMerk());
            ps.setString(3, mobil.getModel());
            ps.setInt(4, mobil.getTahun());
            ps.setDouble(5, mobil.getHargaSewa());
            ps.setString(6, mobil.getStatus());
            ps.executeUpdate();
        }
    }

    public void updateMobil(Mobil mobil) throws SQLException {
        String sql = "UPDATE mobil SET plat_nomor = ?, merk = ?, model = ?, tahun = ?, harga_sewa = ?, status = ? WHERE id_mobil = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobil.getPlatNomor());
            ps.setString(2, mobil.getMerk());
            ps.setString(3, mobil.getModel());
            ps.setInt(4, mobil.getTahun());
            ps.setDouble(5, mobil.getHargaSewa());
            ps.setString(6, mobil.getStatus());
            ps.setInt(7, mobil.getIdMobil());
            ps.executeUpdate();
        }
    }

    public void deleteMobil(int idMobil) throws SQLException {
        String sql = "DELETE FROM mobil WHERE id_mobil = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMobil);
            ps.executeUpdate();
        }
    }

    private Mobil mapResultSet(ResultSet rs) throws SQLException {
        return new Mobil(
                rs.getInt("id_mobil"),
                rs.getString("plat_nomor"),
                rs.getString("merk"),
                rs.getString("model"),
                rs.getInt("tahun"),
                rs.getDouble("harga_sewa"),
                rs.getString("status")
        );
    }
}

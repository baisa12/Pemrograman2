package com.rentcar.dao;

import com.rentcar.config.DatabaseConnection;
import com.rentcar.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> getAllCustomer() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY id_customer DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    public Customer getCustomerById(int idCustomer) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id_customer = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCustomer);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        }
        return null;
    }

    public void insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (nama, no_ktp, no_hp, alamat) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getNama());
            ps.setString(2, customer.getNoKtp());
            ps.setString(3, customer.getNoHp());
            ps.setString(4, customer.getAlamat());
            ps.executeUpdate();
        }
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET nama = ?, no_ktp = ?, no_hp = ?, alamat = ? WHERE id_customer = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getNama());
            ps.setString(2, customer.getNoKtp());
            ps.setString(3, customer.getNoHp());
            ps.setString(4, customer.getAlamat());
            ps.setInt(5, customer.getIdCustomer());
            ps.executeUpdate();
        }
    }

    public void deleteCustomer(int idCustomer) throws SQLException {
        String sql = "DELETE FROM customer WHERE id_customer = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCustomer);
            ps.executeUpdate();
        }
    }

    private Customer mapResultSet(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("id_customer"),
                rs.getString("nama"),
                rs.getString("no_ktp"),
                rs.getString("no_hp"),
                rs.getString("alamat")
        );
    }
}

package Pertemuan9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiPenjualan {
    private static Connection mysqlconfig;
    
    public static Connection configDB() throws SQLException {
        try {
            // Mengarahkan ke database yang baru kamu buat
            String url = "jdbc:mysql://localhost:3306/db_penjualan"; 
            String user = "root"; // user default XAMPP
            String pass = ""; // password default XAMPP (kosong)
            
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url, user, pass);
            
        } catch (Exception e) {
            System.err.println("Koneksi gagal: " + e.getMessage());
        }
        return mysqlconfig;
    }
}
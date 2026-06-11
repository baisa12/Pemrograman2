package com.rentcar.dao;

import com.rentcar.config.DatabaseConnection;
import com.rentcar.model.Transaksi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {
    private static final double DENDA_PER_HARI = 50000;

    public List<Transaksi> getAllTransaksi() throws SQLException {
        String sql = "SELECT t.*, m.plat_nomor, m.merk, m.model, c.nama " +
                "FROM transaksi t " +
                "JOIN mobil m ON t.id_mobil = m.id_mobil " +
                "JOIN customer c ON t.id_customer = c.id_customer " +
                "ORDER BY t.id_transaksi DESC";
        return getTransaksiByQuery(sql, null);
    }

    public List<Transaksi> getTransaksiAktif() throws SQLException {
        String sql = "SELECT t.*, m.plat_nomor, m.merk, m.model, c.nama " +
                "FROM transaksi t " +
                "JOIN mobil m ON t.id_mobil = m.id_mobil " +
                "JOIN customer c ON t.id_customer = c.id_customer " +
                "WHERE t.status = 'DISEWA' ORDER BY t.id_transaksi DESC";
        return getTransaksiByQuery(sql, null);
    }

    public List<Transaksi> getLaporan(String tanggalAwal, String tanggalAkhir, String status) throws SQLException {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t.*, m.plat_nomor, m.merk, m.model, c.nama ");
        sql.append("FROM transaksi t ");
        sql.append("JOIN mobil m ON t.id_mobil = m.id_mobil ");
        sql.append("JOIN customer c ON t.id_customer = c.id_customer WHERE 1=1 ");

        if (tanggalAwal != null && !tanggalAwal.isEmpty()) {
            sql.append("AND t.tanggal_sewa >= ? ");
            params.add(Date.valueOf(tanggalAwal));
        }
        if (tanggalAkhir != null && !tanggalAkhir.isEmpty()) {
            sql.append("AND t.tanggal_sewa <= ? ");
            params.add(Date.valueOf(tanggalAkhir));
        }
        if (status != null && !status.isEmpty() && !"SEMUA".equalsIgnoreCase(status)) {
            sql.append("AND t.status = ? ");
            params.add(status);
        }
        sql.append("ORDER BY t.id_transaksi DESC");
        return getTransaksiByQuery(sql.toString(), params);
    }

    public Transaksi getTransaksiById(int idTransaksi) throws SQLException {
        String sql = "SELECT t.*, m.plat_nomor, m.merk, m.model, c.nama " +
                "FROM transaksi t " +
                "JOIN mobil m ON t.id_mobil = m.id_mobil " +
                "JOIN customer c ON t.id_customer = c.id_customer " +
                "WHERE t.id_transaksi = ?";
        List<Object> params = new ArrayList<>();
        params.add(idTransaksi);
        List<Transaksi> hasil = getTransaksiByQuery(sql, params);
        return hasil.isEmpty() ? null : hasil.get(0);
    }

    public void insertTransaksi(int idMobil, int idCustomer, Date tanggalSewa, Date tanggalRencanaKembali) throws SQLException {
        String sqlHarga = "SELECT harga_sewa FROM mobil WHERE id_mobil = ? AND status = 'TERSEDIA'";
        String sqlTransaksi = "INSERT INTO transaksi (id_mobil, id_customer, tanggal_sewa, tanggal_rencana_kembali, total_biaya, denda, status) " +
                "VALUES (?, ?, ?, ?, ?, 0, 'DISEWA')";
        String sqlMobil = "UPDATE mobil SET status = 'DISEWA' WHERE id_mobil = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                double hargaSewa;
                try (PreparedStatement psHarga = conn.prepareStatement(sqlHarga)) {
                    psHarga.setInt(1, idMobil);
                    try (ResultSet rs = psHarga.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Mobil tidak tersedia atau tidak ditemukan.");
                        }
                        hargaSewa = rs.getDouble("harga_sewa");
                    }
                }

                long hari = ChronoUnit.DAYS.between(tanggalSewa.toLocalDate(), tanggalRencanaKembali.toLocalDate());
                if (hari <= 0) {
                    hari = 1;
                }
                double totalBiaya = hari * hargaSewa;

                try (PreparedStatement psTransaksi = conn.prepareStatement(sqlTransaksi)) {
                    psTransaksi.setInt(1, idMobil);
                    psTransaksi.setInt(2, idCustomer);
                    psTransaksi.setDate(3, tanggalSewa);
                    psTransaksi.setDate(4, tanggalRencanaKembali);
                    psTransaksi.setDouble(5, totalBiaya);
                    psTransaksi.executeUpdate();
                }

                try (PreparedStatement psMobil = conn.prepareStatement(sqlMobil)) {
                    psMobil.setInt(1, idMobil);
                    psMobil.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void prosesPengembalian(int idTransaksi, Date tanggalKembali) throws SQLException {
        String sqlCari = "SELECT id_mobil, tanggal_rencana_kembali FROM transaksi WHERE id_transaksi = ? AND status = 'DISEWA'";
        String sqlUpdateTransaksi = "UPDATE transaksi SET tanggal_kembali = ?, denda = ?, status = 'KEMBALI' WHERE id_transaksi = ?";
        String sqlUpdateMobil = "UPDATE mobil SET status = 'TERSEDIA' WHERE id_mobil = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int idMobil;
                LocalDate rencanaKembali;
                try (PreparedStatement psCari = conn.prepareStatement(sqlCari)) {
                    psCari.setInt(1, idTransaksi);
                    try (ResultSet rs = psCari.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Transaksi aktif tidak ditemukan.");
                        }
                        idMobil = rs.getInt("id_mobil");
                        rencanaKembali = rs.getDate("tanggal_rencana_kembali").toLocalDate();
                    }
                }

                long telat = ChronoUnit.DAYS.between(rencanaKembali, tanggalKembali.toLocalDate());
                if (telat < 0) {
                    telat = 0;
                }
                double denda = telat * DENDA_PER_HARI;

                try (PreparedStatement psUpdateTransaksi = conn.prepareStatement(sqlUpdateTransaksi)) {
                    psUpdateTransaksi.setDate(1, tanggalKembali);
                    psUpdateTransaksi.setDouble(2, denda);
                    psUpdateTransaksi.setInt(3, idTransaksi);
                    psUpdateTransaksi.executeUpdate();
                }

                try (PreparedStatement psUpdateMobil = conn.prepareStatement(sqlUpdateMobil)) {
                    psUpdateMobil.setInt(1, idMobil);
                    psUpdateMobil.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void deleteTransaksi(int idTransaksi) throws SQLException {
        Transaksi transaksi = getTransaksiById(idTransaksi);
        if (transaksi == null) {
            return;
        }

        String sqlDelete = "DELETE FROM transaksi WHERE id_transaksi = ?";
        String sqlMobil = "UPDATE mobil SET status = 'TERSEDIA' WHERE id_mobil = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement psDelete = conn.prepareStatement(sqlDelete)) {
                    psDelete.setInt(1, idTransaksi);
                    psDelete.executeUpdate();
                }
                if ("DISEWA".equals(transaksi.getStatus())) {
                    try (PreparedStatement psMobil = conn.prepareStatement(sqlMobil)) {
                        psMobil.setInt(1, transaksi.getIdMobil());
                        psMobil.executeUpdate();
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private List<Transaksi> getTransaksiByQuery(String sql, List<Object> params) throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (params != null) {
                for (int i = 0; i < params.size(); i++) {
                    Object param = params.get(i);
                    if (param instanceof Date) {
                        ps.setDate(i + 1, (Date) param);
                    } else if (param instanceof Integer) {
                        ps.setInt(i + 1, (Integer) param);
                    } else {
                        ps.setString(i + 1, String.valueOf(param));
                    }
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        }
        return list;
    }

    private Transaksi mapResultSet(ResultSet rs) throws SQLException {
        Transaksi transaksi = new Transaksi();
        transaksi.setIdTransaksi(rs.getInt("id_transaksi"));
        transaksi.setIdMobil(rs.getInt("id_mobil"));
        transaksi.setIdCustomer(rs.getInt("id_customer"));
        transaksi.setPlatNomor(rs.getString("plat_nomor"));
        transaksi.setMerk(rs.getString("merk"));
        transaksi.setModelMobil(rs.getString("model"));
        transaksi.setNamaCustomer(rs.getString("nama"));
        transaksi.setTanggalSewa(rs.getDate("tanggal_sewa"));
        transaksi.setTanggalRencanaKembali(rs.getDate("tanggal_rencana_kembali"));
        transaksi.setTanggalKembali(rs.getDate("tanggal_kembali"));
        transaksi.setTotalBiaya(rs.getDouble("total_biaya"));
        transaksi.setDenda(rs.getDouble("denda"));
        transaksi.setStatus(rs.getString("status"));
        return transaksi;
    }
}

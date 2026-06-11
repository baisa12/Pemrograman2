<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="com.rentcar.model.Mobil"%>
<%@page import="com.rentcar.model.Customer"%>
<%@page import="com.rentcar.model.Transaksi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Mobil> listMobil = (List<Mobil>) request.getAttribute("listMobil");
    List<Customer> listCustomer = (List<Customer>) request.getAttribute("listCustomer");
    List<Transaksi> listTransaksi = (List<Transaksi>) request.getAttribute("listTransaksi");
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaksi Sewa - Rent Car</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style.css">
</head>
<body>
<div class="app">
    <aside class="sidebar">
        <div class="brand">Rent Car Web</div>
        <div class="subtitle">Aplikasi Penyewaan Mobil</div>
        <nav class="nav">
            <a href="<%= request.getContextPath() %>/dashboard">Dashboard</a>
            <a href="<%= request.getContextPath() %>/mobil">Data Mobil</a>
            <a href="<%= request.getContextPath() %>/customer">Data Customer</a>
            <a class="active" href="<%= request.getContextPath() %>/transaksi">Transaksi Sewa</a>
            <a href="<%= request.getContextPath() %>/pengembalian">Pengembalian</a>
            <a href="<%= request.getContextPath() %>/laporan">Laporan</a>
        </nav>
    </aside>

    <main class="content">
        <h1 class="page-title">Transaksi Penyewaan</h1>
        <p class="page-desc">Form transaksi penyewaan mobil dan daftar riwayat transaksi.</p>

        <% if (success != null) { %>
            <div class="alert alert-success"><%= success %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="alert alert-error"><%= error %></div>
        <% } %>

        <div class="card">
            <h2>Tambah Transaksi Sewa</h2>
            <form method="post" action="<%= request.getContextPath() %>/transaksi">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Mobil Tersedia</label>
                        <select name="idMobil" required>
                            <option value="">-- Pilih Mobil --</option>
                            <% if (listMobil != null) {
                                for (Mobil mobil : listMobil) { %>
                                    <option value="<%= mobil.getIdMobil() %>">
                                        <%= mobil.getPlatNomor() %> - <%= mobil.getMerk() %> <%= mobil.getModel() %> (<%= rupiah.format(mobil.getHargaSewa()) %>/hari)
                                    </option>
                            <%  }
                            } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Customer</label>
                        <select name="idCustomer" required>
                            <option value="">-- Pilih Customer --</option>
                            <% if (listCustomer != null) {
                                for (Customer customer : listCustomer) { %>
                                    <option value="<%= customer.getIdCustomer() %>"><%= customer.getNama() %> - <%= customer.getNoHp() %></option>
                            <%  }
                            } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Tanggal Sewa</label>
                        <input type="date" name="tanggalSewa" required>
                    </div>
                    <div class="form-group">
                        <label>Rencana Tanggal Kembali</label>
                        <input type="date" name="tanggalRencanaKembali" required>
                    </div>
                </div>
                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">Simpan Transaksi</button>
                    <a class="btn btn-secondary" href="<%= request.getContextPath() %>/mobil">Tambah Mobil</a>
                    <a class="btn btn-secondary" href="<%= request.getContextPath() %>/customer">Tambah Customer</a>
                </div>
            </form>
        </div>

        <div class="card">
            <h2>Daftar Transaksi</h2>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th>No</th>
                        <th>Customer</th>
                        <th>Mobil</th>
                        <th>Tgl Sewa</th>
                        <th>Rencana Kembali</th>
                        <th>Tgl Kembali</th>
                        <th>Total Sewa</th>
                        <th>Denda</th>
                        <th>Status</th>
                        <th>Aksi</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (listTransaksi != null && !listTransaksi.isEmpty()) {
                        int no = 1;
                        for (Transaksi transaksi : listTransaksi) { %>
                        <tr>
                            <td><%= no++ %></td>
                            <td><%= transaksi.getNamaCustomer() %></td>
                            <td><%= transaksi.getPlatNomor() %> - <%= transaksi.getMerk() %> <%= transaksi.getModelMobil() %></td>
                            <td><%= transaksi.getTanggalSewa() %></td>
                            <td><%= transaksi.getTanggalRencanaKembali() %></td>
                            <td><%= transaksi.getTanggalKembali() == null ? "-" : transaksi.getTanggalKembali() %></td>
                            <td><%= rupiah.format(transaksi.getTotalBiaya()) %></td>
                            <td><%= rupiah.format(transaksi.getDenda()) %></td>
                            <td>
                                <span class="badge <%= "DISEWA".equals(transaksi.getStatus()) ? "badge-yellow" : "badge-green" %>"><%= transaksi.getStatus() %></span>
                            </td>
                            <td>
                                <% if ("DISEWA".equals(transaksi.getStatus())) { %>
                                    <a class="btn btn-success" href="<%= request.getContextPath() %>/pengembalian">Kembalikan</a>
                                <% } %>
                                <a class="btn btn-danger" onclick="return confirm('Yakin hapus transaksi ini?')" href="<%= request.getContextPath() %>/transaksi?action=delete&id=<%= transaksi.getIdTransaksi() %>">Hapus</a>
                            </td>
                        </tr>
                    <%  }
                    } else { %>
                        <tr><td colspan="10">Belum ada transaksi.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
</body>
</html>

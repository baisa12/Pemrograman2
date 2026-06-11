<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="com.rentcar.model.Transaksi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Transaksi> listLaporan = (List<Transaksi>) request.getAttribute("listLaporan");
    String error = (String) request.getAttribute("error");
    String tanggalAwal = (String) request.getAttribute("tanggalAwal");
    String tanggalAkhir = (String) request.getAttribute("tanggalAkhir");
    String status = (String) request.getAttribute("status");
    Double totalPendapatan = (Double) request.getAttribute("totalPendapatan");
    Double totalDenda = (Double) request.getAttribute("totalDenda");
    if (tanggalAwal == null) tanggalAwal = "";
    if (tanggalAkhir == null) tanggalAkhir = "";
    if (status == null) status = "SEMUA";
    if (totalPendapatan == null) totalPendapatan = 0.0;
    if (totalDenda == null) totalDenda = 0.0;
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Laporan Transaksi - Rent Car</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style.css">
</head>
<body>
<div class="app">
    <aside class="sidebar no-print">
        <div class="brand">Rent Car Web</div>
        <div class="subtitle">Aplikasi Penyewaan Mobil</div>
        <nav class="nav">
            <a href="<%= request.getContextPath() %>/dashboard">Dashboard</a>
            <a href="<%= request.getContextPath() %>/mobil">Data Mobil</a>
            <a href="<%= request.getContextPath() %>/customer">Data Customer</a>
            <a href="<%= request.getContextPath() %>/transaksi">Transaksi Sewa</a>
            <a href="<%= request.getContextPath() %>/pengembalian">Pengembalian</a>
            <a class="active" href="<%= request.getContextPath() %>/laporan">Laporan</a>
        </nav>
    </aside>

    <main class="content">
        <h1 class="page-title">Laporan Transaksi</h1>
        <p class="page-desc no-print">Filter laporan berdasarkan tanggal sewa dan status transaksi.</p>

        <% if (error != null) { %>
            <div class="alert alert-error"><%= error %></div>
        <% } %>

        <div class="card no-print">
            <h2>Filter Laporan</h2>
            <form method="get" action="<%= request.getContextPath() %>/laporan">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Tanggal Awal</label>
                        <input type="date" name="tanggalAwal" value="<%= tanggalAwal %>">
                    </div>
                    <div class="form-group">
                        <label>Tanggal Akhir</label>
                        <input type="date" name="tanggalAkhir" value="<%= tanggalAkhir %>">
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <select name="status">
                            <option value="SEMUA" <%= "SEMUA".equals(status) ? "selected" : "" %>>SEMUA</option>
                            <option value="DISEWA" <%= "DISEWA".equals(status) ? "selected" : "" %>>DISEWA</option>
                            <option value="KEMBALI" <%= "KEMBALI".equals(status) ? "selected" : "" %>>KEMBALI</option>
                        </select>
                    </div>
                </div>
                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">Tampilkan</button>
                    <a class="btn btn-secondary" href="<%= request.getContextPath() %>/laporan">Reset</a>
                    <button type="button" class="btn btn-success" onclick="window.print()">Cetak Laporan</button>
                </div>
            </form>
        </div>

        <div class="card print-area">
            <h2 style="text-align:center; margin-bottom: 4px;">Laporan Transaksi Rent Car</h2>
            <p style="text-align:center; margin-top: 0; color:#64748b;">Aplikasi Web Penyewaan Mobil</p>
            <div class="grid">
                <div class="card">
                    <h3>Total Pendapatan</h3>
                    <div class="stat-number"><%= rupiah.format(totalPendapatan) %></div>
                </div>
                <div class="card">
                    <h3>Total Denda</h3>
                    <div class="stat-number"><%= rupiah.format(totalDenda) %></div>
                </div>
            </div>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th>No</th>
                        <th>ID</th>
                        <th>Customer</th>
                        <th>Mobil</th>
                        <th>Tgl Sewa</th>
                        <th>Rencana Kembali</th>
                        <th>Tgl Kembali</th>
                        <th>Total Sewa</th>
                        <th>Denda</th>
                        <th>Total Bayar</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (listLaporan != null && !listLaporan.isEmpty()) {
                        int no = 1;
                        for (Transaksi transaksi : listLaporan) {
                            double totalBayar = transaksi.getTotalBiaya() + transaksi.getDenda(); %>
                        <tr>
                            <td><%= no++ %></td>
                            <td>TRX-<%= transaksi.getIdTransaksi() %></td>
                            <td><%= transaksi.getNamaCustomer() %></td>
                            <td><%= transaksi.getPlatNomor() %> - <%= transaksi.getMerk() %> <%= transaksi.getModelMobil() %></td>
                            <td><%= transaksi.getTanggalSewa() %></td>
                            <td><%= transaksi.getTanggalRencanaKembali() %></td>
                            <td><%= transaksi.getTanggalKembali() == null ? "-" : transaksi.getTanggalKembali() %></td>
                            <td><%= rupiah.format(transaksi.getTotalBiaya()) %></td>
                            <td><%= rupiah.format(transaksi.getDenda()) %></td>
                            <td><%= rupiah.format(totalBayar) %></td>
                            <td><span class="badge <%= "DISEWA".equals(transaksi.getStatus()) ? "badge-yellow" : "badge-green" %>"><%= transaksi.getStatus() %></span></td>
                        </tr>
                    <%  }
                    } else { %>
                        <tr><td colspan="11">Tidak ada data laporan.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
</body>
</html>

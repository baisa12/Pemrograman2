<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="com.rentcar.model.Transaksi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Transaksi> listTransaksiAktif = (List<Transaksi>) request.getAttribute("listTransaksiAktif");
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pengembalian - Rent Car</title>
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
            <a href="<%= request.getContextPath() %>/transaksi">Transaksi Sewa</a>
            <a class="active" href="<%= request.getContextPath() %>/pengembalian">Pengembalian</a>
            <a href="<%= request.getContextPath() %>/laporan">Laporan</a>
        </nav>
    </aside>

    <main class="content">
        <h1 class="page-title">Pengembalian Mobil</h1>
        <p class="page-desc">Proses pengembalian mobil yang masih berstatus DISEWA.</p>

        <% if (success != null) { %>
            <div class="alert alert-success"><%= success %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="alert alert-error"><%= error %></div>
        <% } %>

        <div class="card">
            <h2>Form Pengembalian</h2>
            <form method="post" action="<%= request.getContextPath() %>/pengembalian">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Transaksi Aktif</label>
                        <select name="idTransaksi" required>
                            <option value="">-- Pilih Transaksi --</option>
                            <% if (listTransaksiAktif != null) {
                                for (Transaksi transaksi : listTransaksiAktif) { %>
                                    <option value="<%= transaksi.getIdTransaksi() %>">
                                        TRX-<%= transaksi.getIdTransaksi() %> | <%= transaksi.getNamaCustomer() %> | <%= transaksi.getPlatNomor() %> - <%= transaksi.getMerk() %> <%= transaksi.getModelMobil() %>
                                    </option>
                            <%  }
                            } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Tanggal Kembali</label>
                        <input type="date" name="tanggalKembali" required>
                    </div>
                </div>
                <div class="btn-row">
                    <button type="submit" class="btn btn-success">Proses Pengembalian</button>
                </div>
            </form>
        </div>

        <div class="card">
            <h2>Transaksi Aktif</h2>
            <p>Denda keterlambatan otomatis dihitung Rp50.000 per hari setelah tanggal rencana kembali.</p>
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
                        <th>Total Sewa</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (listTransaksiAktif != null && !listTransaksiAktif.isEmpty()) {
                        int no = 1;
                        for (Transaksi transaksi : listTransaksiAktif) { %>
                        <tr>
                            <td><%= no++ %></td>
                            <td>TRX-<%= transaksi.getIdTransaksi() %></td>
                            <td><%= transaksi.getNamaCustomer() %></td>
                            <td><%= transaksi.getPlatNomor() %> - <%= transaksi.getMerk() %> <%= transaksi.getModelMobil() %></td>
                            <td><%= transaksi.getTanggalSewa() %></td>
                            <td><%= transaksi.getTanggalRencanaKembali() %></td>
                            <td><%= rupiah.format(transaksi.getTotalBiaya()) %></td>
                            <td><span class="badge badge-yellow"><%= transaksi.getStatus() %></span></td>
                        </tr>
                    <%  }
                    } else { %>
                        <tr><td colspan="8">Tidak ada transaksi aktif.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
</body>
</html>

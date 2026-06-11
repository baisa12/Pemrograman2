<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="com.rentcar.model.Mobil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Mobil> listMobil = (List<Mobil>) request.getAttribute("listMobil");
    Mobil mobilEdit = (Mobil) request.getAttribute("mobilEdit");
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
    NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Data Mobil - Rent Car</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style.css">
</head>
<body>
<div class="app">
    <aside class="sidebar">
        <div class="brand">Rent Car Web</div>
        <div class="subtitle">Aplikasi Penyewaan Mobil</div>
        <nav class="nav">
            <a href="<%= request.getContextPath() %>/dashboard">Dashboard</a>
            <a class="active" href="<%= request.getContextPath() %>/mobil">Data Mobil</a>
            <a href="<%= request.getContextPath() %>/customer">Data Customer</a>
            <a href="<%= request.getContextPath() %>/transaksi">Transaksi Sewa</a>
            <a href="<%= request.getContextPath() %>/pengembalian">Pengembalian</a>
            <a href="<%= request.getContextPath() %>/laporan">Laporan</a>
        </nav>
    </aside>

    <main class="content">
        <h1 class="page-title">Data Mobil</h1>
        <p class="page-desc">Form input, edit, hapus, dan daftar mobil.</p>

        <% if (success != null) { %>
            <div class="alert alert-success"><%= success %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="alert alert-error"><%= error %></div>
        <% } %>

        <div class="card">
            <h2><%= mobilEdit == null ? "Tambah Mobil" : "Edit Mobil" %></h2>
            <form method="post" action="<%= request.getContextPath() %>/mobil">
                <input type="hidden" name="idMobil" value="<%= mobilEdit == null ? "" : mobilEdit.getIdMobil() %>">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Plat Nomor</label>
                        <input type="text" name="platNomor" required placeholder="Contoh: B 1234 ABC" value="<%= mobilEdit == null ? "" : mobilEdit.getPlatNomor() %>">
                    </div>
                    <div class="form-group">
                        <label>Merk</label>
                        <input type="text" name="merk" required placeholder="Contoh: Toyota" value="<%= mobilEdit == null ? "" : mobilEdit.getMerk() %>">
                    </div>
                    <div class="form-group">
                        <label>Model</label>
                        <input type="text" name="model" required placeholder="Contoh: Avanza" value="<%= mobilEdit == null ? "" : mobilEdit.getModel() %>">
                    </div>
                    <div class="form-group">
                        <label>Tahun</label>
                        <input type="number" name="tahun" required min="1990" value="<%= mobilEdit == null ? "2022" : mobilEdit.getTahun() %>">
                    </div>
                    <div class="form-group">
                        <label>Harga Sewa / Hari</label>
                        <input type="number" name="hargaSewa" required min="0" step="1000" value="<%= mobilEdit == null ? "350000" : (long) mobilEdit.getHargaSewa() %>">
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <select name="status" required>
                            <option value="TERSEDIA" <%= mobilEdit != null && "TERSEDIA".equals(mobilEdit.getStatus()) ? "selected" : "" %>>TERSEDIA</option>
                            <option value="DISEWA" <%= mobilEdit != null && "DISEWA".equals(mobilEdit.getStatus()) ? "selected" : "" %>>DISEWA</option>
                        </select>
                    </div>
                </div>
                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">Simpan</button>
                    <% if (mobilEdit != null) { %>
                        <a class="btn btn-secondary" href="<%= request.getContextPath() %>/mobil">Batal Edit</a>
                    <% } %>
                </div>
            </form>
        </div>

        <div class="card">
            <h2>Daftar Mobil</h2>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th>No</th>
                        <th>Plat Nomor</th>
                        <th>Merk</th>
                        <th>Model</th>
                        <th>Tahun</th>
                        <th>Harga / Hari</th>
                        <th>Status</th>
                        <th>Aksi</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (listMobil != null && !listMobil.isEmpty()) {
                        int no = 1;
                        for (Mobil mobil : listMobil) { %>
                        <tr>
                            <td><%= no++ %></td>
                            <td><%= mobil.getPlatNomor() %></td>
                            <td><%= mobil.getMerk() %></td>
                            <td><%= mobil.getModel() %></td>
                            <td><%= mobil.getTahun() %></td>
                            <td><%= rupiah.format(mobil.getHargaSewa()) %></td>
                            <td>
                                <span class="badge <%= "TERSEDIA".equals(mobil.getStatus()) ? "badge-green" : "badge-yellow" %>"><%= mobil.getStatus() %></span>
                            </td>
                            <td>
                                <a class="btn btn-warning" href="<%= request.getContextPath() %>/mobil?action=edit&id=<%= mobil.getIdMobil() %>">Edit</a>
                                <a class="btn btn-danger" onclick="return confirm('Yakin hapus data mobil ini?')" href="<%= request.getContextPath() %>/mobil?action=delete&id=<%= mobil.getIdMobil() %>">Hapus</a>
                            </td>
                        </tr>
                    <%  }
                    } else { %>
                        <tr><td colspan="8">Belum ada data mobil.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
</body>
</html>

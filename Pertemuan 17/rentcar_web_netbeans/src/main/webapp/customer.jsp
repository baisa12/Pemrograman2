<%@page import="java.util.List"%>
<%@page import="com.rentcar.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Customer> listCustomer = (List<Customer>) request.getAttribute("listCustomer");
    Customer customerEdit = (Customer) request.getAttribute("customerEdit");
    String success = request.getParameter("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Data Customer - Rent Car</title>
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
            <a class="active" href="<%= request.getContextPath() %>/customer">Data Customer</a>
            <a href="<%= request.getContextPath() %>/transaksi">Transaksi Sewa</a>
            <a href="<%= request.getContextPath() %>/pengembalian">Pengembalian</a>
            <a href="<%= request.getContextPath() %>/laporan">Laporan</a>
        </nav>
    </aside>

    <main class="content">
        <h1 class="page-title">Data Customer</h1>
        <p class="page-desc">Form input, edit, hapus, dan daftar customer.</p>

        <% if (success != null) { %>
            <div class="alert alert-success"><%= success %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="alert alert-error"><%= error %></div>
        <% } %>

        <div class="card">
            <h2><%= customerEdit == null ? "Tambah Customer" : "Edit Customer" %></h2>
            <form method="post" action="<%= request.getContextPath() %>/customer">
                <input type="hidden" name="idCustomer" value="<%= customerEdit == null ? "" : customerEdit.getIdCustomer() %>">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Nama Customer</label>
                        <input type="text" name="nama" required placeholder="Nama lengkap" value="<%= customerEdit == null ? "" : customerEdit.getNama() %>">
                    </div>
                    <div class="form-group">
                        <label>No KTP</label>
                        <input type="text" name="noKtp" required placeholder="Nomor KTP" value="<%= customerEdit == null ? "" : customerEdit.getNoKtp() %>">
                    </div>
                    <div class="form-group">
                        <label>No HP</label>
                        <input type="text" name="noHp" required placeholder="Nomor HP" value="<%= customerEdit == null ? "" : customerEdit.getNoHp() %>">
                    </div>
                    <div class="form-group">
                        <label>Alamat</label>
                        <textarea name="alamat" required placeholder="Alamat customer"><%= customerEdit == null ? "" : customerEdit.getAlamat() %></textarea>
                    </div>
                </div>
                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">Simpan</button>
                    <% if (customerEdit != null) { %>
                        <a class="btn btn-secondary" href="<%= request.getContextPath() %>/customer">Batal Edit</a>
                    <% } %>
                </div>
            </form>
        </div>

        <div class="card">
            <h2>Daftar Customer</h2>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr>
                        <th>No</th>
                        <th>Nama</th>
                        <th>No KTP</th>
                        <th>No HP</th>
                        <th>Alamat</th>
                        <th>Aksi</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (listCustomer != null && !listCustomer.isEmpty()) {
                        int no = 1;
                        for (Customer customer : listCustomer) { %>
                        <tr>
                            <td><%= no++ %></td>
                            <td><%= customer.getNama() %></td>
                            <td><%= customer.getNoKtp() %></td>
                            <td><%= customer.getNoHp() %></td>
                            <td><%= customer.getAlamat() %></td>
                            <td>
                                <a class="btn btn-warning" href="<%= request.getContextPath() %>/customer?action=edit&id=<%= customer.getIdCustomer() %>">Edit</a>
                                <a class="btn btn-danger" onclick="return confirm('Yakin hapus data customer ini?')" href="<%= request.getContextPath() %>/customer?action=delete&id=<%= customer.getIdCustomer() %>">Hapus</a>
                            </td>
                        </tr>
                    <%  }
                    } else { %>
                        <tr><td colspan="6">Belum ada data customer.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
</body>
</html>

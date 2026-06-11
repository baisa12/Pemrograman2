<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Integer jumlahMobil = (Integer) request.getAttribute("jumlahMobil");
    Integer jumlahCustomer = (Integer) request.getAttribute("jumlahCustomer");
    Integer jumlahTransaksi = (Integer) request.getAttribute("jumlahTransaksi");
    Integer jumlahTransaksiAktif = (Integer) request.getAttribute("jumlahTransaksiAktif");
    if (jumlahMobil == null) jumlahMobil = 0;
    if (jumlahCustomer == null) jumlahCustomer = 0;
    if (jumlahTransaksi == null) jumlahTransaksi = 0;
    if (jumlahTransaksiAktif == null) jumlahTransaksiAktif = 0;
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Rent Car</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style.css">
</head>
<body>
<div class="app">
    <aside class="sidebar">
        <div class="brand">Rent Car Web</div>
        <div class="subtitle">Aplikasi Penyewaan Mobil</div>
        <nav class="nav">
            <a class="active" href="<%= request.getContextPath() %>/dashboard">Dashboard</a>
            <a href="<%= request.getContextPath() %>/mobil">Data Mobil</a>
            <a href="<%= request.getContextPath() %>/customer">Data Customer</a>
            <a href="<%= request.getContextPath() %>/transaksi">Transaksi Sewa</a>
            <a href="<%= request.getContextPath() %>/pengembalian">Pengembalian</a>
            <a href="<%= request.getContextPath() %>/laporan">Laporan</a>
        </nav>
    </aside>

    <main class="content">
        <h1 class="page-title">Dashboard</h1>
        <p class="page-desc">Ringkasan aplikasi web penyewaan mobil.</p>

        <% if (error != null) { %>
            <div class="alert alert-error"><%= error %></div>
        <% } %>

        <section class="grid">
            <div class="card">
                <h3>Total Mobil</h3>
                <div class="stat-number"><%= jumlahMobil %></div>
                <p>Jumlah data mobil yang terdaftar.</p>
            </div>
            <div class="card">
                <h3>Total Customer</h3>
                <div class="stat-number"><%= jumlahCustomer %></div>
                <p>Jumlah pelanggan penyewa mobil.</p>
            </div>
            <div class="card">
                <h3>Total Transaksi</h3>
                <div class="stat-number"><%= jumlahTransaksi %></div>
                <p>Semua transaksi penyewaan dan pengembalian.</p>
            </div>
            <div class="card">
                <h3>Sedang Disewa</h3>
                <div class="stat-number"><%= jumlahTransaksiAktif %></div>
                <p>Mobil yang masih dalam proses sewa.</p>
            </div>
        </section>

        <div class="card">
            <h2>Menu Implementasi</h2>
            <p>Aplikasi ini sudah berisi form master data mobil, customer, transaksi penyewaan, proses pengembalian, dan laporan transaksi.</p>
            <div class="btn-row">
                <a class="btn btn-primary" href="<%= request.getContextPath() %>/mobil">Input Mobil</a>
                <a class="btn btn-primary" href="<%= request.getContextPath() %>/customer">Input Customer</a>
                <a class="btn btn-success" href="<%= request.getContextPath() %>/transaksi">Buat Transaksi</a>
                <a class="btn btn-secondary" href="<%= request.getContextPath() %>/laporan">Lihat Laporan</a>
            </div>
        </div>
    </main>
</div>
</body>
</html>

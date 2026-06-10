<%@page import="java.sql.*"%>
<%@page import="koneksi.KoneksiDB"%>

<%@page contentType="text/html"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<title>

Data Mahasiswa

</title>

<link
rel="stylesheet"
href="style.css">

</head>

<body>

<div class="header">

<div class="logo">

🎓

</div>

<div>

<h1>

Informasi Nilai Mahasiswa

</h1>

<h2>

UNIVERSITAS PAMULANG

</h2>

<p>

Jl. Surya Kencana No.1 Pamulang

</p>

</div>

</div>

<nav>

<ul>

<li>

<a href="index.jsp">

🏠 Home

</a>

</li>

<li>

<a href="#">

📁 Master Data

</a>

<ul>

<li>

<a href="dataMahasiswa.jsp">

Mahasiswa

</a>

</li>

<li>

<a href="dataMatkul.jsp">

Mata Kuliah

</a>

</li>

</ul>

</li>

<li>

<a href="#">

📝 Transaksi

</a>

<ul>

<li>

<a href="inputNilai.jsp">

Input Nilai

</a>

</li>

</ul>

</li>

<li>

<a href="#">

📊 Laporan

</a>

<ul>

<li>

<a href="cetakNilai.jsp">

Cetak Nilai

</a>

</li>

</ul>

</li>

<li>

<a href="login.jsp">

🔒 Login

</a>

</li>

</ul>

</nav>

<div class="boxutama">

<div class="atasbox">

<div>

<h1>

👨‍🎓 Data Mahasiswa

</h1>

<p>

Kelola data mahasiswa

</p>

</div>

<a
href="tambahMahasiswa.jsp">

<button
class="tambah">

+ Tambah Mahasiswa

</button>

</a>

</div>

<hr>

<div class="searchbox">

<input

type="text"

placeholder=

"Cari NIM atau Nama Mahasiswa">

</div>

<table>

<tr>

<th>No</th>

<th>NIM</th>

<th>Nama</th>

<th>Jurusan</th>

<th>Alamat</th>

<th>Aksi</th>

</tr>

<%

try{

KoneksiDB db=
new KoneksiDB();

Connection conn=
db.getKoneksi();

Statement st=

conn.createStatement();

ResultSet rs=

st.executeQuery(

"select * from mahasiswa"

);

int no=1;

while(rs.next()){

%>

<tr>

<td>

<%=no++%>

</td>

<td>

<%=rs.getString("nim")%>

</td>

<td>

<%=rs.getString("nama")%>

</td>

<td>

<%=rs.getString("jurusan")%>

</td>

<td>

<%=rs.getString("alamat")%>

</td>

<td>

<a

href=

"editMahasiswa.jsp?nim=<%=rs.getString("nim")%>"

>

<button
class="edit">

Edit

</button>

</a>

<a

href=

"hapusMahasiswa.jsp?nim=<%=rs.getString("nim")%>"

onclick=

"return confirm('Yakin hapus data?')"

>

<button
class="hapus">

Hapus

</button>

</a>

</td>

</tr>

<%

}

}catch(Exception e){

out.println(

e.getMessage()

);

}

%>

</table>

</div>

<div class="footer">

© 2026 Universitas Pamulang

</div>

</body>

</html>

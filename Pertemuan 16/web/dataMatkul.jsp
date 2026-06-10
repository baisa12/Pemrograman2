<%@page import="java.sql.*"%>
<%@page import="koneksi.KoneksiDB"%>

<%@page contentType="text/html"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<title>

Data Mata Kuliah

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

🔒 Logout

</a>

</li>

</ul>

</nav>

<div class="boxutama">

<div class="judulbox">

<div>

<h2>

📚 Data Mata Kuliah

</h2>

<p>

Kelola data mata kuliah

</p>

</div>

<button

type="button"

class="tambah"

onclick=

"location.href='tambahMatkul.jsp'"

>

+ Tambah Mata Kuliah

</button>

</div>

<hr>

<div class="toolbar">

<input

type="text"

placeholder=

"🔍 Cari Kode atau Nama Mata Kuliah">

<select>

<option>

Semua Program Studi

</option>

<option>

Teknik Informatika

</option>

<option>

Sistem Informasi

</option>

</select>

</div>

<table>

<tr>

<th>No</th>

<th>Kode MK</th>

<th>Nama Mata Kuliah</th>

<th>SKS</th>

<th>Dosen</th>

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

"select * from matkul"

);

int no=1;

while(rs.next()){

%>

<tr>

<td>

<%=no++%>

</td>

<td>

<%=rs.getString("kode_matkul")%>

</td>

<td>

<%=rs.getString("nama_matkul")%>

</td>

<td>

<%=rs.getInt("sks")%>

</td>

<td>

<%=rs.getString("dosen")%>

</td>

<td>

<a

href=

"editMatkul.jsp?kode=<%=rs.getString("kode_matkul")%>"

>

<button
class="edit">

✏️

</button>

</a>

<a

href=

"hapusMatkul.jsp?kode=<%=rs.getString("kode_matkul")%>"

onclick=

"return confirm('Yakin hapus data?')"

>

<button
class="hapus">

🗑️

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

<div class="bawah">

Data Mata Kuliah Universitas Pamulang

</div>

</div>

<div class="footer">

© 2026 Universitas Pamulang

</div>

</body>

</html>
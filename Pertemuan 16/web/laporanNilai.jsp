<%@page import="java.sql.*"%>
<%@page import="koneksi.KoneksiDB"%>

<%@page contentType="text/html"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<title>Laporan Nilai Mahasiswa</title>

<link
rel="stylesheet"
href="style.css">

</head>

<body>

<div class="container">

<!-- HEADER -->

<div class="header">

<div class="judul">

<div class="judulheader">

<img

src="images/logo_unpam.png"

class="logounpam"

>

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

<h2>

UNIVERSITAS PAMULANG

</h2>

<p>

Jl. Surya Kencana No.1 Pamulang

</p>

</div>

</div>

<!-- NAVBAR -->

<nav>

<ul>

<li>

<a href="index.jsp">

Home

</a>

</li>

<li>

<a href="dataMahasiswa.jsp">

Master Data

</a>

</li>

<li>

<a href="inputNilai.jsp">

Transaksi

</a>

</li>

<li>

<a href="laporanNilai.jsp">

Laporan

</a>

</li>

<li>

<a href="login.jsp">

Logout

</a>

</li>

</ul>

</nav>

<!-- BOX -->

<div class="boxutama">

<h2>

📊 LAPORAN NILAI MAHASISWA

</h2>

<p>

Laporan hasil nilai mahasiswa

</p>

<form method="get">

<div class="filter">

<!-- PRODI -->

<select name="prodi">

<option>

Sistem Informasi

</option>

<option>

Teknik Informatika

</option>

</select>

<!-- SEMESTER -->

<select>

<option>

Genap

</option>

<option>

Ganjil

</option>

</select>

<!-- MATKUL -->

<select

name="matkul"

id="matkul"

onchange="ubahDosen()"

>

<option value="">

Pilih Mata Kuliah

</option>

<%

try{

KoneksiDB db=
new KoneksiDB();

Connection conn=
db.getKoneksi();

Statement st=
conn.createStatement();

ResultSet rsMatkul=

st.executeQuery(

"SELECT * FROM matkul"

);

while(rsMatkul.next()){

%>

<option

value=

"<%=rsMatkul.getString("nama_matkul")%>"

data-dosen=

"<%=rsMatkul.getString("dosen")%>"

>

<%=rsMatkul.getString("nama_matkul")%>

</option>

<%

}

}catch(Exception e){

}

%>

</select>

<input

id="dosen"

readonly

placeholder=

"Dosen Pengampu"

>

<button

class="biru"

type="submit"

>

🔍 Tampilkan

</button>

<button
type="button"
class="hijau"
onclick="window.location='LaporanNilaiController'">
📄 Cetak PDF
</button>

<button

type="button"

class="kuning"

onclick=

"window.location='exportExcel.jsp'"

>

📊 Export Excel

</button>

</div>

</form>

<!-- TABEL -->

<table>

<tr>

<th>No</th>

<th>NIM</th>

<th>Nama</th>

<th>Tugas</th>

<th>UTS</th>

<th>UAS</th>

<th>Kehadiran</th>

<th>Nilai Akhir</th>

<th>Grade</th>

<th>Status</th>

</tr>

<%

try{

KoneksiDB db=
new KoneksiDB();

Connection conn=
db.getKoneksi();

Statement st=
conn.createStatement();

String matkul=

request.getParameter(
"matkul"
);

String sql=

"SELECT * FROM nilai";

if(

matkul!=null

&&

!matkul.equals("")

){

sql=

"SELECT * FROM nilai WHERE matkul='"

+

matkul

+

"'";

}

ResultSet rs=

st.executeQuery(
sql
);

int no=1;

while(rs.next()){

double nilai=

rs.getDouble(
"nilai_akhir"
);

String status=

nilai>=56

?

"Lulus"

:

"Tidak Lulus";

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

<%=rs.getInt("tugas")%>

</td>

<td>

<%=rs.getInt("uts")%>

</td>

<td>

<%=rs.getInt("uas")%>

</td>

<td>

<%=rs.getInt("kehadiran")%>

</td>

<td>

<%=String.format(
"%.2f",
nilai
)%>

</td>

<td>

<%=rs.getString("grade")%>

</td>

<td>

<%=status%>

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

</div>

<script>

function ubahDosen(){

let matkul=

document.getElementById(
"matkul"
);

let dosen=

matkul.options[
matkul.selectedIndex
]

.getAttribute(
"data-dosen"
);

document.getElementById(
"dosen"
)

.value=dosen;

}

</script>

</body>

</html>
<%@page import="java.sql.*"%>
<%@page import="koneksi.KoneksiDB"%>

<%@page contentType="text/html"
pageEncoding="UTF-8"%>

<%

String nim=
request.getParameter("nim");

String nama=
request.getParameter("nama");

String matkul=
request.getParameter("matkul");

String tugas=
request.getParameter("tugas");

String uts=
request.getParameter("uts");

String uas=
request.getParameter("uas");

String kehadiran=
request.getParameter("kehadiran");

double akhir=0;

String grade="";

if(

nim!=null

&&

nama!=null

&&

tugas!=null

&&

uts!=null

&&

uas!=null

&&

kehadiran!=null

){

try{

double ntugas=

Double.parseDouble(
tugas
);

double nuts=

Double.parseDouble(
uts
);

double nuas=

Double.parseDouble(
uas
);

double hadir=

Double.parseDouble(
kehadiran
);

/*
3 SKS = 21 Pertemuan
*/

double nilaiKehadiran=

(hadir/21.0)*100;

akhir=

(ntugas*0.20)
+
(nuts*0.30)
+
(nuas*0.40)
+
(nilaiKehadiran*0.10);

if(akhir>=85){

grade="A";

}else if(akhir>=75){

grade="B+";

}else if(akhir>=65){

grade="B";

}else if(akhir>=55){

grade="C+";

}else{

grade="D";

}

KoneksiDB db=

new KoneksiDB();

Connection conn=

db.getKoneksi();

PreparedStatement pst=

conn.prepareStatement(

"INSERT INTO nilai(nim,nama,matkul,tugas,uts,uas,kehadiran,nilai_akhir,grade) VALUES(?,?,?,?,?,?,?,?,?)"

);

pst.setString(
1,
nim
);

pst.setString(
2,
nama
);

pst.setString(
3,
matkul
);

pst.setInt(
4,
Integer.parseInt(tugas)
);

pst.setInt(
5,
Integer.parseInt(uts)
);

pst.setInt(
6,
Integer.parseInt(uas)
);

pst.setInt(
7,
Integer.parseInt(kehadiran)
);

pst.setDouble(
8,
akhir
);

pst.setString(
9,
grade
);

pst.executeUpdate();

}catch(Exception e){

out.println(

e.getMessage()

);

}

}

%>

<!DOCTYPE html>

<html>

<head>

<title>

Input Nilai

</title>

<link
rel="stylesheet"
href="style.css">

</head>

<body>

<div class="boxutama">

<div class="judulbox">

<div>

<h2>

📝 Input Nilai

</h2>

<p>

Input nilai mahasiswa

</p>

</div>

</div>

<hr>

<div class="filternilai">

<select>

<option>

Sistem Informasi

</option>

<option>

Teknik Informatika

</option>

</select>

<select>

<option>

Semester Genap

</option>

<option>

Semester Ganjil

</option>

</select>

<select

name="matkul"

id="matkul"

onchange="pilihDosen()"

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

out.println(

e.getMessage()

);

}

%>

</select>

<input

id="dosen"

readonly

placeholder=

"Pilih Mata Kuliah"

>

</div>

<form method="post">

<table>

<tr>

<th>NIM</th>

<th>Nama</th>

<th>Tugas</th>

<th>UTS</th>

<th>UAS</th>

<th>Kehadiran</th>

</tr>

<tr>

<td>

<input
name="nim"
required>

</td>

<td>

<input
name="nama"
required>

</td>

<td>

<input
name="tugas"
type="number"
required>

</td>

<td>

<input
name="uts"
type="number"
required>

</td>

<td>

<input
name="uas"
type="number"
required>

</td>

<td>

<input

name="kehadiran"

type="number"

min="0"

max="21"

placeholder="0-21"

required>

</td>

</tr>

</table>

<input

type="hidden"

name="matkul"

value="Pemrograman II"

>

<button

class="simpannilai"

>

💾 Simpan Nilai

</button>

</form>

<br>
<hr>
<br>

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

"SELECT * FROM nilai"

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
rs.getDouble(
"nilai_akhir"
)
)%>

</td>

<td>

<%=rs.getString("grade")%>

</td>

<td>

<a
href=

"editNilai.jsp?nim=<%=rs.getString("nim")%>"

>

<button

type="button"

class="edit"

>

✏

</button>

</a>

<a

href=

"hapusNilai.jsp?nim=<%=rs.getString("nim")%>"

onclick=

"return confirm('Yakin hapus?')"

>

<button

type="button"

class="hapus"

>

🗑

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

<div class="info">

ℹ Bobot:

Tugas 20%

UTS 30%

UAS 40%

Kehadiran 10%

<br><br>

3 SKS = Maksimal 21 Pertemuan

</div>

</div>

<script>

function pilihDosen(){

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
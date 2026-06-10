<%@page import="java.sql.*"%>
<%@page import="koneksi.KoneksiDB"%>

<%

String kode=
request.getParameter("kode");

String nama=
request.getParameter("nama");

String sks=
request.getParameter("sks");

String dosen=
request.getParameter("dosen");

if(kode!=null){

try{

KoneksiDB db=
new KoneksiDB();

Connection conn=
db.getKoneksi();

String sql=

"INSERT INTO matkul VALUES(?,?,?,?)";

PreparedStatement pst=

conn.prepareStatement(sql);

pst.setString(1,kode);

pst.setString(2,nama);

pst.setInt(

3,

Integer.parseInt(sks)

);

pst.setString(4,dosen);

pst.executeUpdate();

response.sendRedirect(

"dataMatkul.jsp"

);

}catch(Exception e){

out.println(

e.getMessage()

);

}

}

%>

<html>

<head>

<link
rel="stylesheet"
href="style.css">

<title>

Tambah Mata Kuliah

</title>

</head>

<body>

<div class="boxutama">

<h2>

Tambah Mata Kuliah

</h2>

<form method="post">

<input

name="kode"

placeholder=

"Kode Mata Kuliah"

required>

<br><br>

<input

name="nama"

placeholder=

"Nama Mata Kuliah"

required>

<br><br>

<input

name="sks"

type="number"

placeholder="SKS"

required>

<br><br>

<input

name="dosen"

placeholder=

"Nama Dosen"

required>

<br><br>

<button
class="tambah">

Simpan

</button>

</form>

</div>

</body>

</html>

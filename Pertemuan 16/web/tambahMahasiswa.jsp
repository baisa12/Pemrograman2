<%@page import="java.sql.*"%>
<%@page import="koneksi.KoneksiDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

String nim=request.getParameter("nim");
String nama=request.getParameter("nama");
String jurusan=request.getParameter("jurusan");
String alamat=request.getParameter("alamat");

if(
nim!=null &&
nama!=null &&
jurusan!=null &&
alamat!=null
){

try{

KoneksiDB db=
new KoneksiDB();

Connection conn=
db.getKoneksi();

String sql=

"INSERT INTO mahasiswa(nim,nama,jurusan,alamat) VALUES(?,?,?,?)";

PreparedStatement pst=
conn.prepareStatement(sql);

pst.setString(1,nim);
pst.setString(2,nama);
pst.setString(3,jurusan);
pst.setString(4,alamat);

int hasil=
pst.executeUpdate();

if(hasil>0){

out.println(

"<script>"
+

"alert('Data berhasil ditambah');"

+

"window.location='dataMahasiswa.jsp';"

+

"</script>"

);

}else{

out.println(

"<script>alert('Gagal tambah data')</script>"

);

}

}catch(Exception e){

out.println(

"<h3>Error :</h3>"

+

e.getMessage()

);

}

}

%>

<!DOCTYPE html>

<html>

<head>

<title>

Tambah Mahasiswa

</title>

<link
rel="stylesheet"
href="style.css">

</head>

<body>

<div class="boxutama">

<h2>

Tambah Mahasiswa

</h2>

<form method="post">

<input
type="text"
name="nim"
placeholder="NIM"
required>

<br><br>

<input
type="text"
name="nama"
placeholder="Nama Mahasiswa"
required>

<br><br>

<input
type="text"
name="jurusan"
placeholder="Jurusan"
required>

<br><br>

<textarea

name="alamat"

placeholder="Alamat"

required>

</textarea>

<br><br>

<button
type="submit"
class="tambah">

Simpan

</button>

</form>

</div>

</body>

</html>
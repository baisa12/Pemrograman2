<%@page contentType="text/html"
pageEncoding="UTF-8"%>

<%@page import="java.sql.*"%>
<%@page import="koneksi.KoneksiDB"%>

<!DOCTYPE html>

<html>

<head>

<title>

Cetak Nilai

</title>

</head>

<body>

<center>

<h2>

LAPORAN NILAI MAHASISWA

</h2>

<table
border="1"
cellpadding="10">

<tr>

<th>No</th>

<th>NIM</th>

<th>Nama</th>

<th>Mata Kuliah</th>

<th>Tugas</th>

<th>UTS</th>

<th>UAS</th>

<th>Nilai Akhir</th>

<th>Grade</th>

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

<%=rs.getString("matkul")%>

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

<%=rs.getDouble("nilai_akhir")%>

</td>

<td>

<%=rs.getString("grade")%>

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

<br>

<button
onclick="window.print()">

Cetak

</button>

</center>

</body>

</html>
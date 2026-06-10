<%@page import="java.sql.*"%>
<%@page import="koneksi.KoneksiDB"%>

<%

String kode=

request.getParameter(
"kode"
);

try{

KoneksiDB db=
new KoneksiDB();

Connection conn=
db.getKoneksi();

String sql=

"DELETE FROM matkul WHERE kode_matkul=?";

PreparedStatement pst=

conn.prepareStatement(
sql
);

pst.setString(
1,
kode
);

int hasil=

pst.executeUpdate();

if(hasil>0){

out.println(

"<script>"

+

"alert('Data Mata Kuliah berhasil dihapus');"

+

"window.location='dataMatkul.jsp';"

+

"</script>"

);

}else{

out.println(

"<script>"

+

"alert('Data gagal dihapus');"

+

"window.location='dataMatkul.jsp';"

+

"</script>"

);

}

}catch(Exception e){

out.println(

"<script>"

+

"alert('Error : "

+e.getMessage()

+"');"

+

"</script>"

);

}

%>
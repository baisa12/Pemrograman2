<%@page import="java.sql.*"%>
<%@page import="koneksi.KoneksiDB"%>

<%

String nim=

request.getParameter(
"nim"
);

try{

KoneksiDB db=
new KoneksiDB();

Connection conn=

db.getKoneksi();

String sql=

"DELETE FROM mahasiswa WHERE nim=?";

PreparedStatement pst=

conn.prepareStatement(
sql
);

pst.setString(
1,
nim
);

int hasil=

pst.executeUpdate();

if(hasil>0){

out.println(

"<script>"

+

"alert('Data berhasil dihapus');"

+

"window.location='dataMahasiswa.jsp';"

+

"</script>"

);

}else{

out.println(

"<script>"

+

"alert('Data gagal dihapus');"

+

"window.location='dataMahasiswa.jsp';"

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
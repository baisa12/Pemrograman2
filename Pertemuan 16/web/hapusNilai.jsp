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

PreparedStatement pst=

conn.prepareStatement(

"DELETE FROM nilai WHERE nim=?"

);

pst.setString(
1,
nim
);

int hasil=

pst.executeUpdate();

if(hasil>0){

response.sendRedirect(
"inputNilai.jsp"
);

}else{

out.println(

"<script>"

+

"alert('Data gagal dihapus');"

+

"window.location='inputNilai.jsp';"

+

"</script>"

);

}

}catch(Exception e){

out.println(

e.getMessage()

);

}

%>
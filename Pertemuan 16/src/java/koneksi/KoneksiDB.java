package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class KoneksiDB {

Connection conn;

public Connection getKoneksi(){

try{

Class.forName(
"com.mysql.cj.jdbc.Driver"
);

conn=
DriverManager.getConnection(

"jdbc:mysql://localhost/db_nilai",

"root",

""

);

}catch(Exception e){

System.out.println(e);

}

return conn;

}

}
APLIKASI WEB RENT CAR - NETBEANS

Jenis project:
- Maven Web Application
- Java Servlet + JSP
- Database MySQL/XAMPP
- Server yang disarankan: Apache Tomcat 9

Menu aplikasi:
1. Dashboard
2. Data Mobil
3. Data Customer
4. Transaksi Penyewaan
5. Pengembalian Mobil
6. Laporan Transaksi + Cetak

Catatan penting:
- File SQL tidak dimasukkan ke ZIP sesuai permintaan.
- Jalankan file database_rentcar.sql melalui phpMyAdmin/XAMPP sebelum menjalankan project.
- Konfigurasi database ada di:
  src/main/java/com/rentcar/config/DatabaseConnection.java

Default koneksi database:
Database : rentcar_db
User     : root
Password : kosong
Host     : localhost
Port     : 3306

Jika password MySQL kamu berbeda, ubah bagian PASSWORD di DatabaseConnection.java.

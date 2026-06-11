// Deklarasi variabel sesuai materi
        String user="root";
        String pass="";
        String host="localhost";
        String db="mhs";
        String url="";
        java.io.File dir1=new java.io.File(".");
        String dirr="";

        try {
            // Pengaturan driver untuk MySQL versi baru
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            url="jdbc:mysql://"+ host +"/"+ db + "?user=" + user + "&password="+ pass;
            java.sql.Connection conn=(java.sql.Connection) java.sql.DriverManager.getConnection(url);
            
            java.sql.Statement st = (java.sql.Statement) conn.createStatement();

            // Mengambil file desain dari folder laporan
            dirr=dir1.getCanonicalPath()+"/src/laporan/";
            net.sf.jasperreports.engine.design.JasperDesign disain = net.sf.jasperreports.engine.xml.JRXmlLoader.load(dirr+"report1.jrxml");
            net.sf.jasperreports.engine.JasperReport nilaiLaporan = net.sf.jasperreports.engine.JasperCompileManager.compileReport(disain);
            
            // Eksekusi query
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM datamhs ");
            net.sf.jasperreports.engine.JRResultSetDataSource resultSetDataSource = new net.sf.jasperreports.engine.JRResultSetDataSource(rs);
            
            // Proses cetak
            net.sf.jasperreports.engine.JasperPrint cetak = net.sf.jasperreports.engine.JasperFillManager.fillReport(nilaiLaporan, new java.util.HashMap(), resultSetDataSource);
            
            // Menampilkan laporan
            net.sf.jasperreports.view.JasperViewer.viewReport(cetak, false);

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null,"Gagal mencetak\n" +ex,"Kesalahan",javax.swing.JOptionPane.ERROR_MESSAGE);
        }

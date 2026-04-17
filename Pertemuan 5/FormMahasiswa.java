import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*; 

public class FormMahasiswa extends JFrame {
    
    // Deklarasi Komponen GUI
    JLabel lNim, lNama, lSemester, lKelas;
    JTextField txtNim, txtNama, txtSemester, txtKelas;
    JButton btnSimpan;
    JTable tabelMhs;
    DefaultTableModel mod;
    JScrollPane scroll;

    // Deklarasi Variabel Koneksi Database
    Connection conn;

    public FormMahasiswa() {
        setTitle("Form Database Mahasiswa - MHS");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Mengatur Komponen GUI ---
        lNim = new JLabel("NIM"); 
        lNim.setBounds(30, 20, 100, 25); 
        add(lNim);
        
        txtNim = new JTextField(); 
        txtNim.setBounds(130, 20, 150, 25); 
        add(txtNim);

        lNama = new JLabel("Nama"); 
        lNama.setBounds(30, 60, 100, 25); 
        add(lNama);
        
        txtNama = new JTextField(); 
        txtNama.setBounds(130, 60, 200, 25); 
        add(txtNama);

        lSemester = new JLabel("Semester"); 
        lSemester.setBounds(30, 100, 100, 25); 
        add(lSemester);
        
        txtSemester = new JTextField(); 
        txtSemester.setBounds(130, 100, 50, 25); 
        add(txtSemester);

        lKelas = new JLabel("Kelas"); 
        lKelas.setBounds(30, 140, 100, 25); 
        add(lKelas);
        
        txtKelas = new JTextField(); 
        txtKelas.setBounds(130, 140, 50, 25); 
        add(txtKelas);

        btnSimpan = new JButton("Simpan"); 
        btnSimpan.setBounds(130, 180, 100, 30); 
        add(btnSimpan);

        // --- Mengatur JTable ---
        mod = new DefaultTableModel();
        mod.addColumn("NIM"); 
        mod.addColumn("Nama"); 
        mod.addColumn("Semester"); 
        mod.addColumn("Kelas");
        
        tabelMhs = new JTable(mod);
        scroll = new JScrollPane(tabelMhs);
        scroll.setBounds(30, 230, 420, 200);
        add(scroll);

        // 1. Panggil koneksi dan tampilkan data saat program dijalankan
        bukaKoneksi();
        tampilData();

        // 2. Event Handling Tombol Simpan
        btnSimpan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simpanData();
            }
        });
    }

    // --- Method untuk Membuka Koneksi ke XAMPP ---
    private void bukaKoneksi() {
        try {
            // PERUBAHAN UNTUK CONNECTOR VERSI 9:
            // Menggunakan com.mysql.cj.jdbc.Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Parameter timezone ditambahkan untuk menghindari error zona waktu pada MySQL versi baru
            String url = "jdbc:mysql://localhost:3306/MHS?serverTimezone=Asia/Jakarta";
            String user = "root";
            String password = ""; // Kosongkan jika XAMPP bawaan tidak pakai password
            
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi Database Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: Periksa XAMPP atau Library JDBC!\n" + e.getMessage());
        }
    }

    // --- Method untuk Menyimpan Data (INSERT) ---
    private void simpanData() {
        try {
            String sql = "INSERT INTO datamhs (nim, nama, semester, kelas) VALUES (?, ?, ?, ?)";
            PreparedStatement pStat = conn.prepareStatement(sql);
            
            pStat.setString(1, txtNim.getText());
            pStat.setString(2, txtNama.getText());
            pStat.setString(3, txtSemester.getText()); 
            pStat.setString(4, txtKelas.getText());
            
            pStat.executeUpdate(); 
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan ke Database!");
            
            txtNim.setText(""); 
            txtNama.setText(""); 
            txtSemester.setText(""); 
            txtKelas.setText("");
            txtNim.requestFocus();
            
            tampilData(); 
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan: " + e.getMessage());
        }
    }

    // --- Method untuk Menampilkan Data ke JTable (SELECT) ---
    private void tampilData() {
        mod.setRowCount(0); 
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM datamhs");
            
            while (rs.next()) {
                Object[] dataBaris = new Object[4];
                dataBaris[0] = rs.getString("nim");
                dataBaris[1] = rs.getString("nama");
                dataBaris[2] = rs.getString("semester");
                dataBaris[3] = rs.getString("kelas");
                
                mod.addRow(dataBaris); 
            }
        } catch (SQLException e) {
            System.out.println("Gagal membaca tabel: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new FormMahasiswa().setVisible(true);
    }
}

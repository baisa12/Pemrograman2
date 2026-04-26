import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FormMahasiswa extends JFrame {
    
    // Deklarasi Komponen GUI
    JLabel lNim, lNama, lSemester, lKelas;
    JTextField txtNim, txtNama, txtSemester, txtKelas;
    JButton btnSimpan, btnUbah, btnHapus, btnCari;
    JTable tabelMhs;
    DefaultTableModel mod;
    JScrollPane scroll;

    Connection conn;

    public FormMahasiswa() {
        setTitle("Form Database Mahasiswa - Lengkap (CRUD)");
        setSize(550, 500);
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

        // Tombol Cari diletakkan di sebelah NIM
        btnCari = new JButton("Cari");
        btnCari.setBounds(290, 20, 80, 25);
        add(btnCari);

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
        txtKelas.setBounds(130, 140, 100, 25); 
        add(txtKelas);

        // --- Jejeran Tombol Aksi ---
        btnSimpan = new JButton("Simpan"); 
        btnSimpan.setBounds(30, 180, 90, 30); 
        add(btnSimpan);

        btnUbah = new JButton("Ubah"); 
        btnUbah.setBounds(130, 180, 90, 30); 
        add(btnUbah);

        btnHapus = new JButton("Hapus"); 
        btnHapus.setBounds(230, 180, 90, 30); 
        add(btnHapus);

        // --- Mengatur JTable ---
        mod = new DefaultTableModel();
        mod.addColumn("NIM"); 
        mod.addColumn("Nama"); 
        mod.addColumn("Semester"); 
        mod.addColumn("Kelas");
        
        tabelMhs = new JTable(mod);
        scroll = new JScrollPane(tabelMhs);
        scroll.setBounds(30, 230, 460, 200);
        add(scroll);

        // Buka koneksi ke MySQL v9 dan tampilkan data awal
        bukaKoneksi();
        tampilData();

        // --- Event Handling Tombol ---
        btnSimpan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { simpanData(); }
        });

        btnCari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { cariData(); }
        });

        btnUbah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { ubahData(); }
        });

        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { hapusData(); }
        });
    }

    // Method Koneksi
    private void bukaKoneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/MHS?serverTimezone=Asia/Jakarta";
            conn = DriverManager.getConnection(url, "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage());
        }
    }

    // Method Menampilkan Data ke Tabel
    private void tampilData() {
        mod.setRowCount(0); 
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM datamhs");
            while (rs.next()) {
                Object[] dataBaris = { rs.getString("nim"), rs.getString("nama"), rs.getString("semester"), rs.getString("kelas") };
                mod.addRow(dataBaris); 
            }
        } catch (SQLException e) {
            System.out.println("Gagal membaca tabel: " + e.getMessage());
        }
    }

    // Method Simpan
    private void simpanData() {
        try {
            String sql = "INSERT INTO datamhs (nim, nama, semester, kelas) VALUES (?, ?, ?, ?)";
            PreparedStatement pStat = conn.prepareStatement(sql);
            pStat.setString(1, txtNim.getText());
            pStat.setString(2, txtNama.getText());
            pStat.setString(3, txtSemester.getText()); 
            pStat.setString(4, txtKelas.getText());
            
            pStat.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
            bersihkanForm();
            tampilData(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan: " + e.getMessage());
        }
    }

    // --- IMPLEMENTASI MATERI PERTEMUAN 6 ---

    // Method Cari Data (Berdasarkan NIM)
    private void cariData() {
        try {
            Statement st = conn.createStatement();
            // Mencari data yang NIM-nya sesuai dengan yang diketik
            String sql = "SELECT * FROM datamhs WHERE nim='" + txtNim.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            
            if (rs.next()) {
                // Jika ketemu, masukkan isinya ke kotak form
                txtNama.setText(rs.getString("nama"));
                txtSemester.setText(rs.getString("semester"));
                txtKelas.setText(rs.getString("kelas"));
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari data: " + e.getMessage());
        }
    }

    // Method Ubah Data
    private void ubahData() {
        try {
            // Update nama, semester, kelas berdasarkan patokan (WHERE) NIM
            String sql = "UPDATE datamhs SET nama=?, semester=?, kelas=? WHERE nim=?";
            PreparedStatement pS = conn.prepareStatement(sql);
            
            pS.setString(1, txtNama.getText());
            pS.setString(2, txtSemester.getText());
            pS.setString(3, txtKelas.getText());
            pS.setString(4, txtNim.getText()); // NIM diletakkan terakhir sesuai posisi tanda tanya (?)
            
            if (pS.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Edit Sukses", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                bersihkanForm();
                tampilData();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Edit Gagal: " + e.getMessage(), "Informasi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method Hapus Data
    private void hapusData() {
        try {
            // Memunculkan kotak dialog konfirmasi yes/no
            int x = JOptionPane.showConfirmDialog(null, "Data Yakin akan Dihapus?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            
            if (x == JOptionPane.YES_OPTION) {
                String sql = "DELETE FROM datamhs WHERE nim=?";
                PreparedStatement pS = conn.prepareStatement(sql);
                pS.setString(1, txtNim.getText());
                
                // Di slide tertulis executeDelete(), tapi yang benar di Java adalah executeUpdate()
                pS.executeUpdate(); 
                
                JOptionPane.showMessageDialog(this, "Data Telah Dihapus", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                bersihkanForm();
                tampilData();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Data Gagal Dihapus: " + e.getMessage(), "Informasi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method bantuan untuk mengosongkan text field
    private void bersihkanForm() {
        txtNim.setText("");
        txtNama.setText("");
        txtSemester.setText("");
        txtKelas.setText("");
        txtNim.requestFocus();
    }

    public static void main(String[] args) {
        new FormMahasiswa().setVisible(true);
    }
}
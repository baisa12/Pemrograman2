import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LatihanTabel extends JFrame {
    
    // Deklarasi Komponen
    JLabel labelNim, labelNama, labelNilai;
    JTextField txtNim, txtNama, txtNilai;
    JButton btnTambah;
    JTable tabelMahasiswa;
    DefaultTableModel mod; // Digunakan untuk mengatur isi JTable
    JScrollPane scrollPane; // Agar tabel bisa di-scroll

    public LatihanTabel() {
        setTitle("Latihan JTable Pertemuan 4");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Mengatur Label dan Input Field
        labelNim = new JLabel("N I M");
        labelNim.setBounds(30, 20, 120, 25);
        add(labelNim);

        txtNim = new JTextField();
        txtNim.setBounds(150, 20, 150, 25);
        add(txtNim);

        labelNama = new JLabel("Nama Mahasiswa");
        labelNama.setBounds(30, 60, 120, 25);
        add(labelNama);

        txtNama = new JTextField();
        txtNama.setBounds(150, 60, 200, 25);
        add(txtNama);

        labelNilai = new JLabel("Nilai");
        labelNilai.setBounds(30, 100, 120, 25);
        add(labelNilai);

        txtNilai = new JTextField();
        txtNilai.setBounds(150, 100, 100, 25);
        add(txtNilai);

        // 2. Mengatur Tombol
        btnTambah = new JButton("TABEL");
        btnTambah.setBounds(360, 20, 90, 40);
        add(btnTambah);

        // 3. Mengatur JTable dan DefaultTableModel
        mod = new DefaultTableModel();
        tabelMahasiswa = new JTable(mod); // Memasukkan model ke dalam tabel
        
        // Menambahkan judul kolom sesuai materi
        mod.addColumn("N I M");
        mod.addColumn("Nama Mahasiswa");
        mod.addColumn("Nilai");

        // Memasukkan tabel ke dalam JScrollPane lalu menempatkannya di Frame
        scrollPane = new JScrollPane(tabelMahasiswa);
        scrollPane.setBounds(30, 150, 420, 180);
        add(scrollPane);

        // 4. Logika saat tombol TABEL diklik
        btnTambah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mengambil teks dari inputan
                String nim = txtNim.getText();
                String nama = txtNama.getText();
                String nilai = txtNilai.getText();

                // Pastikan input tidak kosong sebelum dimasukkan ke tabel
                if (!nim.isEmpty() && !nama.isEmpty() && !nilai.isEmpty()) {
                    // Membuat array Object untuk menampung 1 baris data
                    Object[] data = {nim, nama, nilai}; 
                    
                    // Memasukkan data ke dalam baris tabel
                    mod.addRow(data);

                    // Mengosongkan text field setelah data masuk
                    txtNim.setText("");
                    txtNama.setText("");
                    txtNilai.setText("");
                    txtNim.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Tolong isi semua data terlebih dahulu!");
                }
            }
        });
    }

    public static void main(String[] args) {
        new LatihanTabel().setVisible(true);
    }
}

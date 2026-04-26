import javax.swing.*;

public class ClassTujuan extends JFrame {
    
    JLabel labelJudul, labelNim, labelNama;
    JTextField txtNim, txtNama;

    public ClassTujuan() {
        setTitle("Frame Tujuan");
        setSize(400, 250);
        setLayout(null);
        
        // PENTING: Gunakan DISPOSE_ON_CLOSE. 
        // Supaya kalau jendela ini disilang (close), menu utamanya tidak ikut tertutup.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 

        // Membuat komponen
        labelJudul = new JLabel("FRAME YANG DIPANGGIL", SwingConstants.CENTER);
        labelJudul.setBounds(50, 20, 300, 25);
        add(labelJudul);

        labelNim = new JLabel("NIM");
        labelNim.setBounds(50, 70, 120, 25);
        add(labelNim);

        txtNim = new JTextField();
        txtNim.setBounds(180, 70, 150, 25);
        add(txtNim);

        labelNama = new JLabel("Nama Mahasiswa");
        labelNama.setBounds(50, 110, 120, 25);
        add(labelNama);

        txtNama = new JTextField();
        txtNama.setBounds(180, 110, 150, 25);
        add(txtNama);
    }
    
    // File ini tidak butuh public static void main karena dia dipanggil oleh file LatPanggil
}
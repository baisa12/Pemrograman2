import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LatPanggil extends JFrame {
    
    JLabel labelJudul;
    JButton btnPanggil;

    public LatPanggil() {
        setTitle("Menu Utama Panggil Frame");
        setSize(400, 200);
        setLayout(null);
        // EXIT_ON_CLOSE agar saat jendela ini ditutup, program berhenti
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // Membuat tulisan judul
        labelJudul = new JLabel("MEMANGGIL - MENAMPILKAN FRAME LAIN", SwingConstants.CENTER);
        labelJudul.setBounds(20, 30, 350, 25);
        add(labelJudul);

        // Membuat tombol
        btnPanggil = new JButton("PANGGIL FRAME");
        btnPanggil.setBounds(100, 80, 180, 30);
        add(btnPanggil);

        // Logika saat tombol diklik
        btnPanggil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Baris kode ini berfungsi memanggil dan memunculkan classTujuan
                new ClassTujuan().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new LatPanggil().setVisible(true);
    }
}
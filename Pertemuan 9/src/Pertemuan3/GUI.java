import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Nama class diubah menjadi GUI
public class GUI extends JFrame {
    
    // Komponen dasar sesuai materi [cite: 9]
    JLabel labelSatu, labelDua, labelHasil; // JLabel untuk keterangan [cite: 16, 20]
    JTextField textSatu, textDua, textHasil; // JTextField untuk input [cite: 17, 21]
    JButton btnTambah, btnHapus, btnExit; // JButton untuk proses [cite: 18, 22]

    public GUI() {
        // Setup dasar Frame
        setTitle("Program Pertambahan - GUI");
        setSize(400, 300);
        setLayout(null); // Mengatur posisi komponen secara manual
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Mengatur JLabel [cite: 16]
        labelSatu = new JLabel("Angka Pertama");
        labelSatu.setBounds(30, 30, 100, 25);
        add(labelSatu);

        labelDua = new JLabel("Angka Kedua");
        labelDua.setBounds(30, 70, 100, 25);
        add(labelDua);

        labelHasil = new JLabel("Hasil");
        labelHasil.setBounds(30, 110, 100, 25);
        add(labelHasil);

        // 2. Mengatur JTextField untuk input data [cite: 17]
        textSatu = new JTextField();
        textSatu.setBounds(150, 30, 150, 25);
        add(textSatu);

        textDua = new JTextField();
        textDua.setBounds(150, 70, 150, 25);
        add(textDua);

        textHasil = new JTextField();
        textHasil.setBounds(150, 110, 150, 25);
        textHasil.setEditable(false); 
        add(textHasil);

        // 3. Mengatur JButton untuk menjalankan proses [cite: 18]
        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(30, 170, 80, 25);
        add(btnTambah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(130, 170, 80, 25);
        add(btnHapus);

        btnExit = new JButton("Exit");
        btnExit.setBounds(230, 170, 80, 25);
        add(btnExit);

        // Event Handling untuk menangani klik user [cite: 14]
        btnTambah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int angka1 = Integer.parseInt(textSatu.getText());
                    int angka2 = Integer.parseInt(textDua.getText());
                    int hasil = angka1 + angka2;
                    textHasil.setText(String.valueOf(hasil));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Masukkan angka yang valid!");
                }
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textSatu.setText("");
                textDua.setText("");
                textHasil.setText("");
                textSatu.requestFocus();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new GUI().setVisible(true);
    }
}
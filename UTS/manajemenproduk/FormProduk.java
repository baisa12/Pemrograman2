package manajemenproduk;

import javax.swing.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Comparator;

public class FormProduk extends JFrame {

    StackProduk data = new StackProduk();

    JTextField txtNama = new JTextField();
    JTextField txtHarga = new JTextField();
    JTextField txtCari = new JTextField();

    JTextArea area = new JTextArea();

    JButton btnTambah = new JButton("Tambah");
    JButton btnHapus = new JButton("Hapus");
    JButton btnSort = new JButton("Sort Harga");
    JButton btnCari = new JButton("Cari");

    public FormProduk() {

        setTitle("Manajemen Produk");
        setSize(500, 500);
        setLayout(null);

        JLabel l1 = new JLabel("Nama Produk");
        l1.setBounds(20, 20, 100, 25);
        add(l1);

        txtNama.setBounds(130, 20, 150, 25);
        add(txtNama);

        JLabel l2 = new JLabel("Harga");
        l2.setBounds(20, 60, 100, 25);
        add(l2);

        txtHarga.setBounds(130, 60, 150, 25);
        add(txtHarga);

        btnTambah.setBounds(20, 100, 100, 30);
        add(btnTambah);

        btnHapus.setBounds(130, 100, 100, 30);
        add(btnHapus);

        btnSort.setBounds(240, 100, 120, 30);
        add(btnSort);

        JLabel l3 = new JLabel("Cari");
        l3.setBounds(20, 150, 100, 25);
        add(l3);

        txtCari.setBounds(130, 150, 150, 25);
        add(txtCari);

        btnCari.setBounds(300, 150, 100, 25);
        add(btnCari);

        JScrollPane pane = new JScrollPane(area);
        pane.setBounds(20, 200, 430, 200);
        add(pane);

        tampilData();

        btnTambah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    String nama = txtNama.getText();
                    int harga = Integer.parseInt(txtHarga.getText());

                    Produk p = new Produk(nama, harga);

                    data.tambahProduk(p);

                    tampilData();

                    txtNama.setText("");
                    txtHarga.setText("");

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null,
                            "Input salah!");

                }

            }
        });

        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    data.hapusProduk();

                    tampilData();

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null,
                            ex.getMessage());

                }

            }
        });

        btnSort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Collections.sort(data.getData(),
                        new Comparator<Produk>() {

                    public int compare(Produk a,
                            Produk b) {

                        return a.harga - b.harga;
                    }
                });

                tampilData();

            }
        });

        btnCari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String cari = txtCari.getText();

                area.setText("");

                for (Produk p : data.getData()) {

                    if (p.nama.toLowerCase()
                            .contains(cari.toLowerCase())) {

                        area.append(p.toString()
                                + "\n");
                    }
                }
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void tampilData() {

        area.setText("");

        for (Produk p : data.getData()) {

            area.append(p.toString() + "\n");
        }
    }
}
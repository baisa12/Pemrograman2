package com.unpam.controller;

import com.unpam.model.Mahasiswa;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MahasiswaController extends HttpServlet {

    private final MainForm mainForm = new MainForm();

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        Mahasiswa mahasiswa = new Mahasiswa();

        String tombol = request.getParameter("tombol");
        String pesan = "";

        String nim = nilai(request.getParameter("nim"));
        String nama = nilai(request.getParameter("nama"));
        int semester = angka(request.getParameter("semester"));
        String kelas = nilai(request.getParameter("kelas"));

        if ("Simpan".equals(tombol)) {
            mahasiswa.setNim(nim);
            mahasiswa.setNama(nama);
            mahasiswa.setSemester(semester);
            mahasiswa.setKelas(kelas);

            if (mahasiswa.simpan()) {
                pesan = "<p style='color:green;'>Data mahasiswa berhasil disimpan</p>";
            } else {
                pesan = "<p style='color:red;'>" + mahasiswa.getPesan() + "</p>";
            }

        } else if ("Cari".equals(tombol)) {
            if (mahasiswa.baca(nim)) {
                nim = mahasiswa.getNim();
                nama = mahasiswa.getNama();
                semester = mahasiswa.getSemester();
                kelas = mahasiswa.getKelas();
            } else {
                pesan = "<p style='color:red;'>" + mahasiswa.getPesan() + "</p>";
            }

        } else if ("Hapus".equals(tombol)) {
            if (mahasiswa.hapus(nim)) {
                pesan = "<p style='color:green;'>Data mahasiswa berhasil dihapus</p>";
                nim = "";
                nama = "";
                semester = 0;
                kelas = "";
            } else {
                pesan = "<p style='color:red;'>" + mahasiswa.getPesan() + "</p>";
            }
        }

        String konten = pesan + formMahasiswa(nim, nama, semester, kelas);

        if ("Lihat".equals(tombol)) {
            konten += tabelMahasiswa();
        }

        mainForm.tampilan(konten, request, response);
    }

    private String formMahasiswa(String nim, String nama, int semester, String kelas) {
        return "<h2>Data Mahasiswa</h2>"
                + "<form action='MahasiswaController' method='post'>"
                + "<table class='form-table'>"
                + "<tr><td>NIM</td><td><input type='text' name='nim' value='" + esc(nim) + "'>"
                + "<input type='submit' name='tombol' value='Cari'>"
                + "<input type='submit' name='tombol' value='Lihat'></td></tr>"
                + "<tr><td>Nama</td><td><input type='text' name='nama' value='" + esc(nama) + "'></td></tr>"
                + "<tr><td>Semester</td><td><input type='text' name='semester' value='" + semester + "' class='input-kecil'></td></tr>"
                + "<tr><td>Kelas</td><td><input type='text' name='kelas' value='" + esc(kelas) + "' class='input-sedang'></td></tr>"
                + "<tr><td></td><td>"
                + "<input type='submit' name='tombol' value='Simpan' class='btn'> "
                + "<input type='submit' name='tombol' value='Hapus' class='btn'>"
                + "</td></tr>"
                + "</table>"
                + "</form>";
    }

    private String tabelMahasiswa() {
        Mahasiswa mahasiswa = new Mahasiswa();

        if (!mahasiswa.bacaData(0, 100)) {
            return "<p style='color:red;'>" + mahasiswa.getPesan() + "</p>";
        }

        Object[][] data = mahasiswa.getList();

        String html = "<br><h3>Daftar Mahasiswa</h3>"
                + "<table border='1' cellspacing='0' cellpadding='4' align='center'>"
                + "<tr><th>NIM</th><th>Nama</th><th>Semester</th><th>Kelas</th></tr>";

        for (int i = 0; i < data.length; i++) {
            html += "<tr>"
                    + "<td>" + data[i][0] + "</td>"
                    + "<td>" + data[i][1] + "</td>"
                    + "<td>" + data[i][2] + "</td>"
                    + "<td>" + data[i][3] + "</td>"
                    + "</tr>";
        }

        html += "</table>";

        return html;
    }

    private String nilai(String value) {
        return value == null ? "" : value;
    }

    private int angka(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return 0;
        }
    }

    private String esc(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("'", "&#39;")
                .replace("\"", "&quot;");
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
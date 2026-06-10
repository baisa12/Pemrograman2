package com.unpam.controller;

import com.unpam.model.Enkripsi;
import com.unpam.model.Mahasiswa;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MahasiswaController", urlPatterns = {"/MahasiswaController"})
public class MahasiswaController extends HttpServlet {

    private final Enkripsi enkripsi = new Enkripsi();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tombol = request.getParameter("btn");

        if (tombol == null) {
            tombol = "";
        }

        Mahasiswa mahasiswa = new Mahasiswa();
        String pesan = "";

        String nim = ambilParameter(request, "nim");
        String nama = ambilParameter(request, "nama");
        String kelas = ambilParameter(request, "kelas");
        String semesterText = ambilParameter(request, "semester");
        String password = ambilParameter(request, "password");

        if (tombol.equals("Cari")) {
            if (!nim.equals("")) {
                if (!mahasiswa.baca(nim)) {
                    pesan = "<div class='pesan-error'>" + escape(mahasiswa.getPesan()) + "</div>";
                    mahasiswa.setNim(nim);
                }
            }
        } else if (tombol.equals("Simpan")) {
            int semester = ubahKeInt(semesterText);

            mahasiswa.setNim(nim);
            mahasiswa.setNama(nama);
            mahasiswa.setSemester(semester);
            mahasiswa.setKelas(kelas);

            try {
                if (password.equals("")) {
                    Mahasiswa dataLama = new Mahasiswa();
                    if (dataLama.baca(nim)) {
                        mahasiswa.setPassword(dataLama.getPassword());
                    } else {
                        mahasiswa.setPassword(enkripsi.hashMD5("123"));
                    }
                } else {
                    mahasiswa.setPassword(enkripsi.hashMD5(password));
                }

                if (validasiMahasiswa(mahasiswa)) {
                    if (mahasiswa.simpan()) {
                        pesan = "<div class='pesan-sukses'>Data mahasiswa berhasil disimpan.</div>";
                    } else {
                        pesan = "<div class='pesan-error'>" + escape(mahasiswa.getPesan()) + "</div>";
                    }
                } else {
                    pesan = "<div class='pesan-error'>NIM, nama, semester, dan kelas wajib diisi.</div>";
                }
            } catch (Exception ex) {
                pesan = "<div class='pesan-error'>Password gagal dienkripsi: " + escape(ex.getMessage()) + "</div>";
            }
        } else if (tombol.equals("Hapus")) {
            mahasiswa.setNim(nim);

            if (!nim.equals("")) {
                if (mahasiswa.hapus(nim)) {
                    pesan = "<div class='pesan-sukses'>Data mahasiswa berhasil dihapus.</div>";
                    mahasiswa = new Mahasiswa();
                } else {
                    pesan = "<div class='pesan-error'>" + escape(mahasiswa.getPesan()) + "</div>";
                }
            } else {
                pesan = "<div class='pesan-error'>Masukkan NIM yang akan dihapus.</div>";
            }
        } else if (tombol.equals("Baru")) {
            mahasiswa = new Mahasiswa();
        } else {
            mahasiswa.setNim(nim);
        }

        String konten = "<br><h2>Master Data Mahasiswa</h2>"
                + pesan
                + formMahasiswa(mahasiswa)
                + "<br>"
                + tabelMahasiswa();

        MainForm mainForm = new MainForm();
        mainForm.tampilkan(request, response, konten);
    }

    private String formMahasiswa(Mahasiswa mahasiswa) {
        String nim = escape(mahasiswa.getNim());
        String nama = escape(mahasiswa.getNama());
        String kelas = escape(mahasiswa.getKelas());
        String semester = mahasiswa.getSemester() == 0 ? "" : String.valueOf(mahasiswa.getSemester());

        return "<form method='post' action='MahasiswaController'>"
                + "<table class='form-table'>"
                + "<tr>"
                + "<td>NIM</td>"
                + "<td><input type='text' name='nim' value='" + nim + "' required></td>"
                + "<td><input type='submit' name='btn' value='Cari'></td>"
                + "</tr>"
                + "<tr>"
                + "<td>Nama</td>"
                + "<td colspan='2'><input type='text' name='nama' value='" + nama + "' required></td>"
                + "</tr>"
                + "<tr>"
                + "<td>Semester</td>"
                + "<td colspan='2'><input type='number' name='semester' value='" + semester + "' min='1' max='14' required></td>"
                + "</tr>"
                + "<tr>"
                + "<td>Kelas</td>"
                + "<td colspan='2'><input type='text' name='kelas' value='" + kelas + "' required></td>"
                + "</tr>"
                + "<tr>"
                + "<td>Password</td>"
                + "<td colspan='2'><input type='password' name='password' placeholder='Isi jika ingin ganti password'></td>"
                + "</tr>"
                + "<tr>"
                + "<td></td>"
                + "<td colspan='2'>"
                + "<input type='submit' name='btn' value='Simpan'> "
                + "<input type='submit' name='btn' value='Hapus'> "
                + "<input type='submit' name='btn' value='Baru'>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</form>";
    }

    private String tabelMahasiswa() {
        Mahasiswa mahasiswa = new Mahasiswa();
        StringBuilder html = new StringBuilder();

        html.append("<h3>Daftar Mahasiswa</h3>");

        if (mahasiswa.bacaData()) {
            Object[][] data = mahasiswa.getList();

            html.append("<table class='data-table'>");
            html.append("<tr>");
            html.append("<th>No</th>");
            html.append("<th>NIM</th>");
            html.append("<th>Nama</th>");
            html.append("<th>Semester</th>");
            html.append("<th>Kelas</th>");
            html.append("<th>Aksi</th>");
            html.append("</tr>");

            for (int i = 0; i < data.length; i++) {
                String nim = escape(String.valueOf(data[i][0]));
                String nama = escape(String.valueOf(data[i][1]));
                String semester = escape(String.valueOf(data[i][2]));
                String kelas = escape(String.valueOf(data[i][3]));

                html.append("<tr>");
                html.append("<td>").append(i + 1).append("</td>");
                html.append("<td>").append(nim).append("</td>");
                html.append("<td>").append(nama).append("</td>");
                html.append("<td>").append(semester).append("</td>");
                html.append("<td>").append(kelas).append("</td>");
                html.append("<td><a href='MahasiswaController?btn=Cari&nim=").append(nim).append("'>Pilih</a></td>");
                html.append("</tr>");
            }

            html.append("</table>");
        } else {
            html.append("<div class='pesan-error'>").append(escape(mahasiswa.getPesan())).append("</div>");
        }

        return html.toString();
    }

    private boolean validasiMahasiswa(Mahasiswa mahasiswa) {
        return mahasiswa.getNim() != null && !mahasiswa.getNim().trim().equals("")
                && mahasiswa.getNama() != null && !mahasiswa.getNama().trim().equals("")
                && mahasiswa.getSemester() > 0
                && mahasiswa.getKelas() != null && !mahasiswa.getKelas().trim().equals("");
    }

    private String ambilParameter(HttpServletRequest request, String namaParameter) {
        String value = request.getParameter(namaParameter);

        if (value == null) {
            return "";
        }

        return value.trim();
    }

    private int ubahKeInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return 0;
        }
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#039;");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
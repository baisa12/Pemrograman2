package com.unpam.controller;

import com.unpam.model.MataKuliah;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MataKuliahController", urlPatterns = {"/MataKuliahController"})
public class MataKuliahController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tombol = request.getParameter("btn");

        if (tombol == null) {
            tombol = "";
        }

        MataKuliah mataKuliah = new MataKuliah();
        String pesan = "";

        String kodeMataKuliah = ambilParameter(request, "kodeMataKuliah");
        String namaMataKuliah = ambilParameter(request, "namaMataKuliah");
        String jumlahSksText = ambilParameter(request, "jumlahSks");

        if (tombol.equals("Cari")) {
            if (!kodeMataKuliah.equals("")) {
                if (!mataKuliah.baca(kodeMataKuliah)) {
                    pesan = "<div class='pesan-error'>" + escape(mataKuliah.getPesan()) + "</div>";
                    mataKuliah.setKodeMataKuliah(kodeMataKuliah);
                }
            }
        } else if (tombol.equals("Simpan")) {
            int jumlahSks = ubahKeInt(jumlahSksText);

            mataKuliah.setKodeMataKuliah(kodeMataKuliah);
            mataKuliah.setNamaMataKuliah(namaMataKuliah);
            mataKuliah.setJumlahSks(jumlahSks);

            if (validasiMataKuliah(mataKuliah)) {
                if (mataKuliah.simpan()) {
                    pesan = "<div class='pesan-sukses'>Data mata kuliah berhasil disimpan.</div>";
                } else {
                    pesan = "<div class='pesan-error'>" + escape(mataKuliah.getPesan()) + "</div>";
                }
            } else {
                pesan = "<div class='pesan-error'>Kode, nama mata kuliah, dan jumlah SKS wajib diisi.</div>";
            }
        } else if (tombol.equals("Hapus")) {
            mataKuliah.setKodeMataKuliah(kodeMataKuliah);

            if (!kodeMataKuliah.equals("")) {
                if (mataKuliah.hapus(kodeMataKuliah)) {
                    pesan = "<div class='pesan-sukses'>Data mata kuliah berhasil dihapus.</div>";
                    mataKuliah = new MataKuliah();
                } else {
                    pesan = "<div class='pesan-error'>" + escape(mataKuliah.getPesan()) + "</div>";
                }
            } else {
                pesan = "<div class='pesan-error'>Masukkan kode mata kuliah yang akan dihapus.</div>";
            }
        } else if (tombol.equals("Baru")) {
            mataKuliah = new MataKuliah();
        } else {
            mataKuliah.setKodeMataKuliah(kodeMataKuliah);
        }

        String konten = "<br><h2>Master Data Mata Kuliah</h2>"
                + pesan
                + formMataKuliah(mataKuliah)
                + "<br>"
                + tabelMataKuliah();

        MainForm mainForm = new MainForm();
        mainForm.tampilkan(request, response, konten);
    }

    private String formMataKuliah(MataKuliah mataKuliah) {
        String kodeMataKuliah = escape(mataKuliah.getKodeMataKuliah());
        String namaMataKuliah = escape(mataKuliah.getNamaMataKuliah());
        String jumlahSks = mataKuliah.getJumlahSks() == 0 ? "" : String.valueOf(mataKuliah.getJumlahSks());

        return "<form method='post' action='MataKuliahController'>"
                + "<table class='form-table'>"
                + "<tr>"
                + "<td>Kode Mata Kuliah</td>"
                + "<td><input type='text' name='kodeMataKuliah' value='" + kodeMataKuliah + "' required></td>"
                + "<td><input type='submit' name='btn' value='Cari'></td>"
                + "</tr>"
                + "<tr>"
                + "<td>Nama Mata Kuliah</td>"
                + "<td colspan='2'><input type='text' name='namaMataKuliah' value='" + namaMataKuliah + "' required></td>"
                + "</tr>"
                + "<tr>"
                + "<td>Jumlah SKS</td>"
                + "<td colspan='2'><input type='number' name='jumlahSks' value='" + jumlahSks + "' min='1' max='6' required></td>"
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

    private String tabelMataKuliah() {
        MataKuliah mataKuliah = new MataKuliah();
        StringBuilder html = new StringBuilder();

        html.append("<h3>Daftar Mata Kuliah</h3>");

        if (mataKuliah.bacaData()) {
            Object[][] data = mataKuliah.getList();

            html.append("<table class='data-table'>");
            html.append("<tr>");
            html.append("<th>No</th>");
            html.append("<th>Kode</th>");
            html.append("<th>Nama Mata Kuliah</th>");
            html.append("<th>Jumlah SKS</th>");
            html.append("<th>Aksi</th>");
            html.append("</tr>");

            for (int i = 0; i < data.length; i++) {
                String kode = escape(String.valueOf(data[i][0]));
                String nama = escape(String.valueOf(data[i][1]));
                String sks = escape(String.valueOf(data[i][2]));

                html.append("<tr>");
                html.append("<td>").append(i + 1).append("</td>");
                html.append("<td>").append(kode).append("</td>");
                html.append("<td>").append(nama).append("</td>");
                html.append("<td>").append(sks).append("</td>");
                html.append("<td><a href='MataKuliahController?btn=Cari&kodeMataKuliah=").append(kode).append("'>Pilih</a></td>");
                html.append("</tr>");
            }

            html.append("</table>");
        } else {
            html.append("<div class='pesan-error'>").append(escape(mataKuliah.getPesan())).append("</div>");
        }

        return html.toString();
    }

    private boolean validasiMataKuliah(MataKuliah mataKuliah) {
        return mataKuliah.getKodeMataKuliah() != null && !mataKuliah.getKodeMataKuliah().trim().equals("")
                && mataKuliah.getNamaMataKuliah() != null && !mataKuliah.getNamaMataKuliah().trim().equals("")
                && mataKuliah.getJumlahSks() > 0;
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
package com.unpam.controller;

import com.unpam.model.MataKuliah;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MataKuliahController extends HttpServlet {

    private final MainForm mainForm = new MainForm();

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        MataKuliah mataKuliah = new MataKuliah();

        String tombol = request.getParameter("tombol");
        String pesan = "";

        String kodeMataKuliah = nilai(request.getParameter("kodeMataKuliah"));
        String namaMataKuliah = nilai(request.getParameter("namaMataKuliah"));
        int jumlahSKS = angka(request.getParameter("jumlahSKS"));

        if ("Simpan".equals(tombol)) {
            mataKuliah.setKodeMataKuliah(kodeMataKuliah);
            mataKuliah.setNamaMataKuliah(namaMataKuliah);
            mataKuliah.setJumlahSKS(jumlahSKS);

            if (mataKuliah.simpan()) {
                pesan = "<p style='color:green;'>Data mata kuliah berhasil disimpan</p>";
            } else {
                pesan = "<p style='color:red;'>" + mataKuliah.getPesan() + "</p>";
            }

        } else if ("Cari".equals(tombol)) {
            if (mataKuliah.baca(kodeMataKuliah)) {
                kodeMataKuliah = mataKuliah.getKodeMataKuliah();
                namaMataKuliah = mataKuliah.getNamaMataKuliah();
                jumlahSKS = mataKuliah.getJumlahSKS();
            } else {
                pesan = "<p style='color:red;'>" + mataKuliah.getPesan() + "</p>";
            }

        } else if ("Hapus".equals(tombol)) {
            if (mataKuliah.hapus(kodeMataKuliah)) {
                pesan = "<p style='color:green;'>Data mata kuliah berhasil dihapus</p>";
                kodeMataKuliah = "";
                namaMataKuliah = "";
                jumlahSKS = 0;
            } else {
                pesan = "<p style='color:red;'>" + mataKuliah.getPesan() + "</p>";
            }
        }

        String konten = pesan + formMataKuliah(kodeMataKuliah, namaMataKuliah, jumlahSKS);

        if ("Lihat".equals(tombol)) {
            konten += tabelMataKuliah();
        }

        mainForm.tampilan(konten, request, response);
    }

    private String formMataKuliah(String kodeMataKuliah, String namaMataKuliah, int jumlahSKS) {
        return "<h2>Data Mata Kuliah</h2>"
                + "<form action='MataKuliahController' method='post'>"
                + "<table class='form-table'>"
                + "<tr><td>Kode Mata Kuliah</td><td><input type='text' name='kodeMataKuliah' value='" + esc(kodeMataKuliah) + "'>"
                + "<input type='submit' name='tombol' value='Cari'>"
                + "<input type='submit' name='tombol' value='Lihat'></td></tr>"
                + "<tr><td>Nama Mata Kuliah</td><td><input type='text' name='namaMataKuliah' value='" + esc(namaMataKuliah) + "'></td></tr>"
                + "<tr><td>Jumlah SKS</td><td><input type='text' name='jumlahSKS' value='" + jumlahSKS + "' class='input-kecil'></td></tr>"
                + "<tr><td></td><td>"
                + "<input type='submit' name='tombol' value='Simpan' class='btn'> "
                + "<input type='submit' name='tombol' value='Hapus' class='btn'>"
                + "</td></tr>"
                + "</table>"
                + "</form>";
    }

    private String tabelMataKuliah() {
        MataKuliah mataKuliah = new MataKuliah();

        if (!mataKuliah.bacaData(0, 100)) {
            return "<p style='color:red;'>" + mataKuliah.getPesan() + "</p>";
        }

        Object[][] data = mataKuliah.getList();

        String html = "<br><h3>Daftar Mata Kuliah</h3>"
                + "<table border='1' cellspacing='0' cellpadding='4' align='center'>"
                + "<tr><th>Kode</th><th>Nama Mata Kuliah</th><th>SKS</th></tr>";

        for (int i = 0; i < data.length; i++) {
            html += "<tr>"
                    + "<td>" + data[i][0] + "</td>"
                    + "<td>" + data[i][1] + "</td>"
                    + "<td>" + data[i][2] + "</td>"
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
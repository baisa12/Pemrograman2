package com.unpam.controller;

import com.unpam.model.Nilai;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NilaiController extends HttpServlet {

    private final MainForm mainForm = new MainForm();

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Nilai nilai = new Nilai();

        String aksi = request.getParameter("aksi");
        String pesan = "";

        String nim = ambil(request.getParameter("nim"));
        String kodeMataKuliah = ambil(request.getParameter("kodeMataKuliah"));

        int nilaiTugas = angka(request.getParameter("nilaiTugas"));
        int nilaiUTS = angka(request.getParameter("nilaiUTS"));
        int nilaiUAS = angka(request.getParameter("nilaiUAS"));

        if ("cariMahasiswa".equals(aksi)) {
            if (nim.equals("")) {
                pesan = "<p class='pesan-error'>NIM belum diisi</p>";
            } else if (!nilai.bacaMahasiswa(nim)) {
                pesan = "<p class='pesan-error'>" + nilai.getPesan() + "</p>";
            }

        } else if ("cariMataKuliah".equals(aksi)) {
            if (kodeMataKuliah.equals("")) {
                pesan = "<p class='pesan-error'>Kode mata kuliah belum diisi</p>";
            } else if (!nilai.bacaMataKuliah(kodeMataKuliah)) {
                pesan = "<p class='pesan-error'>" + nilai.getPesan() + "</p>";
            }

        } else if ("cariNilai".equals(aksi)) {
            if (nim.equals("") || kodeMataKuliah.equals("")) {
                pesan = "<p class='pesan-error'>NIM dan kode mata kuliah harus diisi</p>";
            } else if (!nilai.bacaNilai(nim, kodeMataKuliah)) {
                pesan = "<p class='pesan-error'>" + nilai.getPesan() + "</p>";
            }

        } else if ("simpan".equals(aksi)) {
            if (nim.equals("") || kodeMataKuliah.equals("")) {
                pesan = "<p class='pesan-error'>NIM dan kode mata kuliah harus diisi</p>";
            } else {
                nilai.setNim(nim);
                nilai.setKodeMataKuliah(kodeMataKuliah);
                nilai.setNilaiTugas(nilaiTugas);
                nilai.setNilaiUTS(nilaiUTS);
                nilai.setNilaiUAS(nilaiUAS);

                if (nilai.simpan()) {
                    pesan = "<p class='pesan-sukses'>Data nilai berhasil disimpan</p>";
                    nilai.bacaNilai(nim, kodeMataKuliah);
                } else {
                    pesan = "<p class='pesan-error'>" + nilai.getPesan() + "</p>";
                }
            }

        } else if ("hapus".equals(aksi)) {
            if (nim.equals("") || kodeMataKuliah.equals("")) {
                pesan = "<p class='pesan-error'>NIM dan kode mata kuliah harus diisi</p>";
            } else if (nilai.hapus(nim, kodeMataKuliah)) {
                pesan = "<p class='pesan-sukses'>Data nilai berhasil dihapus</p>";
                nilai = new Nilai();
            } else {
                pesan = "<p class='pesan-error'>" + nilai.getPesan() + "</p>";
            }
        }

        String konten = pesan + formNilai(nilai, nim, kodeMataKuliah, nilaiTugas, nilaiUTS, nilaiUAS);
        mainForm.tampilan(konten, request, response);
    }

    private String formNilai(Nilai nilai, String nimInput, String kodeInput,
            int tugasInput, int utsInput, int uasInput) {

        String nim = nilai.getNim() != null && !nilai.getNim().equals("") ? nilai.getNim() : nimInput;
        String nama = nilai.getNama() != null ? nilai.getNama() : "";
        String kelas = nilai.getKelas() != null ? nilai.getKelas() : "";
        int semester = nilai.getSemester();

        String kodeMataKuliah = nilai.getKodeMataKuliah() != null && !nilai.getKodeMataKuliah().equals("")
                ? nilai.getKodeMataKuliah() : kodeInput;

        String namaMataKuliah = nilai.getNamaMataKuliah() != null ? nilai.getNamaMataKuliah() : "";
        int jumlahSKS = nilai.getJumlahSKS();

        int nilaiTugas = nilai.getNilaiTugas() != 0 ? nilai.getNilaiTugas() : tugasInput;
        int nilaiUTS = nilai.getNilaiUTS() != 0 ? nilai.getNilaiUTS() : utsInput;
        int nilaiUAS = nilai.getNilaiUAS() != 0 ? nilai.getNilaiUAS() : uasInput;

        return ""
                + "<h2>Input Nilai Mahasiswa</h2>"
                + "<form action='NilaiController' method='post'>"
                + "<table class='form-table'>"

                + "<tr>"
                + "<td>NIM</td>"
                + "<td>"
                + "<input type='text' name='nim' value='" + esc(nim) + "' class='input-panjang'>"
                + "<button type='submit' name='aksi' value='cariMahasiswa'>Cari</button>"
                + "<button type='submit' name='aksi' value='lihatMahasiswa'>Lihat</button>"
                + "</td>"
                + "</tr>"

                + "<tr>"
                + "<td>Nama</td>"
                + "<td><input type='text' value='" + esc(nama) + "' class='input-panjang' readonly></td>"
                + "</tr>"

                + "<tr>"
                + "<td>Semester</td>"
                + "<td><input type='text' value='" + (semester == 0 ? "" : semester) + "' class='input-kecil' readonly></td>"
                + "</tr>"

                + "<tr>"
                + "<td>Kelas</td>"
                + "<td><input type='text' value='" + esc(kelas) + "' class='input-kecil' readonly></td>"
                + "</tr>"

                + "<tr>"
                + "<td>Kode Mata Kuliah</td>"
                + "<td>"
                + "<input type='text' name='kodeMataKuliah' value='" + esc(kodeMataKuliah) + "' class='input-panjang'>"
                + "<button type='submit' name='aksi' value='cariMataKuliah'>Cari</button>"
                + "<button type='submit' name='aksi' value='cariNilai'>Lihat</button>"
                + "</td>"
                + "</tr>"

                + "<tr>"
                + "<td>Nama Mata Kuliah</td>"
                + "<td><input type='text' value='" + esc(namaMataKuliah) + "' class='input-panjang' readonly></td>"
                + "</tr>"

                + "<tr>"
                + "<td>Jumlah SKS</td>"
                + "<td><input type='text' value='" + (jumlahSKS == 0 ? "" : jumlahSKS) + "' class='input-kecil' readonly></td>"
                + "</tr>"

                + "<tr>"
                + "<td>Nilai Tugas</td>"
                + "<td><input type='text' name='nilaiTugas' value='" + (nilaiTugas == 0 ? "" : nilaiTugas) + "' class='input-sedang'></td>"
                + "</tr>"

                + "<tr>"
                + "<td>Nilai UTS</td>"
                + "<td><input type='text' name='nilaiUTS' value='" + (nilaiUTS == 0 ? "" : nilaiUTS) + "' class='input-sedang'></td>"
                + "</tr>"

                + "<tr>"
                + "<td>Nilai UAS</td>"
                + "<td><input type='text' name='nilaiUAS' value='" + (nilaiUAS == 0 ? "" : nilaiUAS) + "' class='input-sedang'></td>"
                + "</tr>"

                + "<tr>"
                + "<td></td>"
                + "<td>"
                + "<button type='submit' name='aksi' value='simpan' class='btn'>Simpan</button> "
                + "<button type='submit' name='aksi' value='hapus' class='btn'>Hapus</button>"
                + "</td>"
                + "</tr>"

                + "</table>"
                + "</form>";
    }

    private String ambil(String value) {
        return value == null ? "" : value.trim();
    }

    private int angka(String value) {
        try {
            if (value == null || value.trim().equals("")) {
                return 0;
            }

            return Integer.parseInt(value.trim());
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
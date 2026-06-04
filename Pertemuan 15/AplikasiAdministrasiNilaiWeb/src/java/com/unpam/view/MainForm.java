package com.unpam.view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainForm extends HttpServlet {

    public void tampilan(String konten, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        if (konten == null || konten.equals("")) {
            konten = "<h2>Selamat Datang</h2>"
                    + "<p>Silakan pilih menu yang tersedia.</p>";
        }

        String menuKiri = ""
                + "<div class='menu-title'>Master Data</div>"
                + "<a href='MahasiswaController'>Mahasiswa</a>"
                + "<a href='MataKuliahController'>Mata Kuliah</a>"
                + "<br>"
                + "<div class='menu-title'>Transaksi</div>"
                + "<a href='NilaiController'>Nilai</a>"
                + "<br>"
                + "<div class='menu-title'>Laporan</div>"
                + "<a href='NilaiController'>Nilai</a>"
                + "<br>"
                + "<a href='LogoutController'>Logout</a>";

        String topMenu = ""
                + "<div class='top-menu'>"
                + "<a href='MainForm'>Home</a>"
                + "<a href='MahasiswaController'>Master Data</a>"
                + "<a href='NilaiController'>Transaksi</a>"
                + "<a href='NilaiController'>Laporan</a>"
                + "<a href='LogoutController'>Logout</a>"
                + "</div>";

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Informasi Nilai Mahasiswa</title>");
            out.println("<link rel='stylesheet' type='text/css' href='style.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");

            out.println("<div class='header'>");
            out.println("<h3>Informasi Nilai Mahasiswa</h3>");
            out.println("<h1>UNIVERSITAS PAMULANG</h1>");
            out.println("<p>Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten</p>");
            out.println("</div>");

            out.println("<div class='content-wrapper'>");

            out.println("<div class='sidebar'>");
            out.println(menuKiri);
            out.println("</div>");

            out.println("<div class='main'>");
            out.println(topMenu);
            out.println(konten);
            out.println("</div>");

            out.println("</div>");

            out.println("<div class='footer'>");
            out.println("Copyright &copy; 2014 Universitas Pamulang<br>");
            out.println("Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten");
            out.println("</div>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        tampilan("", request, response);
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

    @Override
    public String getServletInfo() {
        return "MainForm";
    }
}
package com.unpam.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "NilaiController", urlPatterns = {"/NilaiController"})
public class NilaiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            String nim = request.getParameter("nim");
            String nama = request.getParameter("nama");
            String matkul = request.getParameter("matkul");

            double tugas = Double.parseDouble(request.getParameter("tugas"));
            double uts = Double.parseDouble(request.getParameter("uts"));
            double uas = Double.parseDouble(request.getParameter("uas"));

            double nilaiAkhir = (tugas * 0.3) + (uts * 0.3) + (uas * 0.4);

            String grade;

            if (nilaiAkhir >= 85) {
                grade = "A";
            } else if (nilaiAkhir >= 75) {
                grade = "B";
            } else if (nilaiAkhir >= 65) {
                grade = "C";
            } else if (nilaiAkhir >= 50) {
                grade = "D";
            } else {
                grade = "E";
            }

            out.println("<html>");
            out.println("<head><title>Hasil Nilai</title></head>");
            out.println("<body>");
            out.println("<h2>Hasil Perhitungan Nilai</h2>");
            out.println("NIM : " + nim + "<br>");
            out.println("Nama : " + nama + "<br>");
            out.println("Mata Kuliah : " + matkul + "<br>");
            out.println("Nilai Akhir : " + nilaiAkhir + "<br>");
            out.println("Grade : " + grade + "<br>");
            out.println("</body>");
            out.println("</html>");
        }
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
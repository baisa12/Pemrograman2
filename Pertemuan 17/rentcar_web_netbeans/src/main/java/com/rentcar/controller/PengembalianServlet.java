package com.rentcar.controller;

import com.rentcar.dao.TransaksiDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "PengembalianServlet", urlPatterns = "/pengembalian")
public class PengembalianServlet extends HttpServlet {
    private final TransaksiDAO transaksiDAO = new TransaksiDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("listTransaksiAktif", transaksiDAO.getTransaksiAktif());
        } catch (Exception e) {
            request.setAttribute("error", "Gagal memuat transaksi aktif: " + e.getMessage());
        }
        request.getRequestDispatcher("pengembalian.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idTransaksi = Integer.parseInt(request.getParameter("idTransaksi"));
            Date tanggalKembali = Date.valueOf(request.getParameter("tanggalKembali"));
            transaksiDAO.prosesPengembalian(idTransaksi, tanggalKembali);
            response.sendRedirect("pengembalian?success=Pengembalian mobil berhasil diproses");
        } catch (Exception e) {
            request.setAttribute("error", "Gagal memproses pengembalian: " + e.getMessage());
            doGet(request, response);
        }
    }
}

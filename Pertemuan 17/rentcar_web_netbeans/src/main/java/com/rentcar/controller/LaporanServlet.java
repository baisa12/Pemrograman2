package com.rentcar.controller;

import com.rentcar.dao.TransaksiDAO;
import com.rentcar.model.Transaksi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LaporanServlet", urlPatterns = "/laporan")
public class LaporanServlet extends HttpServlet {
    private final TransaksiDAO transaksiDAO = new TransaksiDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String tanggalAwal = request.getParameter("tanggalAwal");
            String tanggalAkhir = request.getParameter("tanggalAkhir");
            String status = request.getParameter("status");

            List<Transaksi> laporan = transaksiDAO.getLaporan(tanggalAwal, tanggalAkhir, status);
            double totalPendapatan = 0;
            double totalDenda = 0;
            for (Transaksi transaksi : laporan) {
                totalPendapatan += transaksi.getTotalBiaya() + transaksi.getDenda();
                totalDenda += transaksi.getDenda();
            }

            request.setAttribute("listLaporan", laporan);
            request.setAttribute("totalPendapatan", totalPendapatan);
            request.setAttribute("totalDenda", totalDenda);
            request.setAttribute("tanggalAwal", tanggalAwal == null ? "" : tanggalAwal);
            request.setAttribute("tanggalAkhir", tanggalAkhir == null ? "" : tanggalAkhir);
            request.setAttribute("status", status == null ? "SEMUA" : status);
        } catch (Exception e) {
            request.setAttribute("error", "Gagal memuat laporan: " + e.getMessage());
        }
        request.getRequestDispatcher("laporan.jsp").forward(request, response);
    }
}

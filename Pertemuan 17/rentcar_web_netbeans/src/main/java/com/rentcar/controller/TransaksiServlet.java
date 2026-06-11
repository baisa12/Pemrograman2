package com.rentcar.controller;

import com.rentcar.dao.CustomerDAO;
import com.rentcar.dao.MobilDAO;
import com.rentcar.dao.TransaksiDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "TransaksiServlet", urlPatterns = "/transaksi")
public class TransaksiServlet extends HttpServlet {
    private final MobilDAO mobilDAO = new MobilDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final TransaksiDAO transaksiDAO = new TransaksiDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("delete".equals(action)) {
                int idTransaksi = Integer.parseInt(request.getParameter("id"));
                transaksiDAO.deleteTransaksi(idTransaksi);
                response.sendRedirect("transaksi?success=Transaksi berhasil dihapus");
                return;
            }
            request.setAttribute("listMobil", mobilDAO.getMobilTersedia());
            request.setAttribute("listCustomer", customerDAO.getAllCustomer());
            request.setAttribute("listTransaksi", transaksiDAO.getAllTransaksi());
        } catch (Exception e) {
            request.setAttribute("error", "Gagal memuat data transaksi: " + e.getMessage());
        }
        request.getRequestDispatcher("transaksi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idMobil = Integer.parseInt(request.getParameter("idMobil"));
            int idCustomer = Integer.parseInt(request.getParameter("idCustomer"));
            Date tanggalSewa = Date.valueOf(request.getParameter("tanggalSewa"));
            Date tanggalRencanaKembali = Date.valueOf(request.getParameter("tanggalRencanaKembali"));

            transaksiDAO.insertTransaksi(idMobil, idCustomer, tanggalSewa, tanggalRencanaKembali);
            response.sendRedirect("transaksi?success=Transaksi penyewaan berhasil disimpan");
        } catch (Exception e) {
            request.setAttribute("error", "Gagal menyimpan transaksi: " + e.getMessage());
            doGet(request, response);
        }
    }
}

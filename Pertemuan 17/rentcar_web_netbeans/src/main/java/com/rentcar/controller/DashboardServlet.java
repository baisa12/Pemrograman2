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

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard", ""})
public class DashboardServlet extends HttpServlet {
    private final MobilDAO mobilDAO = new MobilDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final TransaksiDAO transaksiDAO = new TransaksiDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("jumlahMobil", mobilDAO.getAllMobil().size());
            request.setAttribute("jumlahCustomer", customerDAO.getAllCustomer().size());
            request.setAttribute("jumlahTransaksi", transaksiDAO.getAllTransaksi().size());
            request.setAttribute("jumlahTransaksiAktif", transaksiDAO.getTransaksiAktif().size());
        } catch (Exception e) {
            request.setAttribute("error", "Gagal memuat dashboard: " + e.getMessage());
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

package com.rentcar.controller;

import com.rentcar.dao.MobilDAO;
import com.rentcar.model.Mobil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MobilServlet", urlPatterns = "/mobil")
public class MobilServlet extends HttpServlet {
    private final MobilDAO mobilDAO = new MobilDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("edit".equals(action)) {
                int idMobil = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("mobilEdit", mobilDAO.getMobilById(idMobil));
            } else if ("delete".equals(action)) {
                int idMobil = Integer.parseInt(request.getParameter("id"));
                mobilDAO.deleteMobil(idMobil);
                response.sendRedirect("mobil?success=Data mobil berhasil dihapus");
                return;
            }
            request.setAttribute("listMobil", mobilDAO.getAllMobil());
        } catch (Exception e) {
            request.setAttribute("error", "Gagal memuat data mobil: " + e.getMessage());
        }
        request.getRequestDispatcher("mobil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idMobilText = request.getParameter("idMobil");
        try {
            Mobil mobil = new Mobil();
            mobil.setPlatNomor(request.getParameter("platNomor").toUpperCase());
            mobil.setMerk(request.getParameter("merk"));
            mobil.setModel(request.getParameter("model"));
            mobil.setTahun(Integer.parseInt(request.getParameter("tahun")));
            mobil.setHargaSewa(Double.parseDouble(request.getParameter("hargaSewa")));
            mobil.setStatus(request.getParameter("status"));

            if (idMobilText == null || idMobilText.isEmpty()) {
                mobilDAO.insertMobil(mobil);
                response.sendRedirect("mobil?success=Data mobil berhasil disimpan");
            } else {
                mobil.setIdMobil(Integer.parseInt(idMobilText));
                mobilDAO.updateMobil(mobil);
                response.sendRedirect("mobil?success=Data mobil berhasil diperbarui");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Gagal menyimpan data mobil: " + e.getMessage());
            doGet(request, response);
        }
    }
}

package com.unpam.controller;

import koneksi.KoneksiDB;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@WebServlet(name = "LaporanNilaiController",
        urlPatterns = {"/LaporanNilaiController"})
public class LaporanNilaiController extends HttpServlet {

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Connection conn = null;
        OutputStream out = null;

        try {

            KoneksiDB db = new KoneksiDB();
            conn = db.getKoneksi();

            String reportPath =
                    getServletContext().getRealPath(
                            "/reports/NilaiReport.jrxml");

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(
                            reportPath);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport,
                            new HashMap<>(),
                            conn);

            response.setContentType("application/pdf");

            out = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(
                    jasperPrint,
                    out);

        } catch (IOException | JRException e) {

            response.getWriter().println(
                    "Error : " + e.getMessage());

        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Laporan Nilai JasperReport";
    }
}
package com.unpam.controller;

import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController extends HttpServlet {

    private final MainForm mainForm = new MainForm();

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String tombol = request.getParameter("tombol");

        if ("Login".equals(tombol)) {
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");

            if ("admin".equals(userName) && "admin".equals(password)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("userName", userName);

                response.sendRedirect("MainForm");
            } else {
                tampilkanLogin(request, response, "Username atau password salah");
            }
        } else {
            tampilkanLogin(request, response, "");
        }
    }

    private void tampilkanLogin(HttpServletRequest request,
            HttpServletResponse response, String pesan) throws ServletException, IOException {

        String konten = "";

        if (!pesan.equals("")) {
            konten += "<p style='color:red;'>" + pesan + "</p>";
        }

        konten += "<h2>Login</h2>"
                + "<form action='LoginController' method='post'>"
                + "<table class='form-table'>"
                + "<tr><td>Username</td><td><input type='text' name='userName'></td></tr>"
                + "<tr><td>Password</td><td><input type='password' name='password'></td></tr>"
                + "<tr><td></td><td><input type='submit' name='tombol' value='Login'></td></tr>"
                + "</table>"
                + "</form>"
                + "<p>Username: admin<br>Password: admin</p>";

        mainForm.tampilan(konten, request, response);
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
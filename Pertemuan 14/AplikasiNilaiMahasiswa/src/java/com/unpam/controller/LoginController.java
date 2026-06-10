package com.unpam.controller;

import com.unpam.model.Enkripsi;
import com.unpam.model.Koneksi;
import com.unpam.view.MainForm;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private final Koneksi koneksi = new Koneksi();
    private final Enkripsi enkripsi = new Enkripsi();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tombol = request.getParameter("btn");
        String konten;

        if ("Login".equals(tombol)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            String namaUser = cekLogin(username, password);

            if (!namaUser.equals("")) {
                HttpSession session = request.getSession(true);
                session.setAttribute("userName", namaUser);

                konten = "<br><h2>Login Berhasil</h2>"
                        + "<p>Selamat datang, <b>" + escape(namaUser) + "</b>.</p>";
            } else {
                konten = formLogin("<div class='pesan-error'>Username atau password salah.</div>");
            }
        } else {
            konten = formLogin("");
        }

        MainForm mainForm = new MainForm();
        mainForm.tampilkan(request, response, konten);
    }

    private String cekLogin(String username, String password) {
        String nama = "";
        Connection connection = koneksi.getConnection();

        if (connection == null) {
            return "";
        }

        String sql = "SELECT nama FROM tbuser WHERE username = ? AND password = ?";

        try {
            String passwordHash = enkripsi.hashMD5(password);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, passwordHash);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        nama = resultSet.getString("nama");
                    }
                }
            }
        } catch (Exception ex) {
            nama = "";
        } finally {
            try {
                connection.close();
            } catch (Exception ignored) {
            }
        }

        return nama;
    }

    private String formLogin(String pesan) {
        return "<br><h2>Form Login</h2>"
                + pesan
                + "<form method='post' action='LoginController'>"
                + "<table>"
                + "<tr>"
                + "<td>Username</td>"
                + "<td><input type='text' name='username' required></td>"
                + "</tr>"
                + "<tr>"
                + "<td>Password</td>"
                + "<td><input type='password' name='password' required></td>"
                + "</tr>"
                + "<tr>"
                + "<td></td>"
                + "<td><input type='submit' name='btn' value='Login'></td>"
                + "</tr>"
                + "</table>"
                + "</form>";
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#039;");
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
}
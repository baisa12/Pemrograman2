package com.rentcar.controller;

import com.rentcar.dao.CustomerDAO;
import com.rentcar.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("edit".equals(action)) {
                int idCustomer = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("customerEdit", customerDAO.getCustomerById(idCustomer));
            } else if ("delete".equals(action)) {
                int idCustomer = Integer.parseInt(request.getParameter("id"));
                customerDAO.deleteCustomer(idCustomer);
                response.sendRedirect("customer?success=Data customer berhasil dihapus");
                return;
            }
            request.setAttribute("listCustomer", customerDAO.getAllCustomer());
        } catch (Exception e) {
            request.setAttribute("error", "Gagal memuat data customer: " + e.getMessage());
        }
        request.getRequestDispatcher("customer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idCustomerText = request.getParameter("idCustomer");
        try {
            Customer customer = new Customer();
            customer.setNama(request.getParameter("nama"));
            customer.setNoKtp(request.getParameter("noKtp"));
            customer.setNoHp(request.getParameter("noHp"));
            customer.setAlamat(request.getParameter("alamat"));

            if (idCustomerText == null || idCustomerText.isEmpty()) {
                customerDAO.insertCustomer(customer);
                response.sendRedirect("customer?success=Data customer berhasil disimpan");
            } else {
                customer.setIdCustomer(Integer.parseInt(idCustomerText));
                customerDAO.updateCustomer(customer);
                response.sendRedirect("customer?success=Data customer berhasil diperbarui");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Gagal menyimpan data customer: " + e.getMessage());
            doGet(request, response);
        }
    }
}

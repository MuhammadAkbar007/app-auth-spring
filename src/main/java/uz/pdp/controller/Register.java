package uz.pdp.controller;

import uz.pdp.model.Result;
import uz.pdp.model.User;
import uz.pdp.service.DbService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("register.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phoneNumber");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String prePassword = req.getParameter("prePassword");

        PrintWriter writer = resp.getWriter();

        if (prePassword.equals(password)) {
            DbService service = new DbService();
            User user = new User(firstName, lastName, phoneNumber, username, password);
            Result result = service.registerUser(user);
            if (result.isSuccess()) {
                writer.write("<h1 style='color: green'>" + result.getMessage() + "</h1>");
            } else {
                writer.write("<h1 style='color: red'>" + result.getMessage() + "</h1>");
            }

        } else {
            writer.write("<h1 style='color: yellow'>Passwords are not equal</h1>");
        }

    }

}

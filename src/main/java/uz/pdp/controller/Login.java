package uz.pdp.controller;

import uz.pdp.model.User;
import uz.pdp.service.DbService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        DbService dbService = new DbService();
        User user = dbService.login(username, password);
        PrintWriter writer = resp.getWriter();
        if (user == null) {
            writer.write("<h1 style = 'color: red'>Password or login error !</h1>");
        } else {
            Cookie cookie = new Cookie("authApp", user.getUsername());
            cookie.setMaxAge(60 * 60);       // 1 min * 60 = 1 hour
            resp.addCookie(cookie);

            resp.sendRedirect("/cabinet");
        }

    }

}

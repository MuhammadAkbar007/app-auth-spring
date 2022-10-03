package uz.pdp.controller;

import uz.pdp.model.User;
import uz.pdp.service.DbService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cabinet")
public class Cabinet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Cookie[] cookies = req.getCookies();
        String username = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authApp")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        DbService dbService = new DbService();
        User user = dbService.loadUserByCookie(username);

        if (user == null) {
            Cookie cookie = new Cookie("authApp", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        }

        if (user != null) {
            writer.write("<h1 style = 'color: green'>Welcome " + user.getFirstName() + " " + user.getLastName() + "</h1>");
            writer.write("<h1 style = 'color: indigo'>Your phone number: " + user.getPhoneNumber() + "</h1>");
            writer.write("<button><a href='/logout'>Log Out</a></button>");
        }

    }

}

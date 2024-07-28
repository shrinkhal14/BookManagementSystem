package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
    private static final String query = "UPDATE BOOKDATA SET BOOKNAME=?, BOOKEDITION=?, BOOKAUTHOR=?, BOOKPRICE=? WHERE id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");

        // Get the ID of the record
        int id = Integer.parseInt(req.getParameter("id"));
        // Get the data we want to edit
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        String bookAuthor = req.getParameter("bookAuthor");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Generate the connection and update the record
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "Krishna@14");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setString(3, bookAuthor);
            ps.setFloat(4, bookPrice);
            ps.setInt(5, id);
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2 style='color: green;'>Record is Edited Successfully</h2>");
            } else {
                pw.println("<h2 style='color: red;'>Record is not Edited Successfully</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }

        pw.println("<a href='home.html'>Home</a>");
        pw.println("<br>");
        pw.println("<a href='bookList'>Book List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}

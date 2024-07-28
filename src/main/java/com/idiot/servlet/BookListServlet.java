package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
    private static final String query = "SELECT ID, BOOKNAME, BOOKEDITION, BOOKAUTHOR, BOOKPRICE FROM BOOKDATA";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");

        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "Krishna@14");
             PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            // Add HTML and CSS styles
            pw.println("<html><head><title>Book List</title>");
            pw.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
            pw.println("<style>");
            pw.println("body { background-color: #f8e8e8; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            pw.println(".container { width: 80%; margin: auto; }");
            pw.println("table { background-color: #ffe4e1; border-collapse: collapse; width: 100%; margin-top: 20px; }");
            pw.println("th, td { border: 1px solid #ffccd5; padding: 10px; text-align: center; }");
            pw.println("th { background-color: #ffb6c1; color: white; }");
            pw.println("tr:nth-child(even) { background-color: #ffe4e1; }");
            pw.println("tr:nth-child(odd) { background-color: #ffebee; }");
            pw.println("h1 { background-color: #ffb6c1; color: white; padding: 10px; border-radius: 10px 10px 0 0; }");
            pw.println("a { color: #ff6f91; text-decoration: none; }");
            pw.println("a:hover { text-decoration: underline; }");
            pw.println("</style></head><body>");

            pw.println("<div class='container'>");
            pw.println("<h1>Book List</h1>");
            pw.println("<table class='table table-bordered table-striped'>");
            pw.println("<tr>");
            pw.println("<th>Book Id</th>");
            pw.println("<th>Book Name</th>");
            pw.println("<th>Book Edition</th>");
            pw.println("<th>Book Author</th>");
            pw.println("<th>Book Price</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt("ID") + "</td>");
                pw.println("<td>" + rs.getString("BOOKNAME") + "</td>");
                pw.println("<td>" + rs.getString("BOOKEDITION") + "</td>");
                pw.println("<td>" + rs.getString("BOOKAUTHOR") + "</td>");
                pw.println("<td>" + rs.getFloat("BOOKPRICE") + "</td>");
                pw.println("<td><a href='editScreen?id=" + rs.getInt("ID") + "'>Edit</a></td>");
                pw.println("<td><a href='deleteurl?id=" + rs.getInt("ID") + "'>Delete</a></td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("<a href='home.html'>Home</a>");
            pw.println("</div>");
            pw.println("</body></html>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}

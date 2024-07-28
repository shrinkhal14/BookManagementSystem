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

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    private static final String query = "SELECT BOOKNAME, BOOKEDITION, BOOKAUTHOR, BOOKPRICE FROM BOOKDATA WHERE id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");
        // Get the id of the record
        int id = Integer.parseInt(req.getParameter("id"));

        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "Krishna@14");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            // HTML and CSS for the form
            pw.println("<html><head><title>Edit Book</title>");
            pw.println("<style>");
            pw.println("body { display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f8e8e8; margin: 0; }");
            pw.println(".container { width: 40rem; padding: 2rem; border-radius: 10px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.1); background-color: #ffe4e1; }");
            pw.println("h2 { background-color: #ffb6c1; color: white; padding: 1rem; border-radius: 10px 10px 0 0; }");
            pw.println("table { width: 100%; margin-top: 20px; }");
            pw.println("td { padding: 10px; }");
            pw.println("input[type='text'], input[type='submit'], input[type='reset'] { width: 100%; padding: 0.5rem; border-radius: 5px; border: 1px solid #ffccd5; }");
            pw.println("input[type='submit'], input[type='reset'] { background-color: #ffb6c1; border: none; color: white; cursor: pointer; }");
            pw.println("input[type='submit']:hover, input[type='reset']:hover { background-color: #ff9eb3; }");
            pw.println("</style></head><body>");

            pw.println("<div class='container'>");
            pw.println("<h2>Edit Book</h2>");
            pw.println("<form action='editurl?id=" + id + "' method='post'>");
            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<td>Book Name</td>");
            pw.println("<td><input type='text' name='bookName' value='" + rs.getString("BOOKNAME") + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Book Edition</td>");
            pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString("BOOKEDITION") + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Book Author</td>");
            pw.println("<td><input type='text' name='bookAuthor' value='" + rs.getString("BOOKAUTHOR") + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Book Price</td>");
            pw.println("<td><input type='text' name='bookPrice' value='" + rs.getFloat("BOOKPRICE") + "'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td><input type='submit' value='Edit'></td>");
            pw.println("<td><input type='reset' value='Cancel'></td>");
            pw.println("</tr>");
            pw.println("</table>");
            pw.println("</form>");
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

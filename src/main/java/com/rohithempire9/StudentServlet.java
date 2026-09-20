package com.rohithempire9;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/rohith1";
    private static final String USER = "root";
    private static final String PASSWORD = "#yourPassrowd";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String course = request.getParameter("course");
        int marks = Integer.parseInt(request.getParameter("marks"));

        String sql =
                "INSERT INTO student (id, name, course, marks) VALUES (?, ?, ?, ?)";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.setInt(4, marks);

            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<h2>Student inserted successfully!</h2>");
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            out.println("<h2>Error occurred</h2>");
            out.println("<p>" + e.getMessage() + "</p>");

            e.printStackTrace();
        }
    }
}

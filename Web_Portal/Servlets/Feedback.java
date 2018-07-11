/*
Feedback ke lie hai ye.
Shrini
 */

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login_register;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author Dinesh
 */
public class Feedback extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     *
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String u_name = request.getParameter("name");
        String email = request.getParameter("email");
        String feed = request.getParameter("feed");
        String club = request.getParameter("club");
        int club_no = Integer.parseInt(club);
        int fcount = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

            ResultSet rs;
            String sql1 = "select F_Count from counter;";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            rs = ps1.executeQuery();
            rs.next();
            fcount = rs.getInt(1);
            fcount++;

            PrintWriter out = response.getWriter();

            String sql = "INSERT INTO feedback VALUES(?,?,?,?,?);";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, fcount);
            ps.setString(2, feed);
            ps.setString(3, u_name);
            ps.setString(4, email);
            ps.setInt(5, club_no);

            ps.executeUpdate();

            String sql2 = "UPDATE counter set F_Count = ?;";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, fcount);
            ps2.executeUpdate();

            out.println("Feedback Registered!");
            con.close();
            response.setHeader("Refresh", "0;url=index.jsp");
        } catch (SQLException | ClassNotFoundException e) {
        }

    }

}

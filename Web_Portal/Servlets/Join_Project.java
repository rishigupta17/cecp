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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dinesh
 */
public class Join_Project extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("user_name") != null) {

            PrintWriter out = response.getWriter();

            String str = session.getAttribute("user_id").toString();
            int user_id = Integer.parseInt(str);

            String p = request.getParameter("proj");
            int p_id = Integer.parseInt(p);

            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

                ResultSet rs;
                String sql = "select * from project where P_Id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, p_id);
                rs = ps.executeQuery();
                //out.print(ps);
                if (rs.next()) {
                    int vac = rs.getInt(4);

                    int v = 6 - vac;
                    vac = vac - 1;
                    out.print("  v=" + v + "   vac = " + vac);
                    String sql1 = "update project set Uid" + Integer.toString(v) + "= ?,Vacancy =" + Integer.toString(vac) + " where P_Id=?;";
                    PreparedStatement ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, user_id);
                    ps1.setInt(2, p_id);

                    ps1.executeUpdate();
                    out.print("Project Joined!");
                }
                con.close();
                response.setHeader("Refresh", "0;url=Project.jsp");
            } catch (SQLException | ClassNotFoundException | NullPointerException | NumberFormatException e) {
            }
        }
    }

}

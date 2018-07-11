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
public class Club_Reg extends HttpServlet {

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

        HttpSession session = request.getSession();

        if (session.getAttribute("user_name") != null) {

            String skills = request.getParameter("add_skills");

            String str = session.getAttribute("user_id").toString();
            int user_id = Integer.parseInt(str);
            String str1 = session.getAttribute("c_no").toString();
            int c_no = Integer.parseInt(str1);

            PrintWriter out = response.getWriter();
            out.print(c_no);

            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

                //ResultSet rs;
                String sql = " ";
                PreparedStatement ps;

                if (c_no == 1) {
                    sql = "update users set C1 = ? where Uid = ?;";
                    session.setAttribute("c1", 1);
                } else if (c_no == 2) {
                    sql = "update users set C2 = ? where Uid = ?;";
                    session.setAttribute("c2", 1);
                } else if (c_no == 3) {
                    sql = "update users set C3 = ? where Uid = ?;";
                    session.setAttribute("c3", 1);
                }

                ps = con.prepareStatement(sql);
                ps.setInt(1, 1);
                ps.setInt(2, user_id);

                out.print(ps);
                ps.executeUpdate();

                if (c_no == 1) {
                    sql = "update profile set Skill_C1 = ? where Uid = ?;";
                } else if (c_no == 2) {
                    sql = "update profile set Skill_C2 = ? where Uid = ?;";
                } else if (c_no == 3) {
                    sql = "update profile set Skill_C3 = ? where Uid = ?;";
                }

                ps = con.prepareStatement(sql);
                ps.setString(1, skills);
                ps.setInt(2, user_id);

                out.print(ps);
                ps.executeUpdate();

                con.close();
                out.print("Registered in Club!");
                response.setHeader("Refresh", "0;url=index.jsp");

            } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            }
        }
    }

}

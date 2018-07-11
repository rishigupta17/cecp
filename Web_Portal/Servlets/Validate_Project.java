/*

The projects are to be validated by the mentor

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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dinesh
 */
public class Validate_Project extends HttpServlet {

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

            PrintWriter out = response.getWriter();

            String p = request.getParameter("pro");
            int p_id = Integer.parseInt(p);

            String str = session.getAttribute("user_id").toString();
            int user_id = Integer.parseInt(str);
            String str1 = session.getAttribute("c_no").toString();
            int c_no = Integer.parseInt(str1);

            int temp;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

                ResultSet rs4;
                String sql4 = "select * from project where P_Id = ?;";
                PreparedStatement ps4 = con.prepareStatement(sql4);
                ps4.setInt(1, p_id);
                rs4 = ps4.executeQuery();

                int cntr = 7;
                if (rs4.next()) {
                    while (rs4.getInt(cntr) != 0) {
                        ResultSet rs2;
                        String sql2 = "select * from rating where Uid = ?";
                        PreparedStatement ps2 = con.prepareStatement(sql2);
                        ps2.setInt(1, rs4.getInt(cntr));
                        rs2 = ps2.executeQuery();

                        if (rs2.next()) {
                            temp = rs2.getInt(1 + c_no);
                            temp += 1;
                            String sql1 = "update rating set P_Count" + str1 + "=?" + " where Uid=?;";
                            PreparedStatement ps1 = con.prepareStatement(sql1);
                            ps1.setInt(1, temp);
                            ps1.setInt(2, rs4.getInt(cntr));
                            //out.print(ps1);
                            ps1.executeUpdate();
                            //out.print("hello 1");
                        }
                        cntr++;

                    }

                    /*cntr = 7;
                    while (rs4.getInt(cntr) != 0) {
                        ResultSet rs3;
                        String sql3 = "select Pid from profile where Uid=?;";
                        PreparedStatement ps3 = con.prepareStatement(sql3);
                        ps3.setInt(1, rs4.getInt(cntr));
                        rs3 = ps3.executeQuery();

                        String s;
                        if (rs3.next()) {
                            s = rs3.getString(1);
                            s = s + "," + p;
                            String sql1 = "update profile set Pid = ? where Uid=?;";
                            PreparedStatement ps1 = con.prepareStatement(sql1);
                            ps1.setString(1, s);
                            ps1.setInt(2, rs4.getInt(cntr));

                            ps1.executeUpdate();
                        }
                        cntr++;
                    }*/
                }

                String sql = "update project set Status = 1,Vacancy = 0 where P_Id=?;";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, p_id);

                ps.executeUpdate();

                out.print("Project Validated!");

                con.close();
                response.setHeader("Refresh", "0;url=Mentor.jsp");
            } catch (SQLException | ClassNotFoundException | NullPointerException | NumberFormatException e) {
            }
        }
    }

}

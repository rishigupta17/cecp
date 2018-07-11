/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Iss ke later half ko test karke dekhna hai*/
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
public class Proj_Reg extends HttpServlet {

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

        String p_name = request.getParameter("P_name");
        String mentor = request.getParameter("ment");
        int mentor_id = Integer.parseInt(mentor);
        String vacancy = request.getParameter("Vacancy");

        HttpSession session = request.getSession();
        if (session.getAttribute("user_name") != null) {

            String str = session.getAttribute("user_id").toString();
            int user_id = Integer.parseInt(str);
            String str1 = session.getAttribute("c_no").toString();
            int c_no = Integer.parseInt(str1);

            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

                ResultSet rs;

                PrintWriter out = response.getWriter();

                ResultSet rs2;
                String sql1 = "select Pid_Count from counter;";
                PreparedStatement ps1 = con.prepareStatement(sql1);
                rs2 = ps1.executeQuery();
                //out.print("hello");
                int pno = 0;
                if (rs2.next()) {
                    pno = rs2.getInt(1);
                    pno += 1;
                }

                String sql = "INSERT INTO project VALUES(?,?,?,4,0,?,?,?,?,?,?);";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, c_no);
                ps.setInt(2, pno);
                ps.setString(3, p_name);
                ps.setInt(4, mentor_id);
                ps.setInt(5, user_id);
                ps.setInt(6, 0);
                ps.setInt(7, 0);
                ps.setInt(8, 0);
                ps.setInt(9, 0);
                ps.executeUpdate();

                sql = "UPDATE counter set Pid_Count = ?;";
                ps = con.prepareStatement(sql);
                ps.setInt(1, pno);
                ps.executeUpdate();
                
                 //---------------------------------------------------------------------
                int fcount=0,temp=0;
                ResultSet rs4;
                    String sql4 = "select P1_Count,P2_Count,P3_Count from counter;";
                    PreparedStatement ps4 = con.prepareStatement(sql4);
                    rs4 = ps4.executeQuery();
                    rs4.next();
                    fcount = rs4.getInt(c_no);
                    fcount++;

                    temp = (fcount % 5);
                    if (temp == 0) {
                        temp = 5;
                    }
                    //out.print("hello 1");

                    String sql5 = "update pannouncement set P" + c_no + " = ? where Sno=?;";
                    PreparedStatement ps5 = con.prepareStatement(sql5);
                    p_name += " Started!"; 
                    ps5.setString(1, p_name);
                    ps5.setInt(2, temp);

                    out.print(ps5);

                    ps5.executeUpdate();

                    //out.print("hello 2");
                    String sql2 = "UPDATE counter set P" + c_no + "_Count = ?;";
                    PreparedStatement ps2 = con.prepareStatement(sql2);
                    ps2.setInt(1, fcount);
                    ps2.executeUpdate();
                
                //---------------------------------------------------------------------
                

                out.println(p_name + " Project Registered!");
                con.close();
                response.setHeader("Refresh", "0;url=Project.jsp");
            } catch (SQLException | ClassNotFoundException e) {
            }
        }
    }

}

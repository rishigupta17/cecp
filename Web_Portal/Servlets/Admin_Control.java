/*
Admin ka pura control design karna hai.
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
public class Admin_Control extends HttpServlet {

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
            if (session.getAttribute("user_name").toString().equals("admin")) {
                PrintWriter out = response.getWriter();
                String u_name = request.getParameter("name_get");
                String pass_set = request.getParameter("pass_set");
                String c1_s = request.getParameter("c1_set");
                String c2_s = request.getParameter("c2_set");
                String c3_s = request.getParameter("c3_set");

                //out.print("hello 1");
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");
                    String sql1;
                    PreparedStatement ps1;

                    //out.print("hello 2");
                    if (!(pass_set.equals("NA"))) {
                        sql1 = "update users set Pass = ? where Uname=?;";
                        ps1 = con.prepareStatement(sql1);
                        ps1.setString(1, pass_set);
                        ps1.setString(2, u_name);
                        //out.print(ps1);
                        ps1.executeUpdate();
                        //out.print(ps1);
                    }

                    if (!(c1_s.equals("NA"))) {
                        int c1_set = Integer.parseInt(c1_s);
                        sql1 = "update users set C1 = " + c1_set + " where Uname=?;";
                        ps1 = con.prepareStatement(sql1);
                        ps1.setString(1, u_name);
                        out.print(ps1);
                        ps1.executeUpdate();
                        out.print(ps1);
                    }

                    if (!(c2_s.equals("NA"))) {
                        int c2_set = Integer.parseInt(c2_s);
                        sql1 = "update users set C2 =" + c2_set + " where Uname=?;";
                        ps1 = con.prepareStatement(sql1);
                        ps1.setString(1, u_name);
                        ps1.executeUpdate();
                    }

                    if (!(c3_s.equals("NA"))) {
                        int c3_set = Integer.parseInt(c3_s);
                        sql1 = "update users set C3 =" + c3_set + " where Uname=?;";
                        ps1 = con.prepareStatement(sql1);
                        ps1.setString(1, u_name);
                        ps1.executeUpdate();
                    }

                    out.println("Succesfully Updated!");
                    con.close();
                    response.setHeader("Refresh", "0;url=Admin_Control.jsp");
                } catch (SQLException | ClassNotFoundException e) {
                }
            }
        }
    }
}

/*
/*Shrini
1. Isme Dono announcement(normal and project wale) mix karna hai jab login ho
2. nd only normal dikhana hai jab login nhi ho.
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
import java.lang.*;

import javax.servlet.*;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dinesh
 */
public class Club_Announce extends HttpServlet {

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
        ServletContext sc = getServletContext();
        String str1 = (String) sc.getAttribute("cn");
        int cn = Integer.parseInt(str1);

        PrintWriter out = response.getWriter();
        //out.print("Hello : "+str1);
        //sc.getAttribute("cn");

        String myana = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

            ResultSet rs;
            ResultSet rs1;

            String sql = "SELECT * FROM announcement;";
            PreparedStatement ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            myana = myana + "<div class='marq'><ul>";
            int j = 0;
            while (rs.next() && j < 5) {

                myana += "<li>" + rs.getString(1 + cn) + "</li>";
                j++;
            }

            if (session.getAttribute("user_name") != null) {
                String tmp = "c"+cn;
                if(session.getAttribute(tmp).toString().equals("1")) {

                String sql1 = "SELECT * FROM pannouncement;";
                PreparedStatement ps1 = con.prepareStatement(sql1);

                rs1 = ps1.executeQuery();
                int i = 0;
                while (rs1.next() && i < 5) {

                    myana += "<li>" + rs1.getString(1 + cn) + "</li>";
                    i++;
                }
                }
            }
            myana += "</ul></div>";
            sc.setAttribute("club_ana", myana);

            con.close();

        } catch (SQLException | ClassNotFoundException e) {
        }

    }

}

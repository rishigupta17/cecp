/*
Ye bhi banana hai.
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
public class Leaderboard extends HttpServlet {

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

        ServletContext sc = getServletContext();
        String str1 = (String) sc.getAttribute("cn");
        int cn = Integer.parseInt(str1);
        PrintWriter out = response.getWriter();
        String myana = "<div class='rankings'><ul class='links'>";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

            int rate = 0;
            Integer[] rank = new Integer[10];
            Integer[] user = new Integer[10];
            String[] ranking = new String[10];
            

            ResultSet rs3, rs4;

            

            String sql3 = "SELECT * FROM rating where P_Count"+cn+" > 0 order by P_Count"+cn+" desc;";
            PreparedStatement ps3 = con.prepareStatement(sql3);
            rs3 = ps3.executeQuery();
            int j = 0;
            while (rs3.next() && j < 10) {
                rank[j] = rs3.getInt(1+cn);
                user[j] = rs3.getInt(1);
                j++;
            }
            //out.print("Rank Sample: " + rank[0] + "Name : " + user[0]);

            int i = 0;

            while (i < j) {
                String sql4 = "SELECT Uname FROM users where Uid=? AND C"+cn+" = 1;";
                PreparedStatement ps4 = con.prepareStatement(sql4);
                ps4.setInt(1, user[i]);
                rs4 = ps4.executeQuery();

                if (rs4.next()) {
                    ranking[i] = rs4.getString(1);
                }
                i++;
                //out.print("User Name: "+rnk[0]);
            }

            for (i = 0; i < j; i++) {
                myana += "<li> Uname: " + ranking[i] + " Rank: " + (1+i) + "</li>";
            }
            myana += "</ul></div>";
            
            sc.setAttribute("leader", myana);

            con.close();

        } catch (SQLException | ClassNotFoundException e) {
        }
    }

}

/*
Ye bhi banana hai.
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
import java.lang.*;

import javax.servlet.*;
import javax.servlet.http.HttpSession;

public class Ratings extends HttpServlet {

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
        String str = session.getAttribute("user_id").toString();
        int user_id = Integer.parseInt(str);
        PrintWriter out = response.getWriter();
        String myana = "<div class='rankings'><ul>";

        if (session.getAttribute("user_name") != null) {
            if ((!session.getAttribute("c1").toString().equals("2")) && (!session.getAttribute("c2").toString().equals("2")) && (!session.getAttribute("c3").toString().equals("2"))) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

                    int rate = 0, usr_rnk = -1;
                    Integer[] rank = new Integer[10];
                    Integer[] user = new Integer[10];
                    String[] user_name = new String[10];
                    String[] arr = new String[5]; //fname, lname, uname, email, skills;

                    ResultSet rs, rs1, rs2, rs3, rs4;

                    String sql = "SELECT * FROM users where Uid=?;";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setInt(1, user_id);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        arr[2] = rs.getString(2);
                    }
                    String sql1 = "SELECT * FROM profile where Uid=?;";
                    PreparedStatement ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, user_id);
                    rs1 = ps1.executeQuery();

                    if (rs1.next()) {
                        arr[0] = rs1.getString(2);
                        arr[1] = rs1.getString(3);
                        arr[3] = rs1.getString(4);
                        arr[4] = rs1.getString(5) + " " + rs1.getString(6) + " " + rs1.getString(7);
                    }

                    String sql2 = "SELECT * FROM rating where Uid=?;";
                    PreparedStatement ps2 = con.prepareStatement(sql2);
                    ps2.setInt(1, user_id);
                    rs2 = ps2.executeQuery();

                    if (rs2.next()) {
                        rate = rs2.getInt(2) + rs2.getInt(3) + rs2.getInt(4);
                    }

                    String sql3 = "SELECT * FROM rating order by (P_Count1+P_Count2+P_Count3) desc;";
                    PreparedStatement ps3 = con.prepareStatement(sql3);
                    rs3 = ps3.executeQuery();
                    int j = 0;
                    while (rs3.next() && j < 10) {
                        //rank[j] = rs3.getInt(2) + rs3.getInt(3) + rs3.getInt(4);
                        rank[j] = j + 1;
                        user[j] = rs3.getInt(1);
                        if (user[j] == user_id) {
                            usr_rnk = j;
                        }
                        j++;
                    }
                    //out.print("Rank Sample: " + rank[0] + "Name : " + user[0]);

                    int i = 0;

                    while (i < j) {
                        String sql4 = "SELECT Uname FROM users where Uid=?;";
                        PreparedStatement ps4 = con.prepareStatement(sql4);
                        ps4.setInt(1, user[i]);
                        rs4 = ps4.executeQuery();

                        if (rs4.next()) {
                            user_name[i] = rs4.getString(1);
                        }
                        i++;
                        //out.print("User Name: "+user_name[0]);
                    }

                    for (i = 0; i < j; i++) {
                        myana += "<li> Uname: " + user_name[i] + " Rank: " + (i + 1) + "</li>";
                    }
                    myana += "</ul></div>";

                    //request.setAttribute("output_ana", myana);
                    //ServletContext application = getServletContext();
                    session.setAttribute("details1", arr);
                    session.setAttribute("details2", rate);
                    //out.print("Rank Sample: " + rank[0]);
                    session.setAttribute("details3", rank[usr_rnk]);
                    session.setAttribute("details4", myana);

                    con.close();

                } catch (SQLException | ClassNotFoundException e) {
                }
            }
        }
    }

}

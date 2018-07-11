/*
Announcements feed karne ke lie hai ye.
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dinesh
 */
public class Feed extends HttpServlet {

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
            if (session.getAttribute("user_name").toString().equals("club1") || session.getAttribute("user_name").toString().equals("club2") || session.getAttribute("user_name").toString().equals("club3")) {
                PrintWriter out = response.getWriter();
                String feed = request.getParameter("feed");
                int fcount = 0;
                int temp = 0;

                String str2 = session.getAttribute("c_no").toString();
                int c_no = Integer.parseInt(str2);
                ServletContext sc = getServletContext();
                String str1 = (String) sc.getAttribute("cn");
                int cn = Integer.parseInt(str1);
                //out.print("CN is : "+str1);

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

                    //out.print("hello 1");
                    ResultSet rs;
                    String sql1 = "select A1_Count,A2_Count,A3_Count from counter;";
                    PreparedStatement ps1 = con.prepareStatement(sql1);
                    rs = ps1.executeQuery();
                    rs.next();
                    fcount = rs.getInt(c_no);
                    fcount++;
                    
                    int tot_count = rs.getInt(1) + rs.getInt(2) + rs.getInt(3);
                    //tot_count++;
                    temp = (fcount % 5);
                    if (temp == 0) {
                        temp = 5;
                    }
                    //out.print("hello 1");

                    String sql = "update announcement set A" + c_no + "= ? where Sno=?;";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, feed);
                    ps.setInt(2, temp);

                    //out.print(ps);

                    ps.executeUpdate();
                    
                    sql = "insert into announce_history values(?,?,?);";
                    ps = con.prepareStatement(sql);

                    ps.setInt(1, tot_count+1);
                    ps.setString(2, feed);
                    ps.setInt(3, c_no);

                    //out.print(ps);

                    ps.executeUpdate();

                    //out.print("hello 2");
                    String sql2 = "UPDATE counter set A" + c_no + "_Count = ?;";
                    PreparedStatement ps2 = con.prepareStatement(sql2);
                    ps2.setInt(1, fcount);
                    ps2.executeUpdate();

                    out.println("Announcement Taken!");
                    con.close();
                    response.setHeader("Refresh", "0;url=Feed.jsp");
                } catch (SQLException | ClassNotFoundException e) {
                }
            }
        }
    }

}

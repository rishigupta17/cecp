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
public class Get_Mentors extends HttpServlet {

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
            String mentor_out_code = "<ul>";

            String str = session.getAttribute("user_id").toString();
            int user_id = Integer.parseInt(str);
            String str1 = session.getAttribute("c_no").toString();
            int c_no = Integer.parseInt(str1);

            //out.print("hello -1");    
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");
                

                //out.print("hello 0");
                ResultSet rs;
                String sql = "select * from mentorship;";
                PreparedStatement ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                PreparedStatement ps1;
                String sql1;
                int newid;
                
                while (rs.next()) {

                    newid = rs.getInt(2);

                    //out.print("hello 1");
                    ResultSet rs1;
                    sql1 = "select * from users where Uid = ?;";

                    ps1 = con.prepareStatement(sql1);
                    ps1.setInt(1, newid);
                    //out.print(ps1);
                    rs1 = ps1.executeQuery();
                    //out.print("hello 2");

                    //out.print("");
                    if (rs1.next()) {
                        mentor_out_code += "<li><input type='radio' name='ment' value = " + rs.getInt(1) + " />" + rs1.getString(2) + " Skills: " + rs.getString(3) + "</li>";
                    }
                    //mentor_out_code += "h";
                    //out.print("hello 3");
                }
                mentor_out_code += "</ul>";
                session.setAttribute("mentor_out", mentor_out_code);
                //out.print(mentor_out_code);
                con.close();
            } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            }
        }
    }

}

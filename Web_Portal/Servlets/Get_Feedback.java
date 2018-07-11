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
public class Get_Feedback extends HttpServlet {

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
            String feedback_out_code = "";
            
            
            int club_no=0;
            
            
            String str = session.getAttribute("c1").toString();
            int c1 = Integer.parseInt(str);
            String str1 = session.getAttribute("c2").toString();
            int c2 = Integer.parseInt(str1);
            String str2 = session.getAttribute("c3").toString();
            int c3 = Integer.parseInt(str2);
            
            
            if(c1==2) {
                club_no = 1;
            }
            else if(c2==2) {
                club_no = 2;
            }
            else if(c3==2) {
                club_no = 3;
            }
            else {
                
            }
            
            //out.print("hello -1");    
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

                //out.print("hello 0");
                ResultSet rs;
                String sql = "select * from feedback where C_No = ?;";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, club_no);
                rs = ps.executeQuery();

                while (rs.next()) {
                    feedback_out_code += "<tr>";
                    feedback_out_code += "<td>" + rs.getString(3) + "</td>";
                    feedback_out_code += "<td>" + rs.getString(4) + "</td>";
                    feedback_out_code += "<td>" + rs.getString(2) + "</td>";
                    feedback_out_code += "</tr>";
                }
                
                session.setAttribute("feedback_out", feedback_out_code);
                out.print(feedback_out_code);
                con.close();
            } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            }
        }
    }

}

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
public class Get_Projects extends HttpServlet {

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
        String st = session.getAttribute("user_id").toString();
            int user_id = Integer.parseInt(st);
            String str1 = session.getAttribute("c_no").toString();
            int c_no = Integer.parseInt(str1);
            
            
            int club_no=0;
            String str = session.getAttribute("c1").toString();
            int c1 = Integer.parseInt(str);
            String str0 = session.getAttribute("c2").toString();
            int c2 = Integer.parseInt(str0);
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
            
            PrintWriter out = response.getWriter();
            String project_out_code_1 = " ";
            String project_out_code_2 = " ";
            String errmsg="";
            //int c_no=1, club_no=1;
            
        if (session.getAttribute("user_name") != null) {
            if( c_no == club_no) {
            
            

            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

                project_out_code_1 = "<ul>";
                project_out_code_2 = "<ul>";

                ResultSet rs;
                String sql = "select P_Name from project where Status = 1 AND M_Id = ? AND Club_No = ?;";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, user_id);
                ps.setInt(2, c_no);
                rs = ps.executeQuery();
                
               
                while (rs.next()) {
                    project_out_code_1 += "<li><input type='radio' disabled />" + rs.getString(1) + "</li>";
                    
                }
                project_out_code_1 += "</ul>";

                ResultSet rs1;
                String sql1 = "select * from project where Status = 0 AND M_Id = ? AND Club_No = ?;";
                PreparedStatement ps1 = con.prepareStatement(sql1);
                ps1.setInt(1, user_id);
                ps1.setInt(2, c_no);
                rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    project_out_code_2 += "<li><input type='radio' name='pro' value = " + rs1.getInt(2) + " />" + rs1.getString(3) + "</li>";
                }
                project_out_code_2 += "</ul>";

                if(project_out_code_1.equals(project_out_code_2)) {
                    errmsg = "You're not a Mentor of any Project.";
                }
                else {
                    errmsg = "";
                }
                
                session.setAttribute("project_1", project_out_code_1);
                session.setAttribute("project_2", project_out_code_2);
                session.setAttribute("err", errmsg);
                
                
                
                con.close();
                //response.setHeader("Refresh", "1;url=Project.jsp");
            } catch (SQLException | ClassNotFoundException | NullPointerException | NumberFormatException e) {
            }
            }
        }
    }

}

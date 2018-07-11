/*Shrini*/


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

/**
 *
 * @author Dinesh
 */
public class Announce extends HttpServlet {

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

        String myana = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");
            
            ResultSet rs;
                   
            String sql = "SELECT * FROM announcement;";
            PreparedStatement ps = con.prepareStatement(sql);
            
            
               
                myana = myana + "<div class='marq'><ul>";
            for (int i = 2; i <= 4; i++) {
                rs = ps.executeQuery();
                int j=0;
                while (rs.next() && j<5/*clb[i-2]*/) {
                    
                    myana += "<li>" + rs.getString(i) + "</li>";
                    j++;
                }
                
                
            }
            myana += "</ul></div>";
            //request.setAttribute("output_ana", myana);
            
            ServletContext application = getServletContext();
            application.setAttribute("announce_sc", myana);
            
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
        }

    }

}

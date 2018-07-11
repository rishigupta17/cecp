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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dinesh
 */
public class Login extends HttpServlet {

   
    HttpSession session;
    ServletContext sc_1;
    
    
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

        String u_name = request.getParameter("uname");
        String pass = request.getParameter("pwd");
       

        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

            //Statement stm = con.createStatement();
            ResultSet rs;

            //stm.executeUpdate("insert into users values("+user_n+","+pass_w+")");
            String sql = "SELECT * FROM users WHERE Uname = ?;";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u_name);
            rs = ps.executeQuery();
                    
            int user_id;

            PrintWriter out = response.getWriter();

            if (rs.next()) {
                if (rs.getString(3).equals(pass)) {
                    //out.println("Hello " + user_n + "!");
                        
                    user_id= rs.getInt(1);
                    int c1 = rs.getInt(4);
                    int c2 = rs.getInt(5);
                    int c3 = rs.getInt(6);
                    //sc_1 = getServletContext();
                    //sc_1.setAttribute("c1", c1);
                    //sc_1.setAttribute("c2", c2);
                    //sc_1.setAttribute("c3", c3);
                    
                    session = request.getSession();
                    session.setAttribute("user_name",u_name);
                    session.setAttribute("user_id",user_id);
                    session.setAttribute("c1", c1);
                    session.setAttribute("c2", c2);
                    session.setAttribute("c3", c3);
                    
                    
                } else {

                    request.setAttribute("errMsg", "Invalid Password! ");
                    // The following will keep you in the login page
                    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                }
            } else {

                request.setAttribute("errMsg", "Not Registered!");
                // The following will keep you in the login page
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
                //response.sendRedirect("login_j.jsp");
               
            }
            
            con.close();
            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }*/
            response.sendRedirect("index.jsp");

        } catch (SQLException | ClassNotFoundException e) {
        }

        
    }

}

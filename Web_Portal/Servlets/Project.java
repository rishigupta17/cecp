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
public class Project extends HttpServlet {

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
            String output_code = "<ul>";

            String str = session.getAttribute("user_id").toString();
            int user_id = Integer.parseInt(str);
            String str1 = session.getAttribute("c_no").toString();
            int c_no = Integer.parseInt(str1);

            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

                ResultSet rs;
                String sql = "select * from project where Club_No = ? AND Vacancy > 0";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, c_no);
                rs = ps.executeQuery();

                while (rs.next()) {
                    //out.print("");
                    output_code += "<li><input type='radio' name='proj' value = " + rs.getInt(2) + " />" + rs.getString(3) + "</li>";
                }
                output_code+="</ul>";
                session.setAttribute("out_code", output_code);
                //out.print(output_code);
                con.close();
            } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            }
        }
    }

}

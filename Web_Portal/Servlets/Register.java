/*
Shrini
 */
package login_register;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author Dinesh
 */
public class Register extends HttpServlet {

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
        String f_name = request.getParameter("fname");
        String l_name = request.getParameter("lname");
        String email_id = request.getParameter("email_id");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "root");

            ResultSet rs;

            PrintWriter out = response.getWriter();
            
            ResultSet rs2;
            String sql1 = "select Uid_Count from counter;";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            rs2 = ps1.executeQuery();
            out.print("hello");
            int uno = 0;
            if (rs2.next()) {
                uno = rs2.getInt(1);
                uno += 1;
            }
            
            out.print(" Check 1! ");
            
            String sql = "INSERT INTO users VALUES(?,?,?,?,?,?);";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, uno);
            
            ps.setString(2, u_name);
            ps.setString(3, pass);
            
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            out.print(ps);
            ps.executeUpdate();

            out.print(" Check 2! ");
            sql = "INSERT INTO profile VALUES(?,?,?,?,?,?,?,?);";
            ps = con.prepareStatement(sql);

            ps.setInt(1, uno);
            ps.setString(2, f_name);
            ps.setString(3, l_name);
            ps.setString(4, email_id);
            ps.setString(5, "");
            ps.setString(6, "");
            ps.setString(7, "");
            ps.setString(8, "");
            ps.executeUpdate();

            out.print(" Check 3! ");
            sql = "INSERT INTO rating VALUES(?,?,?,?);";
            ps = con.prepareStatement(sql);

            ps.setInt(1, uno);
            ps.setInt(2, 0);
            ps.setInt(3, 0);
            ps.setInt(4, 0);

            ps.executeUpdate();
out.print(" Check 4! ");
            sql = "UPDATE counter set Uid_Count = ?;";
            ps = con.prepareStatement(sql);
            ps.setInt(1, uno);
            ps.executeUpdate();
            
            out.println(u_name + " Registered!");
            con.close();
            response.setHeader("Refresh", "1;url=index.jsp");
        } catch (SQLException | ClassNotFoundException e) {
        }

    }

}

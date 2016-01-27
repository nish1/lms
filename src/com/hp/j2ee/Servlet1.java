package com.hp.j2ee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet(name = "Geetha", urlPatterns = { "/Geetha" })
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet1() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String name= request.getParameter("user_name");
		String pwd=request.getParameter("pwd");
		
		HttpSession session=request.getSession();
		session.setAttribute("sname", name);
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "password");
		
		stmt=con.createStatement();
		rs=stmt.executeQuery("select * from login");
		while(rs.next()){
			if(name.equals(rs.getString(1)) && pwd.equals(rs.getString(2))){
				RequestDispatcher rd=request.getRequestDispatcher("dashboard.jsp");
				rd.forward(request, response);
			}
		}
		pw.println("Invalid login");
		RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
		rd.include(request, response);
		
		} 
	catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			try {
			rs.close();
			stmt.close();
			con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
	
		}
	}
}

package com.adaptiveWeb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
		doGet(request, response);
		String type = request.getParameter("type");
if(type!=null && type.toLowerCase().equalsIgnoreCase("signup"))
{
	String fName = request.getParameter("fName");
	String lName = request.getParameter("lName");
	String pwd = request.getParameter("pwd");
	String emailId = request.getParameter("emailId");
    //Authenticate the user and set email Id

User user =new User();
user.setEmailId(emailId);
user.setfName(fName);
user.setlName(lName);

request.getSession().setAttribute("user", user);
    response.sendRedirect(request.getContextPath() + "/main.html");
	//Store in Database
   // request.getRequestDispatcher("/WEB-INF/main.html").forward(request, response);

}
if(type!=null && type.toLowerCase().equalsIgnoreCase("login"))
{
	String pwd = request.getParameter("pwd");
	String emailId = request.getParameter("emailId");
	//validate user
    //Authenticate the user and set email Id
    User u = new User();
    u.setEmailId(emailId);

request.getSession().setAttribute("user", u);
    response.sendRedirect(request.getContextPath() + "/main.html");
}
		}
		catch(Exception e)
		{
			request.getRequestDispatcher("/WEB-INF/login.html").forward(request, response);

			
		}

	}

}

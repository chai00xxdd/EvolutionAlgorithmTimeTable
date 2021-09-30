package servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Pages.PageName;
import Server.ServerManager;
import Utils.Attributes;
import Utils.ServerUtils;
import Utils.UserValidations;
import school.Teacher;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("in login servlet");
		if(isUserAlredyLoggedIn(request))
		{
			System.out.println("user alredy logged in");
			response.getWriter().println(PageName.UserPage);
			return;
		}
		
	
		String userNameFromRequest = request.getParameter(Attributes.Username);
		ServerManager serverManager =  ServerUtils.getServerManager(getServletContext());
		 if(UserValidations.isEmptyUserName(userNameFromRequest))
		{
			ServerUtils.sendError(response, "username cant be empty!!!");
			return;
		}
		
		
		 synchronized (this) {
		if( serverManager.IsUserLoggedIn(userNameFromRequest))
		{
			ServerUtils.sendError(response, userNameFromRequest+" alredy logged in!!! please choose another username");
			return;
		}
		serverManager.addUserIfNotExists(userNameFromRequest);
		serverManager.login(userNameFromRequest);
		 }
		
		request.getSession().setAttribute(Attributes.Username,userNameFromRequest);
		response.addCookie(new Cookie(Attributes.Username, userNameFromRequest));
		response.getWriter().println(PageName.UserPage);
	
		
		
	}
	
	private boolean isUserAlredyLoggedIn(HttpServletRequest request)
	{
		return request.getSession(false) != null;
	}

	
}

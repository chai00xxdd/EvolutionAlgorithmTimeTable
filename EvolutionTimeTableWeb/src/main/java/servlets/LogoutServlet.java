package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.ServerManager;
import Utils.ServerUtils;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// TODO Auto-generated method stub
		
		
		if(!ServerUtils.isLoggedIn(request))
		{
			return;
		}
		 
		 String userName = ServerUtils.getUserName(request);
		 ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		 serverManager.logout(userName);
		 request.getSession(false).invalidate();
	
		
	}

	

}

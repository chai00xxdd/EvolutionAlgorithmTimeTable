package servlets;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.ServerManager;
import ServerDTO.UserDTO;
import Utils.ServerUtils;

/**
 * Servlet implementation class GetUsersServlet
 */
@WebServlet("/GetUsersServlet")
public class GetUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServerUtils.isLoggedIn(request))
		{
			ServerUtils.sendError(response,"go to login");
			return;
		}
	
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		List<UserDTO> onlineUsers = serverManager.getAllUsersOnline();
		ServerUtils.writeJson(response, onlineUsers);
		
	}

	

}

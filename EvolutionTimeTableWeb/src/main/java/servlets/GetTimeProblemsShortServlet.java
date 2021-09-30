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
 * Servlet implementation class GetTimeProblemsShortServlet
 */
@WebServlet("/GetTimeProblemsShortServlet")
public class GetTimeProblemsShortServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTimeProblemsShortServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServerUtils.isLoggedIn(request))
		{
			return;
		}
		
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		String username = ServerUtils.getUserName(request);
		ServerUtils.writeJson(response, serverManager.getAllTimeProblemsShortDTO(username));
	}

	
}

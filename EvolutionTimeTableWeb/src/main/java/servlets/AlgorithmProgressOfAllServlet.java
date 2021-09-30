package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.ServerManager;
import ServerDTO.UserAlgorithmProgress;
import Utils.Attributes;
import Utils.ServerUtils;

/**
 * Servlet implementation class AlgorithmProgressOfAllServlet
 */
@WebServlet("/AlgorithmProgressOfAllServlet")
public class AlgorithmProgressOfAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlgorithmProgressOfAllServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!ServerUtils.isLoggedIn(request))
		{
			return;
		}
		
		String problemId = request.getParameter(Attributes.Id);
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		List<UserAlgorithmProgress> progressOfAllUsers = serverManager.getAllUsersAlgorithmProgressOfProblem(problemId);
		ServerUtils.writeJson(response, progressOfAllUsers);
	}

	
}

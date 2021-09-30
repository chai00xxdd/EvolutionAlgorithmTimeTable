package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.BestTimeTableSolutionDTO;
import Server.ServerManager;
import Utils.Attributes;
import Utils.ServerUtils;

/**
 * Servlet implementation class GetBestSolutionServlet
 */
@WebServlet("/GetBestSolutionServlet")
public class GetBestSolutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBestSolutionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if(!ServerUtils.isLoggedIn(request))
		{
			return;
		}
		
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		String userNameToViewHisBestSolution = request.getParameter(Attributes.Username);
		String problemIdToView = request.getParameter(Attributes.Id);
		BestTimeTableSolutionDTO bestSolutionDTO = serverManager.pullBestSolutionDTO(userNameToViewHisBestSolution,problemIdToView);
		if(bestSolutionDTO != null)
		{
		ServerUtils.writeJson(response, bestSolutionDTO);
		}
		else
		{
			ServerUtils.sendError(response, "algorithm must run atlist once!!!!");
		}
	}

	

}

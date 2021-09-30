package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.ServerManager;
import Utils.Attributes;
import Utils.ServerUtils;
import Validations.EvolutionAlgorithmValidator;
import evolution.algorithm.EvolutionAlgorithmEngine;

/**
 * Servlet implementation class PopulationServlet
 */
@WebServlet("/PopulationServlet")
public class PopulationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PopulationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!ServerUtils.isLoggedIn(request))
		{
			return;
		}
		
		
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		String userName = ServerUtils.getUserName(request);
		String problemId = ServerUtils.getProblemId(request);
		EvolutionAlgorithmEngine engineOfUserOfProblem = serverManager.getOrDefaultEngine(userName, problemId);
		String populationString =request.getParameter(Attributes.Population);
		try
		{
			int newPopulationSize = EvolutionAlgorithmValidator.getValidPopulation(populationString);
			engineOfUserOfProblem.getAlgorithmConfiguration().setPopulationSize(newPopulationSize);
		}
		catch(Exception e)
		{
			ServerUtils.sendError(response,e.getMessage());
		}
		//engineOfUserOfProblem.getAlgorithmConfiguration()
	}
	
	
	

}

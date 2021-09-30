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
import evolution.algorithm.crossover.CrossOver;

/**
 * Servlet implementation class CrossOverServlet
 */
@WebServlet("/CrossOverServlet")
public class CrossOverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public CrossOverServlet() {
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
			String crossoverName = request.getParameter(Attributes.Name);
			String cuttingPoints = request.getParameter(Attributes.CuttingPoints);
			String Parameter = ServerUtils.getParameter(request);
			EvolutionAlgorithmEngine engineOfUserOfProblem = serverManager.getOrDefaultEngine(userName, problemId);
			try
			{
			CrossOver crossOver = EvolutionAlgorithmValidator.getValidCrossOver(crossoverName,cuttingPoints,Parameter);
			engineOfUserOfProblem.getAlgorithmConfiguration().setCrossover(crossOver);
			}
			catch(Exception e)
			{
				ServerUtils.sendError(response, e.getMessage());
			}
	}

}

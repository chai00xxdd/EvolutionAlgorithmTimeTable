package servlets;

import java.io.IOException;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.ServerManager;
import Utils.Attributes;
import Utils.ServerUtils;
import Validations.EvolutionAlgorithmValidator;
import evolution.algorithm.EvolutionAlgorithm;
import evolution.algorithm.EvolutionAlgorithmEngine;


@WebServlet("/StopConditionsServlet")
public class StopConditionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public StopConditionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 if(!ServerUtils.isLoggedIn(request))
		 {
			 return;
		 }
		 
		
		 
		 ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
			String userName = ServerUtils.getUserName(request);
			String problemId = ServerUtils.getProblemId(request);
			
			String stopConditionName = request.getParameter(Attributes.Name);
			String paramter = request.getParameter(Attributes.Parameter);
			String enabled = request.getParameter(Attributes.Enabled);
			
			EvolutionAlgorithmEngine engineOfUserOfProblem = serverManager.getOrDefaultEngine(userName, problemId);
			try
			{
				Predicate<EvolutionAlgorithm> stopCondition = EvolutionAlgorithmValidator.getValidStopCondition(stopConditionName,paramter,enabled);
				engineOfUserOfProblem.AddOrUpdateStopCondition(stopCondition);
			}
			catch(Exception e)
			{
				ServerUtils.sendError(response, e.getMessage());
				
			}
	}

}

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
import evolution.algorithm.Mutation;

/**
 * Servlet implementation class MutationsServlet
 */
@WebServlet("/MutationsServlet")
public class MutationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MutationsServlet() {
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
			//System.out.println(problemId);
			String mutationName = request.getParameter(Attributes.Name);
			String MutationId = request.getParameter(Attributes.MutationId);
			String probality = request.getParameter(Attributes.Probality);
			String Parameter = ServerUtils.getParameter(request);
			String operation = request.getParameter(Attributes.Operation);
			String component = request.getParameter(Attributes.Component);
			EvolutionAlgorithmEngine engineOfUserOfProblem = serverManager.getOrDefaultEngine(userName, problemId);
			
			if(operation !=null && operation.toUpperCase().equals("DELETE"))
			{
				engineOfUserOfProblem.getAlgorithmConfiguration().removeMutationById(MutationId);
				return;
			}
			
			else if(operation.toUpperCase().equals("ADD"))
			{
			
			try
			{
			Mutation newMutation = EvolutionAlgorithmValidator.getValidMutation(mutationName,probality,Parameter,component);
			
			engineOfUserOfProblem.getAlgorithmConfiguration().addMutation(newMutation);
			}
			catch(Exception e)
			{
				ServerUtils.sendError(response, e.getMessage());
			}
			}
			else
			{
				ServerUtils.sendError(response,"operation not supported!!!");
			}
		
			
	}

}

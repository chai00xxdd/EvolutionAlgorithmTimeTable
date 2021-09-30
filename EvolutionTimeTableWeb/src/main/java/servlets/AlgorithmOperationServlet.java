package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.ServerManager;
import Utils.Attributes;
import Utils.ServerUtils;
import evolution.algorithm.AlgorithmProgress;
import evolution.algorithm.EvolutionAlgorithmEngine;

/**
 * Servlet implementation class AlgorithmOperationState
 */
@WebServlet("/AlgorithmOperationServlet")
public class AlgorithmOperationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AlgorithmOperationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!ServerUtils.isLoggedIn(request))
		{
			return;
		}
		//System.out.println("in operation get servlet");
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		String userName = ServerUtils.getUserName(request);
		String timeProblemId = request.getParameter(Attributes.Id);
		String getGraphData = request.getParameter(Attributes.GetGraph);
		if(getGraphData == null )
		{
		AlgorithmProgress algorithmProgress = serverManager.getAlgorithmProgress(userName, timeProblemId);
		ServerUtils.writeJson(response, algorithmProgress);
		}
		else
		{
			
			List<AlgorithmProgress> progressGraph = serverManager.getAlgorithmProgressGraph(userName, timeProblemId) ;
			System.out.println(progressGraph.size());
			ServerUtils.writeJson(response, progressGraph);
		}
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServerUtils.isLoggedIn(request))
		{
			return;
		}
		// TODO Auto-generated method stub
		
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		String userName = ServerUtils.getUserName(request);
		String timeProblemId = request.getParameter(Attributes.Id);
		String operation = request.getParameter(Attributes.Operation);
		
		System.out.println("in operation post servlet");
		System.out.println("problem id = " + timeProblemId);
		System.out.println("operation = " +operation);
		EvolutionAlgorithmEngine engine = serverManager.getOrDefaultEngine(userName, timeProblemId);
		if(operation.equals("run"))
		{
			engine.runAlgorithm();
		}
		else if(operation.equals("stop"))
		{
			engine.stopAlgorithm();
		}
		else if(operation.equals("resume"))
		{
			engine.resumeAlgorithm();
		}
		else if(operation.equals("pause"))
		{
			engine.pauseAlgorithm();
		}
		System.out.println("operation happend succesfully");
	}

}

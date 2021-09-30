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
import evolution.algorithm.selection.RouleteWheelSelection;
import evolution.algorithm.selection.Selection;
import evolution.algorithm.selection.SelectionName;
import evolution.algorithm.selection.TournemantSelection;
import evolution.algorithm.selection.TruncationSelection;
import evolution.timetable.crossover.CrossOverName;


@WebServlet("/SelectionServlet")
public class SelectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public SelectionServlet() {
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
			String selectionNameString = request.getParameter(Attributes.Name);
			String Ellitism = request.getParameter(Attributes.Elittism);
			String Parameter = ServerUtils.getParameter(request);
			EvolutionAlgorithmEngine engineOfUserOfProblem = serverManager.getOrDefaultEngine(userName, problemId);
			
			try
			{
				Selection newSelection = EvolutionAlgorithmValidator.getSelection(selectionNameString, Ellitism, Parameter);
				engineOfUserOfProblem.getAlgorithmConfiguration().setSelection(newSelection);
			}
			catch(Exception e)
			{
				ServerUtils.sendError(response, e.getMessage());
			}
			
	}

}

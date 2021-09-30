package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.AlgorithmConfuigrationDTO;
import Server.ServerManager;
import Utils.Attributes;
import Utils.ServerUtils;


@WebServlet("/AlgorithmConfuigrationServlet")
public class AlgorithmConfuigrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public AlgorithmConfuigrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		  if(!ServerUtils.isLoggedIn(request))
		  {
			  return;
		  }
		  
		  ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		  String userNameToViewHisConfig = request.getParameter(Attributes.Username);
		  String timeProblemId = request.getParameter(Attributes.Id);
		  
		  
		  
		  AlgorithmConfuigrationDTO configurationDTO = serverManager.pullAlgorithmConfigDTO(userNameToViewHisConfig, timeProblemId);
		
		  ServerUtils.writeJson(response, configurationDTO);
		  
	}

	

}

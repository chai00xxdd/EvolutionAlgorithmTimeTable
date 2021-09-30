package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.ServerManager;
import ServerDTO.TimeTableProblemWrapperDTO;
import Utils.Attributes;
import Utils.ServerUtils;

/**
 * Servlet implementation class GetTimeProblemDetails
 */
@WebServlet("/GetTimeProblemDetails")
public class GetTimeProblemDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTimeProblemDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("in GetProblemDetails Servlet");
		System.out.println("Requested id = "+request.getParameter(Attributes.Id));
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		TimeTableProblemWrapperDTO wrapperDTO = serverManager.getTimeProblemDtoById(request.getParameter(Attributes.Id));
		ServerUtils.writeJson(response, wrapperDTO);
	}

	
}

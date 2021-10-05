package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Chat.ChatRecord;
import Server.ServerManager;
import Utils.Attributes;
import Utils.ServerUtils;

/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatServlet() {
        
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("at chat get");
		if(!ServerUtils.isLoggedIn(request))
		{
			ServerUtils.sendError(response, "not logged in");
		}
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
    	List<ChatRecord> chatRecords = serverManager.getAllChatRecords();
    	ServerUtils.writeJson(response, chatRecords);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("at chat post");
		try
		{
		if(!ServerUtils.isLoggedIn(request))
		{
			ServerUtils.sendError(response, "not logged in!!!");
		}
		
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		String userName =ServerUtils.getUserName(request);
		String message = request.getParameter(Attributes.Message);
		serverManager.addChatRecord(new ChatRecord(userName,message));
		}
		catch(Exception e)
		{
			System.out.println("in server");
			e.printStackTrace();
		}
	}

}

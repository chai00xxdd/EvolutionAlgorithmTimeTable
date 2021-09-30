package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Server.Response;
import Server.ServerManager;
import Utils.ServerUtils;
import console.jaxb.schema.timetable.XmlApiTimeTable;
import evolution.timetable.TimeTableProblem;

/**
 * Servlet implementation class UploadTimeTableProblemServlet
 */
@WebServlet("/UploadTimeTableProblemServlet")
@MultipartConfig (fileSizeThreshold = 1024*1024,maxFileSize = 1014*1024*5)
public class UploadTimeTableProblemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadTimeTableProblemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if(!ServerUtils.isLoggedIn(request))
		{
			return;
		}
		ServerManager serverManager = ServerUtils.getServerManager(getServletContext());
		Collection<Part> fileParts= request.getParts();
		Part xmlFile = fileParts.stream().findFirst().get();
	 
		synchronized (this) {
	
		XmlApiTimeTable xmlApi = new XmlApiTimeTable(2);
		
		try {
			
			
			XmlApiTimeTable.checkValidationOfXmlFileName(xmlFile.getSubmittedFileName());
			TimeTableProblem problem = xmlApi.loadTimeTableProblem(xmlFile.getInputStream());
			serverManager.addTimeProblem(ServerUtils.getUserName(request),problem);
			ServerUtils.writeJson(response, new Response("file uploaded succesfully"));
			
		} catch (Exception e) {
			
			ServerUtils.writeJson(response,new Response(e.getMessage(),xmlApi.getAllErrors()));
			
		}
		
		}
		
		
	}

}

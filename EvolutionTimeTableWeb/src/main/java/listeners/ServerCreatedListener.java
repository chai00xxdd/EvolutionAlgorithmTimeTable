package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import Server.ServerManager;
import Utils.Attributes;

/**
 * Application Lifecycle Listener implementation class ServerCreatedListener
 *
 */
@WebListener
public class ServerCreatedListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ServerCreatedListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent contextEvent)  { 
         // TODO Auto-generated method stub
    	System.out.println("Web Server Started");
    	contextEvent.getServletContext().setAttribute(Attributes.ServerManager, new ServerManager());
    }
	
}

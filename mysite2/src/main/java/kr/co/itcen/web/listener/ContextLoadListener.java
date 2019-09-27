package kr.co.itcen.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextLoadListener implements ServletContextListener {

    public ContextLoadListener() {
    }
    public void contextDestroyed(ServletContextEvent arg0)  { 
    }
    public void contextInitialized(ServletContextEvent servletContextEvent)  {
    	String contextConfigLoaction = servletContextEvent.getServletContext().getInitParameter("contextConfigLoaction");
    	System.out.println("mysite2 app starts..... with "+ contextConfigLoaction);
    }
	
}

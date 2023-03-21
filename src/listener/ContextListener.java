package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import main.CFRTAdapter;

public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		CFRTAdapter.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		CFRTAdapter.init(arg0);
	}

}

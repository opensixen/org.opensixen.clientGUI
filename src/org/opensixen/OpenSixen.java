package org.opensixen;

import java.util.List;
import java.util.logging.Level;

import org.compiere.Adempiere;
import org.compiere.apps.AMenu;
import org.compiere.util.CLogger;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.opensixen.exception.OSXException;
import org.opensixen.interfaces.IStartupProcess;
import org.opensixen.osgi.Service;



public class OpenSixen implements IApplication {

	CLogger log;
	
	/**
	 * Start opensixen
	 */
	private void initOpensixen()	{
		// Change some variables.
		Adempiere.MAIN_VERSION = "1.0.0";
		Adempiere.DATE_VERSION = "2010-06-14";
		Adempiere.DB_VERSION = "2010-06-14";
		Adempiere.NAME = "Opensixen\u00AE";
		Adempiere.TITLE	= "Opensixen";
		Adempiere.URL = "www.opensixen.org";
		Adempiere.ONLINE_HELP_URL = "http://www.adempiere.com/wiki/index.php/Manual";
		Adempiere.s_supportEmail = "support@opensixen.org";
		Adempiere.SUB_TITLE	= "Smart Suite ERP,CRM and SCM";
		Adempiere.ADEMPIERE_R = "Opensixen\u00AE";
		Adempiere.COPYRIGHT		= "\u00A9 2010 Opensixen\u00AE";
				
		
		// Perform framwork startup
		Adempiere.startup(true);
		
		// Startup logger
		log = CLogger.getCLogger(getClass());
		
		// Launch OSGi startup plugins
		List<IStartupProcess>  startupProcess = Service.list(IStartupProcess.class);
		for (IStartupProcess process:startupProcess)	{
			try {
				process.run();
			}
			catch (OSXException e)	{
				log.log(Level.SEVERE, "IStartupProcess fail: " + process.getClass().getName(), e);
			}
		}
		
		AMenu menu = new AMenu();	
	}
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		initOpensixen();
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}


 /******* BEGIN LICENSE BLOCK *****
 * Versión: GPL 2.0/CDDL 1.0/EPL 1.0
 *
 * Los contenidos de este fichero están sujetos a la Licencia
 * Pública General de GNU versión 2.0 (la "Licencia"); no podrá
 * usar este fichero, excepto bajo las condiciones que otorga dicha 
 * Licencia y siempre de acuerdo con el contenido de la presente. 
 * Una copia completa de las condiciones de de dicha licencia,
 * traducida en castellano, deberá estar incluida con el presente
 * programa.
 * 
 * Adicionalmente, puede obtener una copia de la licencia en
 * http://www.gnu.org/licenses/gpl-2.0.html
 *
 * Este fichero es parte del programa opensiXen.
 *
 * OpensiXen es software libre: se puede usar, redistribuir, o
 * modificar; pero siempre bajo los términos de la Licencia 
 * Pública General de GNU, tal y como es publicada por la Free 
 * Software Foundation en su versión 2.0, o a su elección, en 
 * cualquier versión posterior.
 *
 * Este programa se distribuye con la esperanza de que sea útil,
 * pero SIN GARANTÍA ALGUNA; ni siquiera la garantía implícita 
 * MERCANTIL o de APTITUD PARA UN PROPÓSITO DETERMINADO. Consulte 
 * los detalles de la Licencia Pública General GNU para obtener una
 * información más detallada. 
 *
 * TODO EL CÓDIGO PUBLICADO JUNTO CON ESTE FICHERO FORMA PARTE DEL 
 * PROYECTO OPENSIXEN, PUDIENDO O NO ESTAR GOBERNADO POR ESTE MISMO
 * TIPO DE LICENCIA O UNA VARIANTE DE LA MISMA.
 *
 * El desarrollador/es inicial/es del código es
 *  FUNDESLE (Fundación para el desarrollo del Software Libre Empresarial).
 *  Indeos Consultoria S.L. - http://www.indeos.es
 *
 * Contribuyente(s):
 *  Eloy Gómez García <eloy@opensixen.org> 
 *
 * Alternativamente, y a elección del usuario, los contenidos de este
 * fichero podrán ser usados bajo los términos de la Licencia Común del
 * Desarrollo y la Distribución (CDDL) versión 1.0 o posterior; o bajo
 * los términos de la Licencia Pública Eclipse (EPL) versión 1.0. Una 
 * copia completa de las condiciones de dichas licencias, traducida en 
 * castellano, deberán de estar incluidas con el presente programa.
 * Adicionalmente, es posible obtener una copia original de dichas 
 * licencias en su versión original en
 *  http://www.opensource.org/licenses/cddl1.php  y en  
 *  http://www.opensource.org/licenses/eclipse-1.0.php
 *
 * Si el usuario desea el uso de SU versión modificada de este fichero 
 * sólo bajo los términos de una o más de las licencias, y no bajo los 
 * de las otra/s, puede indicar su decisión borrando las menciones a la/s
 * licencia/s sobrantes o no utilizadas por SU versión modificada.
 *
 * Si la presente licencia triple se mantiene íntegra, cualquier usuario 
 * puede utilizar este fichero bajo cualquiera de las tres licencias que 
 * lo gobiernan,  GPL 2.0/CDDL 1.0/EPL 1.0.
 *
 * ***** END LICENSE BLOCK ***** */

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


/**
 * Opensixen Application
 * 
 * Launch Opensixen
 * 
 * 
 * @author Eloy Gomez
 * Indeos Consultoria http://www.indeos.es
 *
 */
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


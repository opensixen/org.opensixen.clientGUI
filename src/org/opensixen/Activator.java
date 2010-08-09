package org.opensixen;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	
	private static BundleContext context;
	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	
	public static BundleContext getContext()	{
		return context;
	}
}

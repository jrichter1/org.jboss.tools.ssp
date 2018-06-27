/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package org.jboss.tools.ssp.server.wildfly.servertype.impl;

import org.jboss.tools.ssp.api.ServerManagementAPIConstants;
import org.jboss.tools.ssp.server.spi.servertype.IServer;
import org.jboss.tools.ssp.server.wildfly.servertype.AbstractJBossServerDelegate;
import org.jboss.tools.ssp.server.wildfly.servertype.IJBossStartLauncher;
import org.jboss.tools.ssp.server.wildfly.servertype.ILauncher;

public class JBossASServerDelegate extends AbstractJBossServerDelegate {
	public JBossASServerDelegate(IServer server) {
		super(server);
		setServerState(ServerManagementAPIConstants.STATE_STOPPED);
	}
	protected IJBossStartLauncher getStartLauncher() {
		return LauncherDiscovery.getDefault().getStartupLauncher(getServer());
	}
	
	protected ILauncher getStopLauncher() {
		return LauncherDiscovery.getDefault().getShutdownLauncher(getServer());
	}
	@Override
	protected String getPollURL(IServer server) {
		// TODO?
		return "http://localhost:8080";
	}
}

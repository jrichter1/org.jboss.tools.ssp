/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.rsp.server.wildfly.servertype.capabilities;

import org.jboss.tools.rsp.server.spi.servertype.IServer;
import org.jboss.tools.rsp.server.wildfly.servertype.launch.IDefaultLaunchArguments;
import org.jboss.tools.rsp.server.wildfly.servertype.launch.Wildfly100DefaultLaunchArguments;

public class Wildfly100ExtendedProperties extends JBossAS710ExtendedProperties {

	public Wildfly100ExtendedProperties(IServer obj) {
		super(obj);
	}

	@Override
	public String getRuntimeTypeVersionString() {
		return "10.x"; //$NON-NLS-1$
	}
	
	@Override
	public IDefaultLaunchArguments getDefaultLaunchArguments() {
		return new Wildfly100DefaultLaunchArguments(server);
	}
	
	@Override
	public String getJMXUrl() {
			return getJMXUrl(getManagementPort(), "service:jmx:remote+http"); //$NON-NLS-1$
	}

	@Override
	protected int getManagementPort() {
		return 9990;
	}

	@Override
	public boolean requiresJDK() {
		return true;
	}

//	@Override
//	public String getManagerServiceId() {
//		return IJBoss7ManagerService.WILDFLY_VERSION_900;
//	}
	
	@Override
	public boolean allowExplodedDeploymentsInWarLibs() {
		return true;
	}
	
	@Override
	public boolean allowExplodedDeploymentsInEars() {
		return true;
	}
	@Override
	public String getMinimumJavaVersionString() {
		return "1.8.";
	}
	@Override
	public String getMaximumJavaVersionString() {
		return "11.";
	}

}

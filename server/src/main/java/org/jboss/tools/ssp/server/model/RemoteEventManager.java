/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package org.jboss.tools.ssp.server.model;

import java.util.List;

import org.jboss.tools.ssp.api.SSPClient;
import org.jboss.tools.ssp.api.dao.DiscoveryPath;
import org.jboss.tools.ssp.api.dao.ServerHandle;
import org.jboss.tools.ssp.api.dao.ServerProcess;
import org.jboss.tools.ssp.api.dao.ServerProcessOutput;
import org.jboss.tools.ssp.api.dao.ServerStateChange;
import org.jboss.tools.ssp.api.dao.VMDescription;
import org.jboss.tools.ssp.eclipse.jdt.launching.IVMInstall;
import org.jboss.tools.ssp.eclipse.jdt.launching.IVMInstallChangedListener;
import org.jboss.tools.ssp.eclipse.jdt.launching.PropertyChangeEvent;
import org.jboss.tools.ssp.server.ServerManagementServerImpl;
import org.jboss.tools.ssp.server.spi.discovery.IDiscoveryPathListener;
import org.jboss.tools.ssp.server.spi.model.IServerModelListener;

public class RemoteEventManager implements IDiscoveryPathListener, IVMInstallChangedListener, IServerModelListener {
	private ServerManagementServerImpl server;
	public RemoteEventManager(ServerManagementServerImpl serverManagementServerImpl) {
		this.server = serverManagementServerImpl; 
		serverManagementServerImpl.getModel().getDiscoveryPathModel().addListener(this);
		//serverManagementServerImpl.getModel().getVMInstallModel().addListener(this);
		serverManagementServerImpl.getModel().getServerModel().addServerModelListener(this);
	}
	@Override
	public void discoveryPathAdded(DiscoveryPath path) {
		List<SSPClient> l = server.getClients();
		for( SSPClient c : l) {
			c.discoveryPathAdded(path);
		}
	}
	@Override
	public void discoveryPathRemoved(DiscoveryPath path) {
		List<SSPClient> l = server.getClients();
		for( SSPClient c : l) {
			c.discoveryPathRemoved(path);
		}
	}

	private VMDescription getDescription(IVMInstall vmi) {
		String vers = vmi.getJavaVersion();
		return new VMDescription(vmi.getId(), vmi.getInstallLocation().getAbsolutePath(), vers);
	}

	
	@Override
	public void vmAdded(IVMInstall vm) {
//		List<SSPClient> l = server.getClients();
//		for( SSPClient c : l) {
//			c.vmAdded(getDescription(vm));
//		}
	}
	@Override
	public void vmRemoved(IVMInstall vm) {
//		List<SSPClient> l = server.getClients();
//		for( SSPClient c : l) {
//			c.vmRemoved(getDescription(vm));
//		}
	}

	public void serverAdded(ServerHandle server2) {
		List<SSPClient> l = server.getClients();
		for( SSPClient c : l) {
			c.serverAdded(server2);
		}
	}
	
	public void serverRemoved(ServerHandle server2) {
		List<SSPClient> l = server.getClients();
		for( SSPClient c : l) {
			c.serverRemoved(server2);
		}
	}
	
	public void serverAttributesChanged(ServerHandle server) {
		// TODO 
	}
	
	public void serverStateChanged(ServerHandle server, int state) {
		List<SSPClient> l = this.server.getClients();
		for( SSPClient c : l) {
			c.serverStateChanged(new ServerStateChange(server, state));
		}
	}
	
	public void serverProcessCreated(ServerHandle server, String processId) {
		List<SSPClient> l = this.server.getClients();
		for( SSPClient c : l) {
			c.serverProcessCreated(new ServerProcess(server, processId));
		}
	}
	
	public void serverProcessTerminated(ServerHandle server, String processId) {
		List<SSPClient> l = this.server.getClients();
		for( SSPClient c : l) {
			c.serverProcessTerminated(new ServerProcess(server, processId));
		}
	}
	
	public void serverProcessOutputAppended(ServerHandle server, String processId, int streamType, String text) {
		List<SSPClient> l = this.server.getClients();
		for( SSPClient c : l) {
			c.serverProcessOutputAppended(new ServerProcessOutput(
					server, processId, streamType, text));
		}
	}
	
	
	
	// To be ignored
	@Override
	public void defaultVMInstallChanged(IVMInstall previous, IVMInstall current) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vmChanged(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}

package com.winchannel.core.importer;

import java.util.Map;

import com.winchannel.core.dao.HibernatePersister;

public abstract class ImpEventHandler {
	
	private ImpConfigurator conf;
	private HibernatePersister persister;
	private Map<String, Object> params;
	
	public final void init(ImpConfigurator conf, HibernatePersister persister, Map<String, Object> params) {
		this.conf = conf;
		this.persister = persister;
		this.params = params;
	}
	
	public abstract void start();
	
	public abstract void startRow(Map<String, String> row);
	
	public abstract <T> void endRow(Map<String, String> row, T bean);
	
	public abstract void end();

	public final ImpConfigurator getConf() {
		return conf;
	}

	public final Map<String, Object> getParams() {
		return params;
	}

	public final HibernatePersister getPersister() {
		return persister;
	}

}

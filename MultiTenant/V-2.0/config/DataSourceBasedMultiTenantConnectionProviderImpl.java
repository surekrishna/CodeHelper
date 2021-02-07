package com.krish.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
	/**
	 * added generated serialVersionUID
	 */
	private static final long serialVersionUID = 1668150795501304265L;

	@Autowired
	private DataSource defaultDS;

	@Autowired
	private ApplicationContext context;

	private Map<String, DataSource> map = new HashMap<>();

	//boolean init = false;

	@PostConstruct
	public void load() {
		map.put("krish_master", defaultDS);
	}

	@Override
	protected DataSource selectAnyDataSource() {
		return map.get("krish_master");
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
//		if (!init) {
//			init = true;
//			TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
//			map.putAll(tenantDataSource.getAll());
//		}
		map.putAll(context.getBean(TenantDataSource.class).getAll());
		if(null == map.get(tenantIdentifier))
			throw new RuntimeException("Selected Database Not Present :: " + tenantIdentifier);
		
		return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get("krish_master");
	}
}

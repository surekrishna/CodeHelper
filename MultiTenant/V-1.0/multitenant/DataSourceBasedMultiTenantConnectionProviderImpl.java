package com.krish.config.multitenant;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
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
	
	@Autowired
	private Environment environment;

	private Map<String, DataSource> map = new HashMap<>();

	@PostConstruct
	public void load() {
		map.put(environment.getProperty("krish.datasource.schema"), defaultDS);
	}

	@Override
	protected DataSource selectAnyDataSource() {
		return map.get(environment.getProperty("krish.datasource.schema"));
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		map.putAll(context.getBean(TenantDataSource.class).getAll());
		if(null == map.get(tenantIdentifier))
			throw new RuntimeException("Selected Database Not Present :: " + tenantIdentifier);
		
		return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(environment.getProperty("krish.datasource.schema"));
	}
}

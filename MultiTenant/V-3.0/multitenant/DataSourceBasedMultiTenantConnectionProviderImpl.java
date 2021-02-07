package com.krish.config.multitenant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
	/**
	 * added generated serialVersionUID
	 */
	private static final long serialVersionUID = 1668150795501304265L;
	
	@Autowired
	private Map<String, DataSource> dataSources;

	@Override
	protected DataSource selectAnyDataSource() {
		return this.dataSources.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return this.dataSources.get(tenantIdentifier);
	}
	
	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		final Connection connection = dataSources.entrySet().iterator().next().getValue().getConnection();
		try {
			connection.createStatement().execute( "USE " + tenantIdentifier );
		}catch (SQLException e ) {
			throw new HibernateException("Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
		}
		return connection;
	}
}

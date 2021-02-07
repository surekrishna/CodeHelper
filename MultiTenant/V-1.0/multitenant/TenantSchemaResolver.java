package com.krish.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

	@Autowired
	private Environment environment; 
	
    private String defaultTenant = environment.getProperty("krish.datasource.schema");

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenent =  TenantContext.getCurrentTenant();
        if(null != tenent)
        	return tenent;
        else
        	return defaultTenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}

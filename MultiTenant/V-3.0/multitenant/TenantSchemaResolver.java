package com.krish.config.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

	@Value("${krish.datasource.schema}")
    private String defaultTenant;

    @Override
    public String resolveCurrentTenantIdentifier() {
    	String tenent =  TenantContext.getCurrentTenant();
    	return null != tenent ? tenent : defaultTenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}

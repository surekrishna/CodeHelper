package com.krish.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    private String defaultTenant ="krish_master";

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

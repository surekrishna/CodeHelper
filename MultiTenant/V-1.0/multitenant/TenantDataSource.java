package com.krish.config.multitenant;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.vit.products.ctrlpay.dto.CtrlPaySchemasDto;

@Component
public class TenantDataSource implements Serializable {
    /**
	 * added generated serialVersionUID
	 */
	private static final long serialVersionUID = -1195085534259116608L;
	
	private HashMap<String, DataSource> dataSources = new HashMap<>();
	
	@Autowired
	private Environment environment;
	
	@Value("${baseDir.upload.path}")
	private String basePath;

    public DataSource getDataSource(String name) {
        if (null != dataSources.get(name))
            return dataSources.get(name);
        
        DataSource dataSource = createDataSource(name);
        if (null != dataSource)
            dataSources.put(name, dataSource);
        
        return dataSource;
    }

    @PostConstruct
    public Map<String, DataSource> getAll() {
    	CtrlPaySchemasDto schemas = null;
    	File file = new File(basePath + "/krish_schemas.xml");
    	try {
			JAXBContext jAXBContext = JAXBContext.newInstance(CtrlPaySchemasDto.class);
			Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
			schemas = (CtrlPaySchemasDto) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	
        Map<String, DataSource> result = new HashMap<>();
        for (String schema : schemas.getSchema()) {
            DataSource dataSource = getDataSource(schema);
            result.put(schema, dataSource);
        }
        return result;
    }

    private DataSource createDataSource(String schema) {
        return DataSourceBuilder
                .create().driverClassName(environment.getProperty("ctrlpay.datasource.driver-class-name"))
                .username(environment.getProperty("ctrlpay.datasource.username"))
                .password(environment.getProperty("ctrlpay.datasource.password"))
                .url(environment.getProperty("ctrlpay.datasource.url") + schema).build();
    }   

}

package com.krish.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;


@Component
public class TenantDataSource implements Serializable {
    /**
	 * added generated serialVersionUID
	 */
	private static final long serialVersionUID = -1195085534259116608L;
	
	private HashMap<String, DataSource> dataSources = new HashMap<>();

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
    	File res = new File("/home/krishnababu/Downloads/test.txt");
    	try {
			Scanner scnr = new Scanner(res);
			while(scnr.hasNextLine()){
			   String line = scnr.nextLine();
			   System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	List<String> databases = List.of("IPL1819", "IPL1920", "test1", "test", "pay_1920", "pay_2021");
        Map<String, DataSource> result = new HashMap<>();
        for (String schema : databases) {
            DataSource dataSource = getDataSource(schema);
            result.put(schema, dataSource);
        }
        return result;
    }

    private DataSource createDataSource(String schema) {
        return DataSourceBuilder
                .create().driverClassName("com.mysql.jdbc.Driver")
                .username("krish")
                .password("krish")
                .url("jdbc:mysql://127.0.0.1:3306/" + schema).build();
    }   

}

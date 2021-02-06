package com.convertjson2xml.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "file:${external.config.location}/config.properties")
public class PropertiesHelper {

    @Autowired
    private Environment environment;

    public String getProperty(String property) {
        return environment.getProperty(property);
    }

}

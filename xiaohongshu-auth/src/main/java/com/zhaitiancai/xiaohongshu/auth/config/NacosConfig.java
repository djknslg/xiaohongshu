package com.zhaitiancai.xiaohongshu.auth.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

@Configuration
public class NacosConfig {

@Value("${nacos.config.server-addr}")
private String serverAddr;

@Value("${nacos.config.data-id}")
private String namespace;

@Value("${nacos.config.group:DEFAULT_GROUP}")
private String group;

/**
 * 创建ConfigService Bean
 */
@Bean
public ConfigService configService() throws NacosException {
	Properties properties = new Properties();
	properties.setProperty("serverAddr", serverAddr);
	properties.setProperty("namespace", namespace);
	return NacosFactory.createConfigService(properties);
}

/**
 * 创建NamingService Bean
 */
@Bean
public NamingService namingService() throws NacosException {
	Properties properties = new Properties();
	properties.setProperty("serverAddr", serverAddr);
	properties.setProperty("namespace", namespace);
	return NamingFactory.createNamingService(properties);
}
}

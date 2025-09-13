package com.zhaitiancai.framework.biz.operationlog.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import com.zhaitiancai.framework.biz.operationlog.aspect.ApiOperationLogAspect;
@AutoConfiguration
public class ApiOperationLogAutoConfiguration {

    @Bean
    public ApiOperationLogAspect apiOperationLogAspect() {
        return new ApiOperationLogAspect();
    }
}
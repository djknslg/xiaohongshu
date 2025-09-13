package com.zhaitiancai.framework.jackson.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import com.zhaitiancai.framework.common.constant.DateConstants;
import com.zhaitiancai.framework.common.util.JsonUtilS;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.TimeZone;

@AutoConfiguration
public class JacksonAutoConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        // 初始化一个 ObjectMapper 对象，用于自定义 Jackson 的行为
        ObjectMapper objectMapper = new ObjectMapper();

        // 忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        // 设置凡是为 null 的字段，返参中均不返回，请根据项目组约定是否开启
        // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 设置时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        // JavaTimeModule 用于指定序列化和反序列化规则
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 支持 LocalDateTime、LocalDate、LocalTime
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateConstants.DATE_FORMAT_Y_M_D_H_M_S));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateConstants.DATE_FORMAT_Y_M_D_H_M_S));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateConstants.DATE_FORMAT_Y_M_D));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateConstants.DATE_FORMAT_Y_M_D));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateConstants.DATE_FORMAT_H_M_S));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateConstants.DATE_FORMAT_H_M_S));
        // 支持 YearMonth
        javaTimeModule.addSerializer(YearMonth.class, new YearMonthSerializer(DateConstants.DATE_FORMAT_Y_M));
        javaTimeModule.addDeserializer(YearMonth.class, new YearMonthDeserializer(DateConstants.DATE_FORMAT_Y_M));

        objectMapper.registerModule(javaTimeModule);
        //初始化 JsonUtils中的objectMapper
        JsonUtilS.init(objectMapper);
        return objectMapper;
    }
    @Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE + 10) // 比objectMapper的配置优先级更高
    static class JacksonMvcConfiguration implements WebMvcConfigurer {
    // 4. 直接注入上面定义好的ObjectMapper
    private final ObjectMapper objectMapper;

    public JacksonMvcConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 5. 先清空现有的转换器（谨慎操作，但有时必须），或者确保你的转换器在最前面
        // converters.clear(); // 如果问题严重，可以尝试先清空，但注释掉先试试不加

        // 6. 添加二进制转换器 - 这是解决序列化问题的一个关键点
        converters.add(new ByteArrayHttpMessageConverter());

        // 7. 创建并使用你的自定义ObjectMapper的转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(this.objectMapper); // 使用自定义的ObjectMapper
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setSupportedMediaTypes(List.of(
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON_UTF8 // 注意：这个在Spring Boot 2.2+后已弃用，但保留无妨
        ));
        // 8. 确保你的转换器被添加到列表里
        converters.add(converter);
    }
}
}

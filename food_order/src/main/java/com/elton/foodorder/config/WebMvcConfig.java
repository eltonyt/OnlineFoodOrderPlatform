package com.elton.foodorder.config;

import com.elton.foodorder.utils.JacksonObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

    // SETUP STATIC FILES FILTER
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("Set Static Files Path");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    // ADD JSON OBJECT MAPPER TO MESSAGE CONVERTER - THIS HELPS SOLVE THE JS CANNOT PARSE LONG DATA TO BACKEND PROPERLY ISSUE
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, messageConverter);
    }
}

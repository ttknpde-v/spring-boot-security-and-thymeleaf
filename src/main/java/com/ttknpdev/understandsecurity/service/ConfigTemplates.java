package com.ttknpdev.understandsecurity.service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
/*
    this class for setting new path of html file
    By defaults. Spring boot will auto configure view resolver for Thymeleaf whenever it will find the springboot-thymeleaf-starter
    dependency on the classpath. Spring boot will pick Thymeleaf templates (HTML pages) from resources/templates folder.
*/
@Configuration
public class ConfigTemplates{
        @Bean
        public ClassLoaderTemplateResolver yourTemplateResolver() {
            ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
            yourTemplateResolver.setPrefix("ui/");
            yourTemplateResolver.setSuffix(".html");
            yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
            yourTemplateResolver.setCharacterEncoding("UTF-8");
            yourTemplateResolver.setOrder(0);  // this is important. This way spring boot will listen to both places 0 and 1
            yourTemplateResolver.setCheckExistence(true);
            return yourTemplateResolver;
        }
}



// package com.neo.config;
//
// import org.springframework.boot.web.servlet.MultipartConfigFactory;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import javax.servlet.MultipartConfigElement;
//
// /**
//  * spring-boot-shiro-FileUploadConfiguration
//  * 文件上传配置类
//  *
//  * @author yh
//  * @since 0.0.1 2017-09-09 17:17
//  */
//
// @Configuration
// public class FileUploadConfiguration {
//
//     @Bean
//     public MultipartConfigElement multipartConfigElement() {
//         MultipartConfigFactory factory = new MultipartConfigFactory();
//         factory.setMaxFileSize("100MB");
//         factory.setMaxRequestSize("101MB");
//         // factory.setLocation("Y:\\101_boring");
//         return factory.createMultipartConfig();
//     }
// }

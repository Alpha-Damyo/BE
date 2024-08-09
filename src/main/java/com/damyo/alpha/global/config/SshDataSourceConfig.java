//package com.damyo.alpha.global.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//@RequiredArgsConstructor
//public class SshDataSourceConfig {
//    private final SshTunnelingInitializer initializer;
//
//    @Bean("dataSource")
//    @Primary
//    public DataSource dataSource(DataSourceProperties properties) {
//        Integer forwardedPort = initializer.buildSshConnection();
//        String url = properties.getUrl().replace("[forwardedPort]", String.valueOf(forwardedPort));
//        return DataSourceBuilder.create()
//                .url(url)
//                .username(properties.getUsername())
//                .password(properties.getPassword())
//                .driverClassName(properties.getDriverClassName())
//                .build();
//    }
//}

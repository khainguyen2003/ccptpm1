//package com.khai.admin.config;
//
//import org.springframework.cglib.proxy.MethodInterceptor;
//
//import javax.sql.DataSource;
//
///**
// * Config insert/update in batch
// */
//private static class ProxyDataSourceInterceptor implements MethodInterceptor {
//    private final DataSource dataSource;
//    public ProxyDataSourceInterceptor(final DataSource dataSource) {
//        this.dataSource = ProxyDataSourceBuilder.create(dataSource)
//                .name("Batch-Insert-Logger")
//                .asJson().countQuery().logQueryToSysOut().build();
//    }
//
//    // Other methods...
//}
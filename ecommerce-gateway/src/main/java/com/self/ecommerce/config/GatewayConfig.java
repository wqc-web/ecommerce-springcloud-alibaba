package com.self.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;

/**
 * 读取nacos相关配置项，用于配置监听器
 *
 * @author wangqichao
 */
@Configuration
public class GatewayConfig {

    /**
     * 读取配置的超时时间
     */
    public static long DEFAULT_TIMEOUT = 30000;

    /**
     * 服务地址
     */
    public static String NACOS_SERVER_ADDR;

    /**
     * 命名空间
     */
    public static String NACOS_NAMESPACE;

    /**
     * data-id
     */
    public static String NACOS_ROUTE_DATA_ID;

    /**
     * 分组
     */
    public static String NACOS_ROUTE_GROUP;


    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public void setNacosServerAddr(String nacosServerAddr) {
        NACOS_SERVER_ADDR = nacosServerAddr;
    }

    @Value("${spring.cloud.nacos.discovery.namespace}")
    public void setNacosNamespace(String nacosNamespace) {
        NACOS_NAMESPACE = nacosNamespace;
    }

    @Value("${spring.gateway.route.config.data-id}")
    public void setNacosRouteDataId(String routeDataId) {
        NACOS_ROUTE_DATA_ID = routeDataId;
    }

    @Value("${spring.gateway.route.config.group}")
    public void setNacosRouteGroup(String nacosRouteGroup) {
        NACOS_ROUTE_GROUP = nacosRouteGroup;
    }

}

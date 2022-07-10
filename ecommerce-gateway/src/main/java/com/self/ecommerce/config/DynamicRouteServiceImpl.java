package com.self.ecommerce.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 事件推送 Aware: 动态更新路由网关 Service
 *
 * @author wangqichao
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    /**
     * 写路由定义
     */
    RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 获取路由定义
     */
    RouteDefinitionLocator routeDefinitionLocator;

    /**
     * 事件发布
     */
    ApplicationEventPublisher publisher;

    public DynamicRouteServiceImpl(RouteDefinitionWriter writer,
                                   RouteDefinitionLocator locator) {
        this.routeDefinitionWriter = writer;
        this.routeDefinitionLocator = locator;
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        // 完成事件推送句柄的初始化
        this.publisher = applicationEventPublisher;

    }

    /**
     * 增加路由定义
     */
    public String addRouteDefinition(RouteDefinition definition) {
        log.info("gateway add route : [{}]", definition);
        // 保存路由配置并发布
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        // 发布事件通知给gateway，同步新增路由定义
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    /**
     * 更新路由-集合
     */
    private String updateList(List<RouteDefinition> definitions) {
        log.info("gateway update route : [{}]", definitions);
        // 先拿到gateway中存储的路由定义
        List<RouteDefinition> routeDefinitionExist =
                routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        // 清除之前所有旧的路由定义
        if (CollectionUtils.isNotEmpty(routeDefinitionExist)) {
            routeDefinitionExist.forEach(rd -> {
                log.info("delete route definition : [{}]", rd);
                deleteById(rd.getId());
            });
        }
        // 把更新的路由定义同步到gateway中
        definitions.forEach(definition -> updateByRouteDefinition(definition));
        return "success";
    }


    /**
     * 根据路由id删除 路由配置
     */
    private String deleteById(String id) {
        try {
            log.info("gateway delete route id : [{}]", id);
            routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            // 发布事件通知给gateway，同步新增路由定义
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            log.error("gateway delete route fail : [{}]", e.getMessage(), e);
            return "delete fail";
        }
    }

    /**
     * 更新路由：删除 + 新增
     */
    private String updateByRouteDefinition(RouteDefinition definition) {
        try {
            log.info("gateway update route : [{}]", definition);
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail , not find routeId : " + definition.getId();
        }

        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            // 发布事件通知给gateway，同步新增路由定义
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route fail";
        }
    }

}

package com.self.ecommerce.notifier;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author wangqichao
 * 自定义告警
 */
@Slf4j
@Component
public class SelfNotifier extends AbstractEventNotifier {
    protected SelfNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(()->{
            if(event instanceof InstanceStatusChangedEvent){
                log.info("instance status change: [{}], [{}] , [{}]",
                        instance.getRegistration().getName(),event.getInstance(),
                        ((InstanceStatusChangedEvent)event).getStatusInfo().getStatus());
            }else{
                log.info("instance info: [{}] , [{}] , [{}]",
                        instance.getRegistration().getName(),event.getInstance(),event.getType());
            }
        });
    }
}

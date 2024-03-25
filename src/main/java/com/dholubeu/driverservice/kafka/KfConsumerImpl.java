package com.dholubeu.driverservice.kafka;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;

@Slf4j
@Component
@RequiredArgsConstructor
public class KfConsumerImpl implements KfConsumer{

    private final KafkaReceiver<String, Message> kafkaReceiver;

    @Override
    @PostConstruct
    public void fetch() {
        this.kafkaReceiver.receive()
                //TODO add consumers groups
                .subscribe(r -> {
                    log.info("New ride id: {}, longitude: {}, latitude: {}",
                            r.value().getRideId(),
                            r.value().getLongitude(),
                            r.value().getLatitude());
                    r.receiverOffset().acknowledge();
                });
    }

}
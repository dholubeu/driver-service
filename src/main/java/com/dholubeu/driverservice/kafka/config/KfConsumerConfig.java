package com.dholubeu.driverservice.kafka.config;

import com.dholubeu.driverservice.kafka.Message;
import com.dholubeu.driverservice.kafka.property.KfProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KfConsumerConfig {

    private final KfProperties kfProperties;

    @Bean
    public ReceiverOptions<String, Message> receiverOptions() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.dholubeuu.driverservice.kafka.Message");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kfProperties.getPort());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kfProperties.getGroup());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        ReceiverOptions<String, Message> receiverOptions = ReceiverOptions.create(props);
        return receiverOptions.subscription(Collections.singleton(kfProperties.getTopic()))
                .addAssignListener(partitions -> log.info("onAssigned: " + partitions))
                .addRevokeListener(partitions -> log.info("onRevoked: " + partitions));
    }

    @Bean
    public KafkaReceiver<String, Message> kafkaReceiver() {
        return KafkaReceiver.create(receiverOptions());
    }

}
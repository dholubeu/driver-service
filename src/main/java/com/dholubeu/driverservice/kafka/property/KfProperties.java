package com.dholubeu.driverservice.kafka.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "kafka")
public class KfProperties {

    private final String port;
    private final String group;
    private final String topic;

}
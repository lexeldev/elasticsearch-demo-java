package com.lexel.elasticsearchdemojava.basicapp.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "es.config")
@Data
public class ESParams {
    String serverUrl;
    String apiKey;
}

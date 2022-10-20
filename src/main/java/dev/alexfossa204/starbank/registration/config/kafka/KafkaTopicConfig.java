package dev.alexfossa204.starbank.registration.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

import static dev.alexfossa204.starbank.registration.config.kafka.KafkaConstant.*;
import static dev.alexfossa204.starbank.registration.config.kafka.KafkaConstant.SET_VERIFICATION_CODE_AS_USED_TOPIC;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> adminProperties = Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress
        );
        return new KafkaAdmin(adminProperties);
    }

    @Bean
    public NewTopic generateVerificationCodeTopic() {
        return new NewTopic(GENERATE_VERIFICATION_CODE_TOPIC, DEFAULT_PARTITION_FACTOR, DEFAULT_REPLICATION_FACTOR);
    }

    @Bean
    public NewTopic setVerificationCodeAsUsedTopic() {
        return new NewTopic(SET_VERIFICATION_CODE_AS_USED_TOPIC, DEFAULT_PARTITION_FACTOR, DEFAULT_REPLICATION_FACTOR);
    }

}

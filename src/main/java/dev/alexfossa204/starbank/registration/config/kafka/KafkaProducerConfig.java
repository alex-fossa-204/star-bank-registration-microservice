package dev.alexfossa204.starbank.registration.config.kafka;

import dev.alexfossa204.starbank.registration.service.dto.broker.VerificationCodeSetAsUsedTopicMessage;
import dev.alexfossa204.starbank.registration.service.dto.broker.VerificationCodeGenerationTopicMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    private Map<String, Object> jsonConfigProps() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, VerificationCodeGenerationTopicMessage> verificationCodeGenerationTopicMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(jsonConfigProps());
    }

    @Bean
    public KafkaTemplate<String, VerificationCodeGenerationTopicMessage> verificationCodeGenerationKafkaTemplate() {
        KafkaTemplate<String, VerificationCodeGenerationTopicMessage> kafkaTemplate = new KafkaTemplate<>(verificationCodeGenerationTopicMessageProducerFactory());
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        return kafkaTemplate;
    }

    @Bean
    public ProducerFactory<String, VerificationCodeSetAsUsedTopicMessage> verificationCodeSetAsUsedTopicMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(jsonConfigProps());
    }

    @Bean
    public KafkaTemplate<String, VerificationCodeSetAsUsedTopicMessage> verificationCodeSetAsUsedKafkaTemplate() {
        KafkaTemplate<String, VerificationCodeSetAsUsedTopicMessage> kafkaTemplate = new KafkaTemplate<>(verificationCodeSetAsUsedTopicMessageProducerFactory());
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        return kafkaTemplate;
    }


}

package dev.alexfossa204.starbank.microservice.service;

import dev.alexfossa204.starbank.microservice.service.dto.broker.VerificationCodeSetAsUsedTopicMessage;
import dev.alexfossa204.starbank.microservice.service.dto.broker.VerificationCodeGenerationTopicMessage;

public interface MessageBrokerTopicProducerService {

    void publishVerificationCodeGenerationTopicEvent(VerificationCodeGenerationTopicMessage topicMessage);

    void publishVerificationCodeSetAsUsedTopicEvent(VerificationCodeSetAsUsedTopicMessage topicMessage);

}

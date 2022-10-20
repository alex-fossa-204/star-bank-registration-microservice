package dev.alexfossa204.starbank.registration.service;

import dev.alexfossa204.starbank.registration.service.dto.broker.VerificationCodeSetAsUsedTopicMessage;
import dev.alexfossa204.starbank.registration.service.dto.broker.VerificationCodeGenerationTopicMessage;

public interface MessageBrokerTopicProducerService {

    void publishVerificationCodeGenerationTopicEvent(VerificationCodeGenerationTopicMessage topicMessage);

    void publishVerificationCodeSetAsUsedTopicEvent(VerificationCodeSetAsUsedTopicMessage topicMessage);

}

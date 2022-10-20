package dev.alexfossa204.starbank.registration.service.impl.broker;

import dev.alexfossa204.starbank.registration.service.dto.broker.VerificationCodeSetAsUsedTopicMessage;
import dev.alexfossa204.starbank.registration.service.dto.broker.VerificationCodeGenerationTopicMessage;
import dev.alexfossa204.starbank.registration.service.MessageBrokerTopicProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static dev.alexfossa204.starbank.registration.config.kafka.KafkaConstant.*;

@Service
@RequiredArgsConstructor
public class KafkaTopicProducerService implements MessageBrokerTopicProducerService {

    private final KafkaTemplate<String, VerificationCodeGenerationTopicMessage> verificationCodeGenerationTopicMessageKafkaTemplate;

    private final KafkaTemplate<String, VerificationCodeSetAsUsedTopicMessage> verificationCodeSetAsUsedKafkaTemplate;

    @Override
    @Async
    public void publishVerificationCodeGenerationTopicEvent(VerificationCodeGenerationTopicMessage topicMessage) {
        verificationCodeGenerationTopicMessageKafkaTemplate.send(GENERATE_VERIFICATION_CODE_TOPIC, topicMessage);
    }

    @Override
    @Async
    public void publishVerificationCodeSetAsUsedTopicEvent(VerificationCodeSetAsUsedTopicMessage topicMessage) {
        verificationCodeSetAsUsedKafkaTemplate.send(SET_VERIFICATION_CODE_AS_USED_TOPIC, topicMessage);
    }

}

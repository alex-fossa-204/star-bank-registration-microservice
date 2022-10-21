package dev.alexfossa204.starbank.microservice.utils.uid;

import java.util.UUID;

public class LongUidGenerator {

    public static Long generateRandomTransferUid() {
        return UUID.randomUUID().getMostSignificantBits() >>> 16;
    }

}

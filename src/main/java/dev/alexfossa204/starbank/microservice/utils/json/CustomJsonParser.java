package dev.alexfossa204.starbank.microservice.utils.json;

public interface CustomJsonParser<T> {

    T parseJsonString(String jsonString);

}

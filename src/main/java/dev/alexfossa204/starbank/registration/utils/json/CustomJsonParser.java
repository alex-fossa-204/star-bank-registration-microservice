package dev.alexfossa204.starbank.registration.utils.json;

public interface CustomJsonParser<T> {

    T parseJsonString(String jsonString);

}

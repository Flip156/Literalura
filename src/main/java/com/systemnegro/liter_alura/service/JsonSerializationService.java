package com.systemnegro.liter_alura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializationService {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T fromJson(String json, Class<T> clazz){
        try {
            return mapper.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

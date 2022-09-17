package org.andersenTask.controller.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper<T> {
    public String EntityToJson(T entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String employeeToJson = null;
        try {
             employeeToJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return employeeToJson;
    }

}


package org.andersenTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.andersenTask.entity.Employee;
import org.andersenTask.service.EmployeeService;

public class main {
    public static void main(String[] args) throws JsonProcessingException {
        Employee byId = new EmployeeService().getById(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String s = objectMapper.writeValueAsString(byId);
        Employee employee = objectMapper.readValue(s,Employee.class);
        System.out.println(employee.toString());
    }
}

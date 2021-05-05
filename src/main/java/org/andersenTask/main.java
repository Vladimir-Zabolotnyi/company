package org.andersenTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.andersenTask.entity.Employee;
import org.andersenTask.entity.Team;
import org.andersenTask.service.EmployeeService;
import org.andersenTask.service.TeamService;

import java.util.List;

public class main {
    public static void main(String[] args) throws JsonProcessingException {
       Team team = new TeamService().getById(1l);
        System.out.println(team);
    }
}

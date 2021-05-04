package org.andersenTask.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Team {
    private Long id;
    private String name;
    private List<Employee> employeeList;

    @Override
    public String toString() {
        String teamEmployeeList;
        if (employeeList == null) {
            teamEmployeeList = "";
        } else {
            teamEmployeeList = ", employeeList=" + employeeList;
        }
        String teamToSting = "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                teamEmployeeList +
                '}';
        return teamToSting;
    }
}

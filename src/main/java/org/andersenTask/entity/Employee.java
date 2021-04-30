package org.andersenTask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.andersenTask.entity.utils.DeveloperLevel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor()
@NoArgsConstructor
public class Employee {
    private Long id;
    private String name;
    private String surname;
    private String fatherName;
    private String email;
    private String phoneNumber;
    private String experience;
    private LocalDate dateOfBirth;
    private LocalDate dateOfRecruitment;
    private Project currentProject;
    private DeveloperLevel developerLevel;
    private String levelOfEnglish;
    private String skype;
    private Feedback feedback;


}

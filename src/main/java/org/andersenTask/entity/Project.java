package org.andersenTask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private Long id;
    private String title;
    private String client;
    private String duration;
    private String methodology;
    private String projectManager;
    private Team team;
}

package org.andersenTask.entity;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    private Long id;
    private String description;
    private LocalDate date;
}

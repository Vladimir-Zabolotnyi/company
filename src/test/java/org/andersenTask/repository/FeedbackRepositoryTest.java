package org.andersenTask.repository;

import org.andersenTask.entity.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackRepositoryTest {
    Repository<Feedback> feedbackRepository;
    Feedback feedback;
    Feedback feedback1;
    List<Feedback> feedbackList;

    @BeforeEach
    void setUp() {
        feedbackRepository = new FeedbackRepository();

        feedback = new Feedback();

        feedback.setId(1L);
        feedback.setDescription("desc1");
        feedback.setDate(LocalDate.of(2021, 02, 02));
        feedback1 = new Feedback();

        feedback1.setId(2L);
        feedback1.setDescription("desc2");
        feedback1.setDate(LocalDate.of(2021, 03, 02));

        feedbackList = new ArrayList<>();
        feedbackList.add(feedback);
        feedbackList.add(feedback1);
    }

    @Test
    @Order(1)
    void insert() {
        assertEquals(1, feedbackRepository.insert(feedback));
    }

    @Test
    @Order(2)
    void getById() {
        assertEquals(feedback, feedbackRepository.getById(1L));
    }

    @Test
    @Order(3)
    void getAll() {
        assertEquals(feedbackList, feedbackRepository.getAll());
    }

    @Test
    @Order(5)
    void deleteById() {
        assertEquals(1, feedbackRepository.deleteById(1L));
    }


    @Test
    @Order(4)
    void update() {
        Feedback feedback3 = new Feedback();
        feedback3.setId(2L);
        feedback3.setDescription("desc3");
        feedback3.setDate(LocalDate.of(2021, 03, 15));
        assertEquals(1, feedbackRepository.update(feedback3));
    }
}
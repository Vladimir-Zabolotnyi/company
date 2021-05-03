package org.andersenTask.service;

import org.andersenTask.entity.Feedback;
import org.andersenTask.repository.FeedbackRepository;

import java.util.List;

public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    public FeedbackService() {
        this.feedbackRepository = new FeedbackRepository();
    }

    public void create(Feedback feedback) {
        feedbackRepository.insert(feedback);
    }

    public Feedback getById(Long id) {
        return feedbackRepository.getById(id);
    }

    public List<Feedback> getAll() {
        return feedbackRepository.getAll();
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    public void updateById(Feedback feedback) {
        feedbackRepository.update(feedback);
    }
}

package org.andersenTask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.andersenTask.controller.mapper.JsonMapper;
import org.andersenTask.entity.Feedback;
import org.andersenTask.service.FeedbackService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        JsonMapper<Feedback> jsonMapper = new JsonMapper();
        if (req.getParameter("id") == null) {
            List<Feedback> feedbackList = new FeedbackService().getAll();
            printWriter.write("<html><body>");
            printWriter.write("<ol>");
            for (Feedback feedback : feedbackList) {
                String feedbackToJson =  jsonMapper.EntityToJson(feedback);
                printWriter.write("<li>");
                printWriter.write(feedbackToJson);
                printWriter.write("</li>");
                printWriter.write("<br>");
            }
            printWriter.write("</ol>");
            printWriter.write("</body></html>");
        } else {
            Feedback feedback = new FeedbackService().getById(Long.valueOf(req.getParameter("id")));
            String feedbackToJson =  jsonMapper.EntityToJson(feedback);
            printWriter.write(feedbackToJson);
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        PrintWriter printWriter = resp.getWriter();
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        Feedback feedback = objectMapper.readValue(stringBuilder.toString(),Feedback.class);
        new FeedbackService().create(feedback);
        printWriter.write("New feedback is created");
        printWriter.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        new FeedbackService().deleteById(id);
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Feedback with id= " + id + " is deleted");
        printWriter.close();
    }
}

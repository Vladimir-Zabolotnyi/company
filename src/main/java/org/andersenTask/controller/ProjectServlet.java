package org.andersenTask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.andersenTask.controller.mapper.JsonMapper;
import org.andersenTask.entity.Employee;
import org.andersenTask.entity.Project;
import org.andersenTask.service.EmployeeService;
import org.andersenTask.service.ProjectService;
import org.andersenTask.service.TeamService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/project")
public class ProjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        JsonMapper<Project> jsonMapper = new JsonMapper();
        if (req.getParameter("id") == null) {
            List<Project> projectList = new ProjectService().getAll();
            printWriter.write("<html><body>");
            printWriter.write("<ol>");
            for (Project project : projectList) {
                String projectToJson =  jsonMapper.EntityToJson(project);
                printWriter.write("<li>");
                printWriter.write(projectToJson);
                printWriter.write("</li>");
                printWriter.write("<br>");
            }
            printWriter.write("</ol>");
            printWriter.write("</body></html>");
        } else {
            Project project = new ProjectService().getById(Long.valueOf(req.getParameter("id")));
            String projectToJson =  jsonMapper.EntityToJson(project);
            printWriter.write(projectToJson);
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
        Project project = objectMapper.readValue(stringBuilder.toString(),Project.class);
        new ProjectService().create(project);
        printWriter.write("New project  is created");
        printWriter.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        new ProjectService().deleteById(id);
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Project with id= " + id + " is deleted");
        printWriter.close();
    }
}

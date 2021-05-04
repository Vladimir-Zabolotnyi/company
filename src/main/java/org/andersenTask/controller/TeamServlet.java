package org.andersenTask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.andersenTask.controller.mapper.JsonMapper;
import org.andersenTask.entity.Team;
import org.andersenTask.service.EmployeeService;
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

@WebServlet("/team")
public class TeamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        JsonMapper<Team> jsonMapper = new JsonMapper();
        if (req.getParameter("id") == null) {
            List<Team> teamList = new TeamService().getAll();
            printWriter.write("<html><body>");
            printWriter.write("<ol>");
            for (Team team : teamList) {
                String teamToJson =  jsonMapper.EntityToJson(team);
                printWriter.write("<li>");
                printWriter.write(teamToJson);
                printWriter.write("</li>");
                printWriter.write("<br>");
            }
            printWriter.write("</ol>");
            printWriter.write("</body></html>");
        } else {
            Team team = new TeamService().getById(Long.valueOf(req.getParameter("id")));
            String teamToJson =  jsonMapper.EntityToJson(team);
            printWriter.write(teamToJson);
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
        Team team = objectMapper.readValue(stringBuilder.toString(),Team.class);
        new TeamService().create(team);
        printWriter.write("New team  is created");
        printWriter.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        new EmployeeService().deleteById(id);
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Employee with id= " + id + " is deleted");
        printWriter.close();
    }
}

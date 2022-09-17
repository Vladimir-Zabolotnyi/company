package org.andersenTask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.andersenTask.controller.mapper.JsonMapper;
import org.andersenTask.entity.Employee;
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

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        JsonMapper<Employee> jsonMapper = new JsonMapper();
        if (req.getParameter("id") == null) {
            List<Employee> employeeList = new EmployeeService().getAll();
            printWriter.write("<html><body>");
            printWriter.write("<ol>");
            for (Employee employee : employeeList) {
                String employeeToJson =  jsonMapper.EntityToJson(employee);
                printWriter.write("<li>");
                printWriter.write(employeeToJson);
                printWriter.write("</li>");
                printWriter.write("<br>");
            }
            printWriter.write("</ol>");
            printWriter.write("</body></html>");
        } else {
            Employee employee = new EmployeeService().getById(Long.valueOf(req.getParameter("id")));
            String employeeToJson =  jsonMapper.EntityToJson(employee);
            printWriter.write(employeeToJson);
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
        Employee employee = objectMapper.readValue(stringBuilder.toString(),Employee.class);
        new EmployeeService().create(employee);
        printWriter.write("New employee  is created");
        printWriter.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        PrintWriter printWriter = resp.getWriter();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        Employee employee = objectMapper.readValue(stringBuilder.toString(),Employee.class);
        new EmployeeService().updateById(employee);
        printWriter.write("Employee with id= " + employee.getId() + " is updated");
        printWriter.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        new TeamService().deleteById(id);
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Team with id= " + id + " is deleted");
        printWriter.close();
    }
}

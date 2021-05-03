package org.andersenTask;

import org.andersenTask.service.EmployeeService;

public class main {
    public static void main(String[] args) {
        new EmployeeService().deleteById(1L);
    }
}

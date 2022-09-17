package org.andersenTask.connection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionPoolImplTest {

    ConnectionPool connectionPool;


    @Test
    void getConnection() {
        try {
            connectionPool = ConnectionPoolImpl.create("jdbc:postgresql://" + "127.0.0.1:5432" + "/" + "company", "postgres", "1234");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            assertTrue(connectionPool.getConnection().isValid(1));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
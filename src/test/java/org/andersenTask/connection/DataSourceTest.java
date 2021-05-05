package org.andersenTask.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceTest {
    @Test
    void getConnection() throws SQLException {
        Connection connection = DataSource.getConnection();
        assertEquals(true,connection.isValid(0));
    }
}
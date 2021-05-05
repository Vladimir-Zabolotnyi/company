package org.andersenTask.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config= new HikariConfig();
        private static HikariDataSource ds;

        static {
            config.setJdbcUrl( "jdbc:postgresql://localhost:5432/company" );
            config.setUsername( "postgres" );
            config.setPassword( "1234" );
            config.setMinimumIdle(10);
            config.setMaximumPoolSize(100);
            config.setDriverClassName("org.postgresql.Driver");
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
            ds = new HikariDataSource( config );
        }

        private DataSource() {}

        public static Connection getConnection() throws SQLException {
            return ds.getConnection();
        }
    }


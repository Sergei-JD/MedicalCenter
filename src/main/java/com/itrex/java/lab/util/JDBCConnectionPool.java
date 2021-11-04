package com.itrex.java.lab.util;

import org.h2.jdbcx.JdbcConnectionPool;

import static com.itrex.java.lab.properties.Properties.*;

public enum JDBCConnectionPool {
    INSTANCE;

    private JdbcConnectionPool connectionPool;

    JDBCConnectionPool() {
        this.connectionPool = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);
    }

    public JdbcConnectionPool getConnectionPool() {
        return connectionPool;
    }
}

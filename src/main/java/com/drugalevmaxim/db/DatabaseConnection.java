package com.drugalevmaxim.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = System.getenv("DATABASE_URL") + "/" + System.getenv("DATABASE_NAME") + "?rewriteBatchedStatements=true";
    private static final String USER =  System.getenv("DATABASE_USERNAME");
    private static final String PASSWORD =  System.getenv("DATABASE_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void generateTable() throws SQLException {
        String sql = """
                CREATE TABLE `employees` (
                  `fullName` varchar(96) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                  `birthdate` date NOT NULL,
                  `gender` enum('m','f') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                  KEY `employees_fullnameandbirthday_idx` (`fullName`,`birthdate`) USING BTREE,
                  FULLTEXT KEY `employees_fullName_IDX` (`fullName`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;""";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.createStatement().executeUpdate(sql);
    }
}

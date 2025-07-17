package com.programm.dbService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBService {

    private static final String DB_URL = "jdbc:sqlite:identifier.sqlite";

    public static void saveMessage(Long userId, String message) {
        String sql = "INSERT INTO messages (text, userId) VALUES (?, ?)";

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, message);
            pstmt.setLong(2, userId);
            pstmt.executeUpdate();

            System.out.println("Message saved to DB");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package com.utoopproject.app;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.codec.binary.Hex;


public class UserSystem {

    public static Connection getDatabaseConnection() throws Exception {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:file:./database");
    }

    public static boolean userExists(String username) throws Exception {
        Connection conn = getDatabaseConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT username FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } finally {
            conn.close();
        }
    }

    public static void loginUser(String username, String password) throws Exception {
        Connection conn = getDatabaseConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            if (hashPassword(password).equals(rs.getString("password"))) {
                System.out.println("You have successfully logged in!");
            } else {
                // TODO After a failed attempt, ask for the password again
                throw new RuntimeException("Wrong password!");
            }

        } finally {
            conn.close();
        }

    }

    public static void registerUser(String username, String password) throws Exception {
        Connection conn = getDatabaseConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, hashPassword(password));
            stmt.executeUpdate();

            System.out.println("Welcome! Your user has been registered.");

        } finally {
            conn.close();
        }

    }

    // MD5 is not secure, don't use in public applications
    public static String hashPassword(String plainPassword) throws Exception {
        byte[] bytesOfMessage = plainPassword.getBytes("UTF-8");

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theDigest = md.digest(bytesOfMessage);

        String hexString = new String(Hex.encodeHex(theDigest));
        return hexString;
    }
}

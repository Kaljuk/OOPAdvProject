package com.utoopproject.app;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.codec.binary.Hex;


public class UserSystem {
    Connection conn;

    public UserSystem(Connection databaseConnection) {
        this.conn = databaseConnection;
    }

    public boolean userExists(String username) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT username FROM users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        return rs.next();
    }

    public boolean loginUser(String username, String password) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        if (hashPassword(password).equals(rs.getString("password"))) {
            return true;
        } else {
            // TODO After a failed attempt, ask for the password again
            return false;
        }


    }

    public boolean registerUser(String username, String password) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, hashPassword(password));
        stmt.executeUpdate();
        return true;
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

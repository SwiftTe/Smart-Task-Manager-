package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import src.database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthController {
    @FXML
    private TextField usernameField, regUsernameField;

    @FXML
    private PasswordField passwordField, regPasswordField;

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter both username and password.");
            return;
        }

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password); // In real projects, compare with hashed password!
            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                showAlert(Alert.AlertType.INFORMATION, "Login successful!");
                // TODO: Load main application window
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error during login.");
        }
    }

    public void handleRegister() {
        String username = regUsernameField.getText();
        String password = regPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all registration fields.");
            return;
        }

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users(username, password) VALUES(?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, password); // In real projects, encrypt passwords!
            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "User registered successfully! Please log in.");
            backToLogin();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error during registration. Username may already exist.");
        }
    }

    public void openRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Register.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to load Register screen.");
        }
    }

    public void backToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) regUsernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to load Login screen.");
        }
    }
}
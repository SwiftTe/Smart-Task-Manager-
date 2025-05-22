package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import src.database.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    // ✅ Updated handleLogin
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill all fields.");
            return;
        }

        try (Connection conn = DBConnector.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password); // ⚠️ Note: Use hashing in real-world apps
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                openDashboard(username);
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid credentials. Try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Something went wrong.");
        }
    }

    // ✅ New openDashboard method
    private void openDashboard(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));
            Scene scene = new Scene(loader.load());

            // Inject username to dashboard
            src.controller.DashboardController controller = loader.getController();
            controller.setUsername(username);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to load Dashboard.");
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
            stmt.setString(2, password); // ⚠️ Hash passwords in production!
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

package src.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void handleLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        System.out.println("Logging in with: " + user + " / " + pass);
        // TODO: Verify with database
    }

    public void openRegister() {
        System.out.println("Redirecting to register...");
        // TODO: Load Register.fxml
    }
}

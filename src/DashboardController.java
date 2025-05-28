package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.model.Task;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Task> taskTable;
    @FXML private TableColumn<Task, String> titleColumn;
    @FXML private TableColumn<Task, String> descColumn;
    @FXML private TableColumn<Task, String> dueDateColumn;

    private String username;
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    // Called after FXML is loaded
    public void initialize() {
        // Set up table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        // Link the task list to the table
        taskTable.setItems(tasks);
    }

    // Set the username and load tasks
    public void setUsername(String username) {
        this.username = username;
        welcomeLabel.setText("Welcome, " + username + "!");
        loadTasks();
    }

    // Load tasks (will later load from DB)
    private void loadTasks() {
        tasks.clear();

        // Sample data (replace with database load)
        tasks.add(new Task("Test Task", "Complete the UI design", "2025-05-25"));
        tasks.add(new Task("Demo", "Write controller logic", "2025-05-30"));
    }

    // Handler for Add Task button
    public void handleAddTask() {
        System.out.println("Add task clicked");
        // TODO: Open add task dialog
    }

    // Handler for Edit Task button
    public void handleEditTask() {
        System.out.println("Edit task clicked");
        // TODO: Open edit task dialog
    }

    // Handler for Delete Task button
    public void handleDeleteTask() {
        System.out.println("Delete task clicked");
        // TODO: Remove selected task from DB and list
    }
}

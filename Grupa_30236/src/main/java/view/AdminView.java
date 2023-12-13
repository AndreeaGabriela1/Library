package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Role;
import model.User;
import model.builder.UserBuilder;

import java.util.ArrayList;
import java.util.List;

public class AdminView {

    private Button addButton;
    private Button searchButton;
    private Button updateButton;
    private Button deleteButton;
    private Button fireButton;
    private TableView<User> userTableView;
    private TextField idField;
    private TextField emailField;
    private TextField passwordField;
    private ComboBox<String> userTypeComboBox;


    public AdminView(Stage primaryStage) {
        primaryStage.setTitle("Admin Interface");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);

        initializeSceneTitle(gridPane);

        userTableView = new TableView<>();
        initializeUserTableView(gridPane);

        initializeButtons(gridPane);
        idField = new TextField();
        idField.setPromptText("Enter User ID");
        gridPane.add(idField, 0, 9);

        emailField = new TextField();
        emailField.setPromptText("Enter Email");
        gridPane.add(emailField, 1, 9);

        passwordField = new TextField();
        passwordField.setPromptText("Enter Password");
        gridPane.add(passwordField, 2, 9);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        userTypeComboBox = new ComboBox<>();
        userTypeComboBox.getItems().addAll("Employee", "Customer");
        userTypeComboBox.setValue("Employee"); // Valoarea implicită
        gridPane.add(userTypeComboBox, 3, 9); // Adaugare ComboBox în gridPane
    }

    private void initializeSceneTitle(GridPane gridPane){
        Text sceneTitle = new Text("Administrator Dashboard");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
    }

    private void initializeUserTableView(GridPane gridPane) {
        TableColumn<User, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTableView.getColumns().addAll(idColumn, usernameColumn);
        gridPane.add(userTableView, 0, 7, 5, 1);
    }

    private void initializeButtons(GridPane gridPane) {
        addButton = new Button("Add User");
        searchButton = new Button("Search User");
        updateButton = new Button("Update User");
        deleteButton = new Button("Delete User");
        fireButton = new Button("Generate Report");

        gridPane.add(addButton, 0, 8);
        gridPane.add(searchButton, 1, 8);
        gridPane.add(updateButton, 2, 8);
        gridPane.add(deleteButton, 3, 8);
        gridPane.add(fireButton, 4, 8);
    }

    public void displayUsers(List<User> users) {
        userTableView.getItems().clear();
        ObservableList<User> observableList = FXCollections.observableArrayList(users);
        userTableView.setItems(observableList);
    }

    public long getId()
    {
        return Long.parseLong(idField.getText());
    }
    public String getUsername()
    {
        return emailField.getText();
    }
    public String getPassword()
    {
        return passwordField.getText();
    }
    public String getUserType() {
        return userTypeComboBox.getValue();
    }
    public void addAddUserListener(EventHandler<ActionEvent> handler) {
        addButton.setOnAction(handler);
    }

    public void addSearchButtonListener(EventHandler<ActionEvent> handler) {
        searchButton.setOnAction(handler);
    }

    public void addUpdateUserListener(EventHandler<ActionEvent> handler) {
        updateButton.setOnAction(handler);
    }

    public void addDeleteUserListener(EventHandler<ActionEvent> handler) {
        deleteButton.setOnAction(handler);
    }

    public void addGenerateButtonListener(EventHandler<ActionEvent> handler) {
        fireButton.setOnAction(handler);
    }

    public User getUserDataFromFields() {
        long id = Long.parseLong(idField.getText()); // Conversia textului din câmp într-un număr
        String username = emailField.getText(); // Adresa de email poate fi considerată ca și username
        String password = passwordField.getText();
        String role = userTypeComboBox.getValue();
        List<Role> roles = new ArrayList<>();
        if (role.equals("Employee")) {
            Role employeeRole = new Role(1L, "Employee", null); // Aici vei adăuga logica de a obține drepturile pentru rolul de Employee
            roles.add(employeeRole);
        } else if (role.equals("Customer")) {
            Role customerRole = new Role(2L, "Customer", null); // Aici vei adăuga logica de a obține drepturile pentru rolul de Customer
            roles.add(customerRole);
        }
        // Construiește și returnează un nou obiect User
        return new UserBuilder().setId(id).setUsername(username).setPassword(password).setRoles(roles).build();
    }
}

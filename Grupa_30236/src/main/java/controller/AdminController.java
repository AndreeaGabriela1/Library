// AdminController.java
package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.User;
import service.user.UserService;
import view.AdminView;

import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final UserService userService;

    public AdminController(AdminView adminView, UserService userService) {
        this.adminView = adminView;
        this.userService = userService;

        attachEventHandlers();
        displayUsersInTable();
    }

    private void displayUsersInTable() {
        List<User> users = userService.getAllUsers();
        adminView.displayUsers(users);
    }

    private void attachEventHandlers() {
        adminView.addAddUserListener(new AddUserListener());
        adminView.addUpdateUserListener(new UpdateUserListener());
        adminView.addDeleteUserListener(new DeleteUserListener());
        // ... alte evenimente
    }

    private class AddUserListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            User newUser = adminView.getUserDataFromFields();
            boolean added = userService.addUser(newUser);
            if (added) {
                displayUsersInTable();
            }
        }
    }

    private class UpdateUserListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Implementează logica pentru actualizarea utilizatorilor
        }
    }

    private class DeleteUserListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Implementează logica pentru ștergerea utilizatorilor
        }
    }

    // ... alte metode specifice pentru operațiile cu utilizatori
}

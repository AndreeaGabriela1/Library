// AdminController.java
package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.User;
import service.user.UserService;
import view.AdminView;

import java.util.ArrayList;
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
        adminView.addSearchButtonListener(new SearchUserListener());
    }
    private class SearchUserListener implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            Long id = adminView.getId();
            // Caută utilizatorii după username în baza de date
            List<User> foundUsers = new ArrayList<>();
            User user = userService.findUser(id);
            foundUsers.add(user);
            // Afișează rezultatul căutării în interfața de utilizator
            adminView.displayUsers(foundUsers);
        }
    }
    private class AddUserListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            User newUser = adminView.getUserDataFromFields();
            boolean added = userService.addUser(newUser); // Presupunând că există o metodă în UserService pentru adăugarea utilizatorului cu rolurile asociate
            if (added) {
                displayUsersInTable();
            }
        }
    }

    private class UpdateUserListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            long userId = adminView.getId(); // ID-ul utilizatorului de actualizat
            User updatedUser = adminView.getUserDataFromFields(); // Noile date pentru utilizator

            // Actualizarea utilizatorului în baza de date
            boolean updated = userService.updateUser(userId, updatedUser);
            if (updated) {
                displayUsersInTable();
            }
        }
    }

    private class DeleteUserListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            long userId = adminView.getId(); // ID-ul utilizatorului de șters

            // Ștergerea utilizatorului din baza de date
            boolean deleted = userService.deleteUser(userId);
            if (deleted) {
                displayUsersInTable();
            }
        }
    }

}

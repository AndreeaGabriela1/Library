package controller;

import database.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.User;
import model.validator.Notification;
import service.book.BookService;
import service.user.AuthenticationService;
import view.CustomerView;
import view.EmployeeView;
import view.LoginView;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("LogIn Successfull!");
                User authenticatedUser = loginNotification.getResult(); // Obține informații despre utilizatorul autentificat
                if (authenticatedUser.getRoles().get(0).getRole().equals(Constants.Roles.ADMINISTRATOR)) {
                    // Inițializează interfața pentru administrator
                    // ...
                } else if (authenticatedUser.getRoles().get(0).getRole().equals(Constants.Roles.EMPLOYEE)) {
                    // Inițializează interfața pentru angajați
                    // ...
                    ComponentFactory factory = ComponentFactory.getInstance(false, loginView.getStage());
                    BookService bookService = factory.getBookService();
                    EmployeeView employeeView = new EmployeeView(new Stage());
                    EmployeeController employeeController = new EmployeeController(employeeView, bookService);
                } else if (authenticatedUser.getRoles().get(0).getRole().equals(Constants.Roles.CUSTOMER)) {
                    // Inițializează interfața pentru clienți
                    // Afișează conținutul specific clientului (CustomerView)
                    ComponentFactory factory = ComponentFactory.getInstance(false, loginView.getStage());
                    BookService bookService = factory.getBookService();
                    CustomerView customerView = new CustomerView(new Stage());
                    CustomerController customerController = new CustomerController(bookService, customerView);

                    customerController.show(); // Afișează CustomerView
                }
                loginView.close(); // Închide fereastra de autentificare
            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }
}

package tk.aarone.registration.AppViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tk.aarone.registration.AppView;
import tk.aarone.registration.Reaction.Reaction;
import tk.aarone.registration.Registration;
import tk.aarone.registration.RegistrationController;

public class Login implements RegistrationController {
    private Registration registration;

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label errorMessage;

    @Override
    public void loaded() {

    }

    @Override
    public void activated() {
        emptyForm();
    }

    @FXML
    public void handleRegisterButtonClicked(ActionEvent actionEvent) {
        registration.setAppView(AppView.getByController(Register.class));
    }

    @FXML
    public void handleLoginButtonClicked(ActionEvent actionEvent) {
        authenticateUser()
                .succeed(reaction -> registration.setAppView(AppView.getByController(Accounts.class)))
                .fail(reaction -> errorMessage.setText(reaction.getMessage()));
    }

    @Override
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    private Reaction authenticateUser() {
        boolean authenticated = registration.getAccounts()
                .stream()
                .filter(account -> account.getUsername().equals(username.getText()))
                .anyMatch(account -> account.authenticate(password.getText()).isSuccess());

        if (authenticated) {
            return Reaction.success();
        }

        return Reaction.failure("The username or password is not correct.");
    }

    private void emptyForm() {
        username.setText("");
        password.setText("");
        errorMessage.setText("");
    }
}

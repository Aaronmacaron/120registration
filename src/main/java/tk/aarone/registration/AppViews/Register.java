package tk.aarone.registration.AppViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.mindrot.jbcrypt.BCrypt;
import tk.aarone.registration.*;
import tk.aarone.registration.Reaction.Reaction;

import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Register implements RegistrationController {

    @FXML private VBox formContainer;
    @FXML private Label errorMessage;
    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private DatePicker birthday;
    @FXML private PasswordField password;
    @FXML private PasswordField repeatPassword;
    private Registration registration;

    @Override
    public void loaded() {
        // Set Width of Date Picker to be equally wide as its parent and thus equally wide as the other fields
        birthday.prefWidthProperty().bind(formContainer.prefWidthProperty());
    }

    @Override
    public void activated() {
        emptyForm();
    }

    @FXML
    public void handleSendButtonClick(ActionEvent actionEvent) {
        validate().succeed(reaction -> {
            registration.getAccounts().add(getAccountFromForm());
            registration.setAppView(AppView.getByController(Accounts.class));
        }).fail(reaction -> errorMessage.setText(reaction.getMessage()))
                .resolve(reaction -> errorMessage.setText(reaction.getMessage()));
    }

    @FXML
    public void handleLoginButtonClick(ActionEvent actionEvent) {
        registration.setAppView(AppView.getByController(Login.class));
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    private Reaction validate() {
        final Stream<Reaction> reactions = Stream.of(
                Validator.validatePassword(password.getText(), repeatPassword.getText()),
                Validator.validateUsername(username.getText(), registration.getAccounts().stream()),
                Validator.validateEmail(email.getText(), registration.getAccounts().stream()),
                Validator.validateBirthday(birthday.getValue())
        );

        return reactions.filter(Reaction::isFailure)
                .findFirst()
                .orElseGet(Reaction::success);
    }

    private Account getAccountFromForm() {
        String passwordHash = BCrypt.hashpw(password.getText(), BCrypt.gensalt());
        return new Account(
                username.getText(),
                email.getText(),
                birthday.getValue(),
                passwordHash,
                false
        );
    }

    private void emptyForm() {
        username.setText("");
        email.setText("");
        birthday.setValue(null);
        password.setText("");
        repeatPassword.setText("");
    }
}

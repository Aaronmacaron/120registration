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
                validatePassword(),
                validateUsername(),
                validateEmail(),
                validateBirthday()
        );

        return reactions.filter(Reaction::isFailure)
                .findFirst()
                .orElseGet(Reaction::success);
    }

    private Reaction validateBirthday() {
        final LocalDate date = birthday.getValue();

        if (date == null) {
            return Reaction.failure("Please provide a valid birthday.");
        }

        if (date.isAfter(LocalDate.now())) {
            return Reaction.failure("Your birthday can't be after after today.");
        }

        return Reaction.success();
    }

    private Reaction validateEmail() {
        final String emailAddress = email.getText();

        boolean emailIsNotUnique = registration.getAccounts().stream()
                .anyMatch(account -> account.getEmail().equals(emailAddress));

        if (emailIsNotUnique) {
            return Reaction.failure("There is already a user with the E-Mail address '" + emailAddress + "'");
        }

        if (emailAddress.isEmpty()) {
            return Reaction.failure("The E-Mail address can't be empty.");
        }

        Pattern emailRegex = Pattern.compile(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\" +
                        "x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[" +
                        "a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-" +
                        "4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]" +
                        ")|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x" +
                        "01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
        );

        if (!emailRegex.matcher(emailAddress).matches()) {
            return Reaction.failure("Please Provide a valid E-Mail address");
        }

        return Reaction.success();
    }

    private Reaction validateUsername() {
        boolean usernameIsNotUnique = registration.getAccounts().stream()
                .anyMatch(account -> account.getUsername().equals(username.getText()));

        if (usernameIsNotUnique) {
            return Reaction.failure("There is already a user called '" + username.getText() + "'");
        }

        if (username.getText().isEmpty()) {
            return Reaction.failure("The username can't be empty.");
        }

        return Reaction.success();
    }

    private Reaction validatePassword() {
        if (!password.getText().equals(repeatPassword.getText())) {
            return Reaction.failure("The passwords must match!");
        }

        if (password.getLength() < 8) {
            return Reaction.failure("The password must be at least 8 characters long");
        }

        return Reaction.success();
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

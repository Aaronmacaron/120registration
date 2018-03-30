import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.mindrot.jbcrypt.BCrypt;

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

    @FXML
    public void handleSendButtonClick(ActionEvent actionEvent) {
        validatePassword()
                .succeed(reaction -> {
                    registration.getAccounts().add(getAccountFromForm());
                    registration.getPrimaryStage().setScene(registration.getAccountsScene());
                })
                .fail(reaction -> errorMessage.setText(reaction.getMessage()))
                .resolve(reaction -> errorMessage.setText(reaction.getMessage()));
    }

    @FXML
    public void handleLoginButtonClick(ActionEvent actionEvent) {
        registration.getPrimaryStage().setScene(registration.getLoginScene());
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
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
                passwordHash
        );
    }
}

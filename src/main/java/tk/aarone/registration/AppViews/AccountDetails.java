package tk.aarone.registration.AppViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.mindrot.jbcrypt.BCrypt;
import tk.aarone.registration.*;
import tk.aarone.registration.Reaction.Reaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AccountDetails implements RegistrationController {

    private Registration registration;

    /* Use Unicode private use are chars to make sure that the user doesn't choose the password placeholder as his new
    password by hazard.*/
    private static final String PASSWORD_PLACEHOLDER = new String(new char[8]).replace("\0", "\ue000");

    @FXML private VBox formContainer;
    @FXML private Label errorMessage;
    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private DatePicker birthday;
    @FXML private TextField password;
    @FXML private CheckBox hidden;

    @Override
    public void loaded() {
        // Set Width of Date Picker to be equally wide as its parent and thus equally wide as the other fields
        birthday.prefWidthProperty().bind(formContainer.prefWidthProperty());
    }

    @Override
    public void activated() {
        Account activeAccount = registration.getAccounts().get(registration.getActiveAccountIndex());
        username.setText(activeAccount.getUsername());
        email.setText(activeAccount.getEmail());
        password.setText(PASSWORD_PLACEHOLDER); // Set hardcoded string since the actual password is hashed
        birthday.setValue(activeAccount.getBirthday());
        hidden.setSelected(activeAccount.isHidden());
    }

    private Reaction validate() {
        List<Account> newAccountList = new ArrayList<>();
        for (int i = 0; i < registration.getAccounts().size(); i++) {
            if (i != registration.getActiveAccountIndex()) {
                newAccountList.add(registration.getAccounts().get(i));
            }
        }

        final Stream<Reaction> reactions = Stream.of(
                Validator.validatePassword(password.getText(), password.getText()),
                Validator.validateUsername(username.getText(), newAccountList.stream()),
                Validator.validateEmail(email.getText(), newAccountList.stream()),
                Validator.validateBirthday(birthday.getValue())
        );

        return reactions.filter(Reaction::isFailure)
                .findFirst()
                .orElseGet(Reaction::success);
    }

    @Override
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public void handleBackButtonClicked(ActionEvent actionEvent) {
        goBackToAccountsScene();
    }

    public void handleDeleteButtonClicked(ActionEvent actionEvent) {
        registration.getAccounts().remove(registration.getActiveAccountIndex());
        goBackToAccountsScene();
    }

    public void handleSaveButtonClicked(ActionEvent actionEvent) {

        String passwordValue = password.getText();
        String password;
        if (passwordValue.equals(PASSWORD_PLACEHOLDER)) { // Password didn't change
            password = registration.getAccounts().get(registration.getActiveAccountIndex()).getPasswordHash();
        } else {
            password = BCrypt.hashpw(passwordValue, BCrypt.gensalt());
        }

        validate().succeed(reaction -> {

            registration.getAccounts().set(registration.getActiveAccountIndex(), new Account(
                    username.getText(),
                    email.getText(),
                    birthday.getValue(),
                    password,
                    hidden.isSelected()
            ));

            registration.setAppView(AppView.getByController(Accounts.class));
        })
                .fail(reaction -> errorMessage.setText(reaction.getMessage()))
                .resolve(reaction -> errorMessage.setText(reaction.getMessage()));
    }

    private void goBackToAccountsScene() {
        registration.setAppView(AppView.getByController(Accounts.class));
    }
}

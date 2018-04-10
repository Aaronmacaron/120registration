package tk.aarone.registration.AppViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.mindrot.jbcrypt.BCrypt;
import tk.aarone.registration.Account;
import tk.aarone.registration.AppView;
import tk.aarone.registration.Registration;
import tk.aarone.registration.RegistrationController;

public class AccountDetails implements RegistrationController {

    private Registration registration;

    private static final String PASSWORD_PLACEHOLDER = "\ue000\ue000\ue000"; /* Use Unicode private use are chars to
    make sure that the user doesn't choose the password placeholder as his new password by hazard.*/

    @FXML private VBox formContainer;
    @FXML private Label errorMessage;
    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private DatePicker birthday;
    @FXML private TextField password;

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

        registration.getAccounts().set(registration.getActiveAccountIndex(), new Account(
                username.getText(),
                email.getText(),
                birthday.getValue(),
                password
        ));

        registration.setAppView(AppView.getByController(Accounts.class));
    }

    private void goBackToAccountsScene() {
        registration.setAppView(AppView.getByController(Accounts.class));
    }
}

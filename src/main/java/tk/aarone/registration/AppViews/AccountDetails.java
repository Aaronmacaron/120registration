package tk.aarone.registration.AppViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tk.aarone.registration.Account;
import tk.aarone.registration.AppView;
import tk.aarone.registration.Registration;
import tk.aarone.registration.RegistrationController;

public class AccountDetails implements RegistrationController {

    private Registration registration;

    @FXML private VBox formContainer;
    @FXML private Label errorMessage;
    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private DatePicker birthday;
    @FXML private TextField password;

    @Override
    public void loaded() {
        Account activeAccount = registration.getAccounts().get(registration.getActiveAccountIndex());
        username.setText(activeAccount.getUsername());
        email.setText(activeAccount.getEmail());
        password.setText("******"); // Set hardcoded string since the actual password is hashed
        birthday.setValue(activeAccount.getBirthday());

        // Set Width of Date Picker to be equally wide as its parent and thus equally wide as the other fields
        birthday.prefWidthProperty().bind(formContainer.prefWidthProperty());
    }

    @Override
    public void activated() {

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

    private void goBackToAccountsScene() {
        registration.setAppView(AppView.getByController(Accounts.class));
    }
}

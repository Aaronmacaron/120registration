import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class Accounts implements RegistrationController {
    private Registration registration;

    @FXML private ListView<Account> accountList;

    @Override
    public void loaded() {
        accountList.setItems(registration.getAccounts());
    }

    @Override
    public void activated() {

    }

    @FXML
    public void handleRegisterButtonClicked(ActionEvent actionEvent) {
        registration.setAppView(AppView.getByController(Register.class));
    }

    @FXML
    public void handleLoginButtonClicked(ActionEvent actionEvent) {
        registration.setAppView(AppView.getByController(Login.class));
    }

    @Override
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public void handleOnListClicked(MouseEvent mouseEvent) {
        Account selectedAccount = accountList.getSelectionModel().getSelectedItem();
        registration.setActiveAccountIndex(registration.getAccounts().indexOf(selectedAccount));
        registration.setAppView(AppView.getByController(AccountDetails.class));
    }
}

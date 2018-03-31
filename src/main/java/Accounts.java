import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Accounts implements RegistrationController {
    private Registration registration;

    @FXML private ListView<Account> accountList;

    @Override
    public void loaded() {
        accountList.setItems(registration.getAccounts());
    }

    @FXML
    public void handleRegisterButtonClicked(ActionEvent actionEvent) {
        registration.getPrimaryStage().setScene(registration.getRegisterScene());
    }

    @FXML
    public void handleLoginButtonClicked(ActionEvent actionEvent) {
        registration.getPrimaryStage().setScene(registration.getLoginScene());
    }

    @Override
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Accounts implements RegistrationController {
    private Registration registration;

    @FXML private ListView<Account> accountList;

    @Override
    public void loaded() {
        accountList.setItems(registration.getAccounts());
    }

    @Override
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}

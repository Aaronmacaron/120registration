import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Login implements RegistrationController {
    private Registration registration;

    @Override
    public void loaded() {

    }

    @FXML
    public void handleLoginButtonClick(ActionEvent actionEvent) {
        registration.getPrimaryStage().setScene(registration.getRegisterScene());
    }

    @Override
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}

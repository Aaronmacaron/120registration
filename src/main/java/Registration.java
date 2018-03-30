import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Registration extends Application {

    private Scene registerScene;
    private Scene loginScene;
    private Scene accountsScene;
    private Stage primaryStage;
    private ObservableList<Account> accounts = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setScene(getRegisterScene());
        primaryStage.show();
    }

    public Scene getRegisterScene() {
        if (this.registerScene != null) {
            return this.registerScene;
        }
        return getScene(Register.class);
    }

    public Scene getLoginScene() {
        if (this.loginScene != null) {
            return this.loginScene;
        }
        return getScene(Login.class);
    }

    public Scene getAccountsScene() {
        if (this.accountsScene != null) {
            return this.accountsScene;
        }
        return getScene(Accounts.class);
    }

    public Scene getScene(Class clazz) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(clazz.getName() + ".fxml"));
        Parent parent  = null;
        try {
            parent = fxmlLoader.load();
            RegistrationController controller = fxmlLoader.getController();
            controller.setRegistration(this);
            controller.loaded();
            return new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("FATAL ERROR!");
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Account> getAccounts() {
        return accounts;
    }
}

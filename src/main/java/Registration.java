import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Registration extends Application {

    private Map<Class, Scene> sceneCache = new HashMap<>();
    private Stage primaryStage;
    private ObservableList<Account> accounts = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle(getClass().getName());
        primaryStage.setScene(getRegisterScene());
        primaryStage.show();
    }

    public Scene getRegisterScene() {
        return getScene(Register.class);
    }

    public Scene getLoginScene() {
        return getScene(Login.class);
    }

    public Scene getAccountsScene() {
        return getScene(Accounts.class);
    }

    public Scene getScene(Class clazz) {
        return sceneCache.computeIfAbsent(clazz, cl -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(cl.getName() + ".fxml"));
            Parent parent;
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
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Account> getAccounts() {
        return accounts;
    }
}

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class Registration extends Application {

    private Stage primaryStage;
    private ObservableList<Account> accounts = FXCollections.observableArrayList();
    private int activeAccountIndex;

    private static Registration registrationSingleton;

    @Override
    public void start(Stage primaryStage) {
        registrationSingleton = this;
        this.primaryStage = primaryStage;
        primaryStage.setTitle(getClass().getName());
        setAppView(AppView.getByController(Register.class));
        primaryStage.show();
    }

    public void setAppView(AppView appView) {
        appView.getRegistrationController().activated();
        primaryStage.setScene(appView.getScene());
    }

    public static Registration getSingleton() {
        return registrationSingleton;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<Account> getAccounts() {
        return accounts;
    }

    public int getActiveAccountIndex() {
        return activeAccountIndex;
    }

    public void setActiveAccountIndex(int activeAccountIndex) {
        this.activeAccountIndex = activeAccountIndex;
    }
}

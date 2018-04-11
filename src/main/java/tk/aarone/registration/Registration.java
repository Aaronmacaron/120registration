package tk.aarone.registration;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import tk.aarone.registration.AppViews.Register;

import java.util.stream.Collectors;

public class Registration extends Application {

    private Stage primaryStage;
    private ObservableList<Account> accounts = FXCollections.observableArrayList();
    private ObservableList<Account> processedAccounts = FXCollections.observableArrayList();
    private int activeAccountIndex;
    private boolean accountsAreHidden = false;

    private static Registration registrationSingleton;

    @Override
    public void start(Stage primaryStage) {
        registrationSingleton = this;
        this.primaryStage = primaryStage;
        primaryStage.setTitle(getClass().getSimpleName());
        setAppView(AppView.getByController(Register.class));
        primaryStage.show();

        accounts.addListener(this::updateProcessedAccount);
    }

    private void updateProcessedAccount(ListChangeListener.Change<? extends Account> c) {
        processedAccounts.setAll(
                accounts.stream()
                        .filter(account -> accountsAreHidden || !account.isHidden())
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
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

    public ObservableList<Account> getProcessedAccounts() {
        return processedAccounts;
    }

    public int getActiveAccountIndex() {
        return activeAccountIndex;
    }

    public void setActiveAccountIndex(int activeAccountIndex) {
        this.activeAccountIndex = activeAccountIndex;
    }

    public void toggleHiddenAccounts() {
        accountsAreHidden = !accountsAreHidden;
        this.updateProcessedAccount(null);
    }
}

package tk.aarone.registration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppView {
    private Scene scene;
    private RegistrationController registrationController;

    private static Map<Class, AppView> appViewCache = new HashMap<>();

    public AppView(Scene scene, RegistrationController registrationController) {
        this.scene = scene;
        this.registrationController = registrationController;
    }

    public static AppView getByController(Class<? extends RegistrationController> clazz) {
        return appViewCache.computeIfAbsent(clazz, cl -> {
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource(cl.getSimpleName() + ".fxml"));
            Parent parent;
            try {
                parent = fxmlLoader.load();
                RegistrationController controller = fxmlLoader.getController();
                controller.setRegistration(Registration.getSingleton());
                controller.loaded();
                return new AppView(new Scene(parent), controller);
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Unrecoverable error occurred while loading scene.");
        });
    }

    public Scene getScene() {
        return scene;
    }

    public RegistrationController getRegistrationController() {
        return registrationController;
    }
}

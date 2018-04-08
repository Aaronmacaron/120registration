import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

public class Account {
    private ObservableStringValue username;
    private ObservableStringValue email;
    private ObjectProperty<LocalDate> birthday;
    private ObservableStringValue passwordHash;

    public Account(ObservableStringValue username, ObservableStringValue email, ObjectProperty<LocalDate> birthday,
                   ObservableStringValue passwordHash) {
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.passwordHash = passwordHash;
    }

    public Reaction authenticate(String password) {
        if (BCrypt.checkpw(password, this.passwordHash.getValue())) {
            return Reaction.success();
        }

        return Reaction.failure();
    }

    public ObservableStringValue getObservableUsername() {
        return username;
    }

    public ObservableStringValue getObservableEmail() {
        return email;
    }

    public ObservableObjectValue<LocalDate> getObservableBirthday() {
        return birthday;
    }

    public ObservableStringValue getObservablePasswordHash() {
        return passwordHash;
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public LocalDate getBirthday() {
        return birthday.getValue();
    }

    public String getPasswordHash() {
        return passwordHash.getValue();
    }

    @Override
    public String toString() {
        return this.username.getValue() + " (" + this.email.getValue() + ")";
    }
}

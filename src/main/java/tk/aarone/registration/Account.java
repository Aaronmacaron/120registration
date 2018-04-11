package tk.aarone.registration;

import org.mindrot.jbcrypt.BCrypt;
import tk.aarone.registration.Reaction.Reaction;

import java.time.LocalDate;

public class Account {
    private String username;
    private String email;
    private LocalDate birthday;
    private String passwordHash;
    private boolean hidden;

    public Account(String username, String email, LocalDate birthday,
                   String passwordHash, boolean hidden) {
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.passwordHash = passwordHash;
        this.hidden = hidden;
    }

    public Reaction authenticate(String password) {
        if (BCrypt.checkpw(password, this.passwordHash)) {
            return Reaction.success();
        }

        return Reaction.failure();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isHidden() {
        return hidden;
    }

    @Override
    public String toString() {
        return this.username + " (" + this.email + ")";
    }
}

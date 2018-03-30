import java.time.LocalDate;

public class Account {
    private String username;
    private String email;
    private LocalDate birthday;
    private String passwordHash;

    public Account(String username, String email, LocalDate birthday, String passwordHash) {
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.passwordHash = passwordHash;
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

    @Override
    public String toString() {
        return this.username + " (" + this.email + ")";
    }
}

package tk.aarone.registration;

import tk.aarone.registration.Reaction.Reaction;

import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Validator {

    public static Reaction validateBirthday(LocalDate date) {
        if (date == null) {
            return Reaction.failure("Please provide a valid birthday.");
        }

        if (date.isAfter(LocalDate.now())) {
            return Reaction.failure("Your birthday can't be after after today.");
        }

        return Reaction.success();
    }

    public static Reaction validateEmail(String email, Stream<Account> accounts) {
        boolean emailIsNotUnique = accounts.anyMatch(account -> account.getEmail().equals(email));

        if (emailIsNotUnique) {
            return Reaction.failure("There is already a user with the E-Mail address '" + email + "'");
        }

        if (email.isEmpty()) {
            return Reaction.failure("The E-Mail address can't be empty.");
        }

        Pattern emailRegex = Pattern.compile(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\" +
                        "x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[" +
                        "a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-" +
                        "4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]" +
                        ")|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x" +
                        "01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
        );

        if (!emailRegex.matcher(email).matches()) {
            return Reaction.failure("Please Provide a valid E-Mail address");
        }

        return Reaction.success();
    }

    public static Reaction validateUsername(String username, Stream<Account> accounts) {
        boolean usernameIsNotUnique = accounts.anyMatch(account -> account.getUsername().equals(username));

        if (usernameIsNotUnique) {
            return Reaction.failure("There is already a user called '" + username + "'");
        }

        if (username.isEmpty()) {
            return Reaction.failure("The username can't be empty.");
        }

        return Reaction.success();
    }

    public static Reaction validatePassword(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            return Reaction.failure("The passwords must match!");
        }

        if (password.length() < 8) {
            return Reaction.failure("The password must be at least 8 characters long");
        }

        return Reaction.success();
    }
}

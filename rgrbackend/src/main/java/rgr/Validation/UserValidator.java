package rgr.Validation;

import org.springframework.stereotype.Component;
import rgr.Models.User;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;

import static rgr.Constants.RegularExpressions.USERDATA_FORBIDDEN_CHARACTERS;

@Component
public class UserValidator extends ApplicationValidator {

    public void validateUser(User user, String repeatedPassword, String role) throws ValidationException {
        Set<ConstraintViolation<User>> validateWarnings = validator.validate(user);
        if (!validateWarnings.isEmpty()) {
            throw new ValidationException("User data is not valid");
        }
        checkRole(role);
        if (!repeatedPassword.equals(user.getPassword())) {
            throw new ValidationException("Пароли не совпадают");
        }
        checkUserStrings(user);
    }

    private void checkRole(String role) throws ValidationException {
        if (role == null) {
            throw new ValidationException("Роль не указна");
        }
        if (!role.equals("USER") && !role.equals("TESTER")) {
            throw new ValidationException("Неверно указана роль");
        }
    }

    private void checkUserStrings(User user) throws ValidationException {
        checkRussianCharacters(user.getPassword());
        checkForbiddenCharactersForUser(user.getPassword());
        checkRussianCharacters(user.getUsername());
        checkForbiddenCharactersForUser(user.getUsername());
    }

    private void checkForbiddenCharactersForUser(String stringForCheck) throws ValidationException {
        if (stringForCheck.matches(USERDATA_FORBIDDEN_CHARACTERS)) {
            throw new ValidationException("User data contains forbidden characters in string: " + stringForCheck);
        }
    }
}

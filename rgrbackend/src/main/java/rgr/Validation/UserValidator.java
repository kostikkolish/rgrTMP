package rgr.Validation;

import org.springframework.stereotype.Component;
import rgr.Models.User;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;

import static rgr.Constants.Constants.MAX_USERDATA_LENGTH;
import static rgr.Constants.Constants.MIN_USERDATA_LENGTH;
import static rgr.Constants.RegularExpressions.USERDATA_FORBIDDEN_CHARACTERS;

@Component
public class UserValidator extends ApplicationValidator {

    public void validateUser(User user, String repeatedPassword, String role) throws ValidationException {
        Set<ConstraintViolation<User>> validateWarnings = validator.validate(user);
        if (!validateWarnings.isEmpty()) {
            throw new ValidationException("User email is not valid");
        }
        checkUserLoginAndPassword(user, repeatedPassword);
        checkRole(role);
        checkUserStrings(user);
    }

    private void checkUserLoginAndPassword(User user, String repeatedPassword) throws ValidationException {
        if(user.getUsername().length() < MIN_USERDATA_LENGTH ||
           user.getUsername().length() > MAX_USERDATA_LENGTH) {
            throw new ValidationException("Длина логина должна быть от 5 до 15 символов");
        } else if(user.getPassword().length() < MIN_USERDATA_LENGTH ||
                  user.getPassword().length() > MAX_USERDATA_LENGTH) {
            throw new ValidationException("Длина пароля должна быть от 5 до 15 символов");
        } else if(!user.getPassword().equals(repeatedPassword)) {
            throw new ValidationException("Пароли не совпадают");
        }
    }

    private void checkRole(String role) throws ValidationException {
        if (role == null) {
            throw new ValidationException("Роль не указана");
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

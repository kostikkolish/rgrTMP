package rgr.Validation;

import org.springframework.stereotype.Component;
import rgr.Models.Test;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;

@Component
public class TestValidator extends ApplicationValidator {

    private void addSlashes(Test test) {
        test.setName(replaceSpecialCharacters(test.getName()));
        test.setDescription(replaceSpecialCharacters(test.getDescription()));
    }

    public void validateTest(Test test) throws ValidationException {
        Set<ConstraintViolation<Test>> validateWarnings = validator.validate(test);
        if (!validateWarnings.isEmpty()) {
            throw new ValidationException("Test data is not valid");
        }
        checkTestStrings(test);
        addSlashes(test);
    }

    private void checkTestStrings(Test test) throws ValidationException {
        checkForbiddenCharacters(test.getName());
        checkForbiddenCharacters(test.getDescription());
    }
}

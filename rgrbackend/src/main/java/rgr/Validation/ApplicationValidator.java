package rgr.Validation;

import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static rgr.Constants.RegularExpressions.RUSSIAN_CHARACTERS;
import static rgr.Constants.RegularExpressions.TEST_FORBIDDEN_CHARACTERS;

public abstract class ApplicationValidator {

    protected static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    protected void checkRussianCharacters(String stringForCheck) throws ValidationException {
        if (stringForCheck.matches(RUSSIAN_CHARACTERS)) {
            throw new ValidationException("Data contains russian characters in string: " + stringForCheck);
        }
    }

    protected String replaceSpecialCharacters(String stringForReplace) {
       return  stringForReplace
                .replaceAll("'", "\\'")
                .replaceAll("\"", "\\\"");
    }

    protected void checkForbiddenCharacters(String stringForCheck) throws ValidationException {
        if (stringForCheck.matches(TEST_FORBIDDEN_CHARACTERS)) {
            throw new ValidationException("Forbidden characters in string: " + stringForCheck);
        }
    }
}

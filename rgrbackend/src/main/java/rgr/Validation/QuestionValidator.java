package rgr.Validation;

import org.springframework.stereotype.Component;
import rgr.Models.Question;
import rgr.Models.QuestionOption;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;

@Component
public class QuestionValidator extends ApplicationValidator {

    public void validateQuestion(Question question) throws ValidationException {
        Set<ConstraintViolation<Question>> validateWarnings = validator.validate(question);
        if (!validateWarnings.isEmpty()) {
            throw new ValidationException("Question data is not valid");
        }
        checkForbiddenCharacters(question.getText());
        replaceSpecialCharacters(question.getText());
    }

    public void validateOption(QuestionOption option) throws ValidationException {
        Set<ConstraintViolation<QuestionOption>> validateWarnings = validator.validate(option);
        if (!validateWarnings.isEmpty()) {
            throw new ValidationException("Option " + option.getOptionText() + "  is not valid");
        }
        replaceSpecialCharacters(option.getOptionText());
        checkForbiddenCharacters(option.getOptionText());
    }

}

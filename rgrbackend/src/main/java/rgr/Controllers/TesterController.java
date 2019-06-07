package rgr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rgr.Models.Question;
import rgr.Models.Test;
import rgr.Models.User;
import rgr.Services.TesterService;

import java.util.List;

import static rgr.Constants.Constants.FIRST_QUESTION_ORDER;
import static rgr.Constants.Pages.*;

@Controller
@RequestMapping("/tester")
@PreAuthorize("hasAuthority('TESTER')")
public class TesterController {

    @Autowired
    TesterService testerService;

    @GetMapping
    public String testerPage(Model model) throws Exception{
        testerService.addTestAttributes(model);
        return TESTER;
    }

    @GetMapping("/creator")
    public String creatorPage(Model model, Integer testId) {
        testerService.addAttributesInTestCreator(model, testId);
        return TEST_CREATOR;
    }

    @PostMapping("/createTest")
    public String createTest(Model model, Test test, @RequestParam Integer testId) {
        test.setId(testId);
        test = testerService.createTest(test);
        testerService.addAttributesOnAccessRedactor(model, test);
        return ACCESS_REDACTOR;
    }

    @GetMapping("/questionRedactor")
    public String getQuestionRedactor(Model model, @RequestParam Integer testId) {
        Question question = testerService.getQuestionByOrder(testId, FIRST_QUESTION_ORDER);
        testerService.addAttributesOnQuestionCreator(model, question, testId);
        return QUESTION_CREATOR;
    }

    @GetMapping("/accessRedactor")
    public String getAccessRedactor(Model model, @RequestParam Integer testId) {
        Test test = testerService.getTestById(testId);
        testerService.addAttributesOnAccessRedactor(model, test);
        return ACCESS_REDACTOR;
    }

    @GetMapping("/createAccess")
    public ResponseEntity<?> createAccess(@RequestParam Integer testId, @RequestParam Integer userId) {
        try {
            testerService.createAccess(testId, userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAccessed")
    public ResponseEntity<List<User>> getAccessedUsers(@RequestParam Integer testId) {
        return ResponseEntity.ok(testerService.getAccessedUsers(testId));
    }

    @GetMapping("/removeAccess")
    public ResponseEntity<?> removeFromAccessed(@RequestParam Integer testId, @RequestParam Integer userId) {
        testerService.removeFromAccessed(testId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getQuestion")
    public String getQuestion(Model model,@RequestParam Integer testId, @RequestParam Integer questionOrder) {
        Question question = testerService.getQuestionByOrder(testId, questionOrder);
        testerService.addAttributesOnQuestionCreator(model, question, testId);
        return QUESTION_CREATOR;
    }

    @PostMapping("/saveQuestion")
    public String saveQuestion(Model model,
                               Integer testId,
                               Integer questionOrder,
                               String questionText,
                               String option1,
                               String option2,
                               String option3,
                               String option4,
                               Integer answer) {
        Question question = testerService.getQuestionByOrder(testId, questionOrder);
        question.setText(questionText);
        testerService.setOptionsAsStrings(question, option1, option2, option3, option4, answer);
        question = testerService.saveQuestion(question);
        testerService.addAttributesOnQuestionCreator(model, question, testId);
        return QUESTION_CREATOR;
    }
}

package rgr.Services;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import rgr.Models.*;
import rgr.Repositories.QuestionsRepository;
import rgr.Repositories.ResultsRepository;
import rgr.Repositories.TestsRepository;
import rgr.Repositories.UserRepository;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static rgr.Constants.Attributes.*;
import static rgr.Constants.Constants.*;
import static rgr.Constants.Pages.LOGIN;
import static rgr.Constants.Pages.SIGN_UP;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TestsRepository testsRepository;
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    ResultsRepository resultsRepository;

    public void saveUser(User user, String role) throws ConstraintViolationException, Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("Пользователь с таким логином уже существует");
        }
        if (role.equals("TESTER")){
            user.setRole(Collections.singleton(Role.TESTER));
        } else {
            user.setRole(Collections.singleton(Role.USER));
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    private List<Test> getAccessedTests() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Test> simpleTestsList = new LinkedList<Test>();
        for(Test test : testsRepository.getAllByAccessedUsersContains(user)) {
            if(isTestCompleted(test)) {
                Test simpleTestModel = new Test();
                simpleTestModel.setId(test.getId());
                simpleTestModel.setName(test.getName());
                simpleTestModel.setDescription(test.getDescription());
                simpleTestModel.setResults(test.getResults());
                simpleTestsList.add(simpleTestModel);
            }
        }
        return simpleTestsList;
    }

    public void addAttributes(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(LOGIN, user.getUsername());
        model.addAttribute(TESTS_ON_USER_PAGE, getAccessedTests());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    private boolean isTestCompleted(Test test) {
        if(test.getName() == null) return false;
        if(test.getDescription() == null) return false;
        for(Question question : questionsRepository.getAllByTest(test)) {
            if(question.getText() == null){
                return false;
            }
            if(question.getAnswer() == null ||
               OPTION1_ORDER > question.getAnswer() ||
               question.getAnswer() > OPTION4_ORDER ) {
                return false;
            }
            if(question.getOptions() == null ||
               question.getOptions().size() != NUMBER_OF_OPTIONS){
                return false;
            }
            for(QuestionOption option : question.getOptions()) {
                if(option.getOptionText() == null ||
                   option.getOptionText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addFirstQuestionOnTestPage(Model model, Integer testId) {
        Test test = testsRepository.getById(testId);
        Question question = questionsRepository.getByNumberAndTest(FIRST_QUESTION_ORDER, test);
        addQuestionOnTestPage(model, question, test, 0);
    }

    private void addQuestionOnTestPage(Model model,
                                       Question question,
                                       Test test,
                                       Integer currentResult) {
        if (isImageExisting(question.generateImageName())) {
            model.addAttribute(QUESTION_IMAGE, question.generateImageName());
        }
        model.addAttribute(QUESTION_BODY, question.getText());
        model.addAttribute(QUESTION_ID_IN_TEST, question.getId());
        Question.addQuestionAttributes(model, question);
        model.addAttribute(TEST_NAME, test.getName());
        model.addAttribute(TEST_ID, test.getId());
        model.addAttribute(CURRENT_RESULT, currentResult);
    }

    public boolean userAnswerProcess(Model model,
                                Integer questionId,
                                Integer currentResult,
                                Integer answer) {

        Question question = questionsRepository.getById(questionId);
        Test test = question.getTest();
        currentResult+=getPointsForAnswer(question, answer);
        if (!question.getNumber().equals(NUMBER_OF_QUESTIONS)) {
            Question nextQuestion = questionsRepository.getByNumberAndTest(question.getNumber()+1, test);
            addQuestionOnTestPage(model, nextQuestion, test, currentResult);
            return true;
        } else {
            finishTest(model, currentResult, test);
            return false;
        }
    }

    private void finishTest(Model model, Integer currentResult,Test test) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        removeResultIfExist(test, user);
        Result result = new Result();
        result.setResult(currentResult);
        result.setTest(test);
        result.setUser(user);
        resultsRepository.save(result);
        addAttributesOnResultPage(model, test, result);
    }


    private void removeResultIfExist(Test test, User user) {
        Result existingResult = resultsRepository.getByUserAndTest(user, test);
        if (existingResult != null) {
            resultsRepository.delete(existingResult);
        }
    }

    private void addAttributesOnResultPage(Model model, Test test, Result result) {
        model.addAttribute(TEST_NAME, test.getName());
        model.addAttribute(RESULT_ON_RESULT_PAGE, result.getResult() + "/" + MAX_TEST_POINTS);
    }


    private Integer getPointsForAnswer(Question question, Integer answer) {
        if (question.getAnswer().equals(answer)) {
            return POINTS_FOR_QUESTION;
        } else {
            return 0;
        }
    }

    public Resource getImageByName(String imageName) throws MalformedURLException, FileNotFoundException {
        Path pathToImage = Paths.get(imageName);
        Resource image = new UrlResource(pathToImage.toUri());
        return image;
    }

    private boolean isImageExisting(String imageName) {
        try {
            Path pathToImage = Paths.get(imageName);
            Resource image = new UrlResource(pathToImage.toUri());
            return image.exists();
        } catch (Exception e) {
            return false;
        }
    }
}

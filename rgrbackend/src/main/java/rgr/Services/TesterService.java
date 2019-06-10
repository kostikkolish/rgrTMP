package rgr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import rgr.Models.*;
import rgr.Repositories.QuestionOptionsRepository;
import rgr.Repositories.QuestionsRepository;
import rgr.Repositories.TestsRepository;
import rgr.Repositories.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static rgr.Constants.Attributes.*;
import static rgr.Constants.Constants.*;

@Service
public class TesterService {

    @Autowired
    TestsRepository testsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    QuestionOptionsRepository questionOptionsRepository;

    public List<Test> getTestsByTester() throws Exception{
        User tester = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!tester.getRole().contains(Role.TESTER)) {
            throw new Exception("пользователь не является тестировщиком");
        }
        return testsRepository.findAllByCreator_Username(tester.getUsername());
    }

    public void addTestAttributes(Model model) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute(LOGIN, user.getUsername());
            model.addAttribute(TESTS_ON_TESTER_PAGE, getTestsByTester());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAttributesInTestCreator(Model model, Integer testId) {
        if (testId.equals(CREATE_NEW_TEST_ID)) {
            model.addAttribute(TEST_NAME, "Название теста");
            model.addAttribute(TEST_DESCRIPTION_IN_CREATOR, "Описание теста");
        } else {
            Test currentTest = testsRepository.getById(testId);
            model.addAttribute(TEST_NAME, currentTest.getName());
            model.addAttribute(TEST_DESCRIPTION_IN_CREATOR, currentTest.getDescription());
        }
        model.addAttribute(TEST_ID, testId);
    }

    public Test createTest(Test test) {
        if (test.getId() == CREATE_NEW_TEST_ID) {
            testsRepository.save(test);
            test =  testsRepository.getByName(test.getName());
        }
        User creator = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        test.setCreator(creator);
        test.setAccessedUsers(testsRepository.getById(test.getId()).getAccessedUsers());
        test.setResults(testsRepository.getById(test.getId()).getResults());
        testsRepository.save(test);
        return test;
    }

    public Test getTestById(Integer testId) {
        return testsRepository.getById(testId);
    }

    public void createAccess(Integer testId, Integer userId) throws Exception{
        Test test = testsRepository.getById(testId);
        User user = userRepository.getById(userId);
        if (test.getAccessedUsers().contains(user) || user.getAccessedTests().contains(test)) {
            throw new Exception("Already accessed");
        }
        test.getAccessedUsers().add(user);
        testsRepository.save(test);
    }

    public List<User> getAccessedUsers(Integer testId) {
        Test test = testsRepository.getById(testId);
        LinkedList<User> accessedUsersWithoutLoginInfo = new LinkedList<User>();
        for(User user : test.getAccessedUsers()) {
            User userWithoutLoginInfo = new User();
            userWithoutLoginInfo.setId(user.getId());
            userWithoutLoginInfo.setUsername(user.getUsername());
            accessedUsersWithoutLoginInfo.add(userWithoutLoginInfo);
        }
        return accessedUsersWithoutLoginInfo;
    }

    public void removeFromAccessed(Integer testId, Integer userId) {
        Test test = testsRepository.getById(testId);
        User user = userRepository.getById(userId);
        if (test.getAccessedUsers().contains(user)) {
            test.getAccessedUsers().remove(user);
        }
        if (user.getAccessedTests().contains(test)) {
            user.getAccessedTests().remove(test);
        }
        testsRepository.save(test);
        userRepository.save(user);
    }

    public Question getQuestionByOrder(Integer testId, Integer order) {
        Test test = testsRepository.getById(testId);
        try{
            Question question = questionsRepository.getByNumberAndTest(order, test);
            if (question == null) throw new Exception("Question doesn't exist");
            return question;
        }catch (Exception e) {
            Question emptyQuestion = new Question();
            emptyQuestion.setNumber(order);
            emptyQuestion.setTest(test);
            questionsRepository.save(emptyQuestion);
            return emptyQuestion;
        }
    }

    public void addAttributesOnQuestionCreator(Model model, Question question, Integer testId) {
        model.addAttribute(TEST_ID, testId);
        model.addAttribute(QUESTION_ORDER_IN_CREATOR, question.getNumber());
        model.addAttribute(QUESTION_BODY, question.getText());
        model.addAttribute(ANSWER_IN_CREATOR, question.getAnswer());
        Question.addQuestionAttributes(model, question);
    }

    public Question saveQuestion(Question question) {
        for (QuestionOption option : question.getOptions()) {
            questionOptionsRepository.save(option);
        }
        questionsRepository.save(question);
        return questionsRepository.getById(question.getId());
    }

    public void setOptionsAsStrings(Question question,
                                    String option1String,
                                    String option2String,
                                    String option3String,
                                    String option4String,
                                    Integer answerNumber) {

        QuestionOption option1 = QuestionOption.createOptionByTextAndOrder(option1String, OPTION1_ORDER);
        QuestionOption option2 = QuestionOption.createOptionByTextAndOrder(option2String, OPTION2_ORDER);
        QuestionOption option3 = QuestionOption.createOptionByTextAndOrder(option3String, OPTION3_ORDER);
        QuestionOption option4 = QuestionOption.createOptionByTextAndOrder(option4String, OPTION4_ORDER);
        option1 = saveOptionIfNotExist(option1, OPTION1_ORDER);
        option2 = saveOptionIfNotExist(option2, OPTION2_ORDER);
        option3 = saveOptionIfNotExist(option3, OPTION3_ORDER);
        option4 = saveOptionIfNotExist(option4, OPTION4_ORDER);
        List<QuestionOption> optionList = new ArrayList<QuestionOption>();
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);
        optionList.add(option4);
        createNewOptionsInQuestion(question, optionList);
        question.setAnswer(answerNumber);
    }

    private void createNewOptionsInQuestion(Question question, List<QuestionOption> options) {
        question.setOptions(null);
        questionsRepository.save(question);
        question.setOptions(options);
    }

    private QuestionOption saveOptionIfNotExist(QuestionOption option, Integer order) {
        QuestionOption optionFromRepository = questionOptionsRepository.getByOptionTextAndOptionOrder(option.getOptionText(), order);
        if (optionFromRepository != null) {
            return optionFromRepository;
        } else {
            questionOptionsRepository.save(option);
            return option;
        }
    }

    public void addAttributesOnAccessRedactor(Model model, Test test) {
        model.addAttribute(ACCESSED_USERS_IN_CREATOR, test.getAccessedUsers());
        model.addAttribute(ALL_USERS_IN_CREATOR, userRepository.findAllByRole(Role.USER));
        model.addAttribute(TEST_ID, test.getId());
    }

    public void saveImageForQuestion(MultipartFile image, Question question) {
        try{
            if (image.isEmpty() || image.getName().contains("..")) {
                throw new Exception("File is not valid");
            }
            Path pathToImage = Paths.get(PATH_TO_QUESTION_IMAGES);
            Files.write(pathToImage.resolve(question.generateImageName()), image.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

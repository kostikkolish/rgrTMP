package rgr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import rgr.Models.Role;
import rgr.Models.Test;
import rgr.Models.User;
import rgr.Repositories.TestsRepository;
import rgr.Repositories.UserRepository;

import java.util.LinkedList;
import java.util.List;

import static rgr.Constants.Attributes.TESTS_ON_TESTER_PAGE;

@Service
public class TesterService {

    @Autowired
    TestsRepository testsRepository;
    @Autowired
    UserRepository userRepository;

    public List<Test> getTestsByTester() throws Exception{
        User tester = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!tester.getRole().contains(Role.TESTER)) {
            throw new Exception("пользователь не является тестировщиком");
        }
        return testsRepository.findAllByCreator_Username(tester.getUsername());
    }

    public Model addTestAttributes(Model model) {
        List<Test> tests = new LinkedList<Test>();
        Test test1 = new Test();
        test1.setName("dasf123");
        Test test2 = new Test();
        test2.setName("asddsf123");
        Test test3 = new Test();
        test3.setName("dasf123adfasdf");
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        model.addAttribute(TESTS_ON_TESTER_PAGE, tests);
        return model;
    }
}

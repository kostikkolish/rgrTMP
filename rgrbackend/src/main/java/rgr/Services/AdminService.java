package rgr.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import rgr.Models.Test;
import rgr.Models.User;
import rgr.Repositories.ResultsRepository;
import rgr.Repositories.TestsRepository;

import static rgr.Constants.Attributes.*;

@Service
public class AdminService {

    @Autowired
    TestsRepository testsRepository;
    @Autowired
    ResultsRepository resultsRepository;

    public void addTestAttributes(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(LOGIN, user.getUsername());
        model.addAttribute(TESTS_ON_ADMIN_PAGE, testsRepository.getAllByResultsIsNotNull());
    }

    public void addStatisticsOnPage(Model model, Integer testId) {
        Test test = testsRepository.getById(testId);
        model.addAttribute(TEST_NAME, test.getName());
        model.addAttribute(RESULTS_IN_STATISTICS, resultsRepository.getAllByTest(test));
    }
}

package rgr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import rgr.Services.UserService;

import static rgr.Constants.Pages.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String userPage(Model model) {
        userService.addAttributes(model);
        return USER;
    }

    @GetMapping("/test")
    public String beginTest(Model model, @RequestParam Integer testId) {
        userService.addFirstQuestionOnTestPage(model, testId);
        return TEST;
    }

    @GetMapping("/answer")
    public String answerQuestion(Model model,
                                 @RequestParam Integer questionId,
                                 @RequestParam Integer currentResult,
                                 @RequestParam Integer answer) {
        if(userService.userAnswerProcess(model, questionId, currentResult, answer)) {
            return TEST;
        } else {
            return RESULT;
        }
    }
}

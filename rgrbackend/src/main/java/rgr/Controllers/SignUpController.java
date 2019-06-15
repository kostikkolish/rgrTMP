package rgr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rgr.Models.User;
import rgr.Services.UserService;
import rgr.Validation.UserValidator;

import static rgr.Constants.Attributes.ERROR_MESSAGE;
import static rgr.Constants.Pages.LOGIN;
import static rgr.Constants.Pages.SIGN_UP;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    UserService userService;
    @Autowired
    UserValidator applicationValidator;

    @GetMapping
    public String signUpPage() {
        return SIGN_UP;
    }

    @PostMapping
    public String addUser(String role, User user, Model model, String repeatedPassword) {
        try {
            applicationValidator.validateUser(user, repeatedPassword, role);
            userService.saveUser(user, role);
            return "redirect:/" + LOGIN;
        } catch (DataIntegrityViolationException e) {
            model.addAttribute(ERROR_MESSAGE, "Пользователь с указанными email или логином уже существует");
            return SIGN_UP;
        } catch (Exception e) {
            model.addAttribute(ERROR_MESSAGE, e.getMessage());
            return SIGN_UP;
        }
    }
}

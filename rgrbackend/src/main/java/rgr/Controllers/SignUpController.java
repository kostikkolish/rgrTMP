package rgr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rgr.Models.User;
import rgr.Services.UserService;

import static rgr.Constants.Attributes.SIGN_UP_ERROR_MESSAGE;
import static rgr.Constants.Pages.LOGIN;
import static rgr.Constants.Pages.SIGN_UP;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    UserService userService;

    @GetMapping
    public String signUpPage() {
        return SIGN_UP;
    }

    @PostMapping
    public String addUser(@RequestParam String role, User user, Model model) {
        try {
            userService.saveUser(user, role);
            return "redirect:/" + LOGIN;
        } catch (DataIntegrityViolationException e) {
            model.addAttribute(SIGN_UP_ERROR_MESSAGE, "Неверные данные, попробуйте ещё раз");
            return SIGN_UP;
        } catch (Exception e) {
            model.addAttribute(SIGN_UP_ERROR_MESSAGE, e.getMessage());
            return SIGN_UP;
        }
    }
}

package rgr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rgr.Models.User;
import rgr.Services.UserService;

@Controller
@RequestMapping("/signupAPI")
public class SignUpController {

    @Autowired
    UserService userService;

    @PostMapping
    public String addUser(User user, Model model) {
        userService.saveUser(user);
        return "redirect:/login";
    }
}

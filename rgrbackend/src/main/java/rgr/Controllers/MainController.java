package rgr.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String loginPage() {
        return "auth";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "signUp";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
}

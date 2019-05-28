package rgr.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static rgr.Constants.Pages.LOGIN;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return LOGIN;
    }
}

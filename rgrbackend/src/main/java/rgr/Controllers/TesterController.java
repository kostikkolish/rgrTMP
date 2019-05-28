package rgr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rgr.Services.TesterService;

import static rgr.Constants.Pages.TESTER;
import static rgr.Constants.Pages.TEST_CREATOR;

@Controller
@RequestMapping("/tester")
@PreAuthorize("hasAuthority('TESTER')")
public class TesterController {

    @Autowired
    TesterService testerService;

    @GetMapping
    public String testerPage(Model model) throws Exception{
        testerService.addTestAttributes(model);
        return TESTER;
    }

    @GetMapping("/creator")
    public String creatorPage() {
        return TEST_CREATOR;
    }
}

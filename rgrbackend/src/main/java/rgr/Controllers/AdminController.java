package rgr.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rgr.Services.AdminService;

import static rgr.Constants.Pages.ADMIN;
import static rgr.Constants.Pages.STATISTICS;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping
    public String testerPage(Model model) throws Exception{
        adminService.addTestAttributes(model);
        return ADMIN;
    }

    @GetMapping("/statistics")
    public String getTestStatistics(Model model, Integer testId) {
        adminService.addStatisticsOnPage(model, testId);
        return STATISTICS;
    }
}

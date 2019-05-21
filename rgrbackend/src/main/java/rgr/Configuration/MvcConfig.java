package rgr.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/auth").setViewName("auth");
        registry.addViewController("/user").setViewName("userPage");
        registry.addViewController("/admin").setViewName("adminPage");
        registry.addViewController("/tester").setViewName("testerPage");
        registry.addViewController("/statistics").setViewName("statistics");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/creator").setViewName("creator");
    }

}
package rgr.Configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if("TESTER".equals(auth.getAuthority())){
                httpServletResponse.sendRedirect("/tester");
            } else if("USER".equals(auth.getAuthority())){
                httpServletResponse.sendRedirect("/user");
            } else if("ADMIN".equals(auth.getAuthority())) {
                httpServletResponse.sendRedirect("/admin");
            }
        }
    }
}

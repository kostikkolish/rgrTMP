package rgr.Models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

public enum  Role implements GrantedAuthority {

    TESTER,
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

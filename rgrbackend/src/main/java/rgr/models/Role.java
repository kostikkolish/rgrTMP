package rgr.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(length = 20)
    private String role;

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}

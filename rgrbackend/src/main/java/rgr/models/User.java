package rgr.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 15, unique = true)
    private String username;

    @NotNull
    @Column(nullable = false, length = 15)
    private String password;

    @ManyToOne
    private Role role;

    @ManyToMany
    private List<Test> accessedTests;

    @OneToMany
    private List<Result> results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Test> getAccessedTests() {
        return accessedTests;
    }

    public List<Result> getResults() {
        return results;
    }
}

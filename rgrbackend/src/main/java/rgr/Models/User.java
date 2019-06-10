package rgr.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false, length = 15, unique = true)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name="role",joinColumns=@JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

    @ManyToMany
    @JoinTable(name = "tests_access", inverseJoinColumns = @JoinColumn(name = "test_id"))
    private List<Test> accessedTests;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "results", inverseJoinColumns =  @JoinColumn(name = "id"))
    private List<Result> results;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public List<Test> getAccessedTests() {
        return accessedTests;
    }

    public void setAccessedTests(List<Test> accessedTests) {
        this.accessedTests = accessedTests;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

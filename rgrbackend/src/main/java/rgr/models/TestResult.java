package rgr.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private User user;

    @OneToOne
    private Test test;

    @NotNull
    private Integer result;

    public User getUser() {
        return user;
    }

    public Test getTest() {
        return test;
    }

    public Integer getResult() {
        return result;
    }
}

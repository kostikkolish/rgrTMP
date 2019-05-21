package rgr.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(length = 60, unique = true)
    private String name;

    @NotNull
    @Column(length = 200)
    private String description;

    @OneToMany
    private List<Question> questions;

    @ManyToMany
    private List<User> accessedUsers;

    @OneToMany
    private List<Result> results;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

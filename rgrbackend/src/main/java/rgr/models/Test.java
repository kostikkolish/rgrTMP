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
    private List<TestQuestion> questions;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<TestQuestion> getQuestions() {
        return questions;
    }
}

package rgr.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Test test;

    @NotNull
    private Integer number;

    @NotNull
    @Column(length = 200)
    private String text;

    @OneToMany
    private List<Option> options;

    @ManyToOne
    private Option answer;

    public Integer getId() {
        return id;
    }

    public Test getTest() {
        return test;
    }

    public Integer getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public Option getAnswer() {
        return answer;
    }
}

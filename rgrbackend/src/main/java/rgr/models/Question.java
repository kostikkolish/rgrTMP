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
    @Min(value = 0)
    @Max(value = 10)
    private Integer number;

    @NotNull
    @Column(length = 200)
    private String text;

    @OneToMany
    private List<QuestionOption> options;

    @OneToOne
    private QuestionOption answer;
}

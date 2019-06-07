package rgr.Models;

import org.springframework.ui.Model;

import javax.persistence.*;
import java.util.List;

import static rgr.Constants.Attributes.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Test test;

    private Integer number;

    @Column(length = 200)
    private String text;

    @ManyToMany
    private List<QuestionOption> options;

    private Integer answer;

    public static void addQuestionAttributes(Model model, Question question) {
        if (question.getOptions() != null) {
            for (QuestionOption option : question.getOptions()) {
                switch (option.getOptionOrder()) {
                    case 1:
                        model.addAttribute(OPTION1_IN_CREATOR, option.getOptionText());
                        break;
                    case 2:
                        model.addAttribute(OPTION2_IN_CREATOR, option.getOptionText());
                        break;
                    case 3:
                        model.addAttribute(OPTION3_IN_CREATOR, option.getOptionText());
                        break;
                    case 4:
                        model.addAttribute(OPTION4_IN_CREATOR, option.getOptionText());
                        break;
                }
            }
        }
    }

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

    public List<QuestionOption> getOptions() {
        return options;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<QuestionOption> options) {
        this.options = options;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
}

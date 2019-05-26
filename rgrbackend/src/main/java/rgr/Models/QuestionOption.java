package rgr.Models;

import javax.persistence.*;

@Entity
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 100)
    private String optionText;

    public Integer getId() {
        return id;
    }

    public String getOptionText() {
        return optionText;
    }
}

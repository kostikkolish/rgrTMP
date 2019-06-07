package rgr.Models;

import javax.persistence.*;

@Entity
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 100)
    private String optionText;

    private Integer optionOrder;

    public static QuestionOption createOptionByTextAndOrder(String optionText, Integer order) {
        QuestionOption option = new QuestionOption();
        option.setOptionText(optionText);
        option.setOptionOrder(order);
        return option;
    }

    public Integer getId() {
        return id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Integer getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(Integer optionOrder) {
        this.optionOrder = optionOrder;
    }
}

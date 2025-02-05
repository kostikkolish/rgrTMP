package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.Models.QuestionOption;

public interface QuestionOptionsRepository extends CrudRepository<QuestionOption, Integer> {

    public QuestionOption getByOptionTextAndOptionOrder(String optionText, Integer optionOrder);
}

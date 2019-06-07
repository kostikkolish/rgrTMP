package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.Models.Question;
import rgr.Models.Test;

import java.util.List;

public interface QuestionsRepository extends CrudRepository<Question, Integer> {

    public Question getByNumberAndTest(Integer number, Test test);

    public Question getById(Integer id);

    public List<Question> getAllByTest(Test test);
}

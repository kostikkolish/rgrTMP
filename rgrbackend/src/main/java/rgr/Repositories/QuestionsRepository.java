package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.models.Question;

public interface QuestionsRepository extends CrudRepository<Question, Integer> {


}

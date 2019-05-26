package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.Models.Question;

public interface QuestionsRepository extends CrudRepository<Question, Integer> {


}

package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.models.Result;

public interface ResultsRepository extends CrudRepository<Result, Integer> {
}

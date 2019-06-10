package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.Models.Result;
import rgr.Models.Test;
import rgr.Models.User;

import java.util.List;

public interface ResultsRepository extends CrudRepository<Result, Integer> {

    public Result getByUserAndTest(User user, Test test);

    public List<Result> getAllByTest(Test test);
}

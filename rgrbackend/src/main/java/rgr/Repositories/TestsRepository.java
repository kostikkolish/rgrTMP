package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.models.Test;
import rgr.models.User;

import java.util.List;

public interface TestsRepository extends CrudRepository<Test, Integer> {

    public List<Test> getAllByAccessedUsersContains(User user);

    public List<Test> getAllByUserResultsExists();

    public Test getByName(String name);
}

package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.Models.Test;
import rgr.Models.User;

import java.util.List;

public interface TestsRepository extends CrudRepository<Test, Integer> {

    public Test getById(Integer id);

    public List<Test> getAllByAccessedUsersContains(User user);

    public List<Test> findAllByCreator_Username(String username);

    public List<Test> getAllByResultsIsNotNull();

    public Test getByName(String name);
}

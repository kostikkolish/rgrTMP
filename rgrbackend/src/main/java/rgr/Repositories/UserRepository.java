package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.Models.Role;
import rgr.Models.Test;
import rgr.Models.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {

    public List<User> findAllByAccessedTestsContains(Test test);

    public List<User> findAllByRole(Role role);

    public User findByUsername(String username);

    public User getById(Integer id);
}

package rgr.Repositories;

import org.springframework.data.repository.CrudRepository;
import rgr.models.Role;
import rgr.models.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    public List<User> getAllByRole(Role role);
}

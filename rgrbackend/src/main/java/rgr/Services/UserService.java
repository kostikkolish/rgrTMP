package rgr.Services;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rgr.Models.Role;
import rgr.Models.User;
import rgr.Repositories.UserRepository;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public void saveUser(User user, String role) throws ConstraintViolationException, Exception {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("Пользователь с таким логином уже существует");
        }

        if (role.equals("TESTER")){
            user.setRole(Collections.singleton(Role.TESTER));
        } else {
            user.setRole(Collections.singleton(Role.USER));
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
}

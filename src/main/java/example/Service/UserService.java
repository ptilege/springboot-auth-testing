package example.Service;

import example.Model.Register;
import example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(Register user) {
        userRepository.save(user);
    }

    public Register findByUsername(String username) {
        return userRepository.findByEmail(username);
    }
}

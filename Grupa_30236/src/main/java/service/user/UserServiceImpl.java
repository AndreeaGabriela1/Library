package service.user;

import model.User;
import repository.user.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean addUser(User user) {
        return userRepository.save(user);
    }

    // Alte metode pentru operațiile specifice utilizatorilor
}

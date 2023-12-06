package service.user;

import model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);
    List<User> getAllUsers();
}

package service.user;

import model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);
    List<User> getAllUsers();
    boolean deleteUser(long userId);
    boolean updateUser(long userId, User updatedUser);
    User findUser(long userId);

}

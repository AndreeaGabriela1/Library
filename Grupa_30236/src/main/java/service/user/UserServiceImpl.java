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
    @Override
    public User findUser(long userId) {
        User existingUser = userRepository.findById(userId);
        return existingUser; // Returnează true dacă utilizatorul cu ID-ul dat există
    }
    @Override
    public boolean updateUser(long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId);
        if (existingUser != null) {
            // Actualizează câmpurile utilizatorului existent
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRoles(updatedUser.getRoles());

            // Salvează modificările în baza de date
            return userRepository.update(existingUser);
        }
        return false; // În cazul în care utilizatorul nu există sau nu poate fi actualizat
    }
    @Override
    public boolean deleteUser(long userId) {
        User existingUser = userRepository.findById(userId);
        if (existingUser != null) {
            // Șterge utilizatorul din baza de date
            return userRepository.delete(existingUser);
        }
        return false; // În cazul în care utilizatorul nu există sau nu poate fi șters
    }


}

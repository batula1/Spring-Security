package ru.itmentor.spring.boot_security.demo.DAO;


import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    List<User> getUsers();
    void saveUser(User user);
    void removeUserById(long id);
    User getUserById(long id);
    Role getRoleById(long id);
    Set<Role> getRoles();
    void updateUser(long id, User updatedUser);
    List<User> findByLogin(String login);
}

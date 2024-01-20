package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.DAO.UserDao;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

     private  final UserDao userDao;
     private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserDao userDao, @Lazy PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {

        return userDao.getUsers();
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

     @Transactional
    @Override
    public void removeUserById(long id) {
    userDao.removeUserById(id);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public Role getRoleById(long id) {
        return userDao.getRoleById(id);
    }

    @Override
    public Set<Role> getRoles() {
        return userDao.getRoles();
    }

    @Override
    public Set<Role> getRole(Set<String> roleId) {
        Set<Role> roles = new HashSet<>();
        for (String id : roleId) {
            roles.add(getRoleById(Long.parseLong(id)));
        }
        return roles;
    }

    @Transactional
    @Override
    public void updateUser(long id, User updatedUser) {
        userDao.updateUser(id,updatedUser);
    }

    @Override
    public List<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<User> users = findByLogin(email);

        if (users.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }

        User user = users.get(0); // Выберите пользователя, который подходит вашим требованиям

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
        );
    }
}

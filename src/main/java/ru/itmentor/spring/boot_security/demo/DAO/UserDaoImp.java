package ru.itmentor.spring.boot_security.demo.DAO;



import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public List<User> getUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        if(user != null){
            entityManager.remove(user);
        }

    }

    @Override
    public User getUserById(long id) {
        User user = entityManager.find(User.class,id);

        return user;
    }

    @Override
    public Role getRoleById(long id) {
        Role role = entityManager.find(Role.class, id);
        return role;
    }

    @Override
    public Set<Role> getRoles() {
        TypedQuery<Role> query = entityManager.createQuery("SELECT u FROM Role u", Role.class);
        return new HashSet<>(query.getResultList());
    }


    @Override
    public User findByLogin(String login) {
        String query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :login";
        return entityManager.createQuery(query, User.class)
                .setParameter("login", login)
                .getSingleResult();
    }
}
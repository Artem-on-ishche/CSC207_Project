package data_access.persistence;

import model.User;
import use_case.login.LoginDataAccessInterface;
import use_case.signup.SignupDataAccessInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Optional;

public class UserDao implements AutoCloseable, LoginDataAccessInterface, SignupDataAccessInterface {

    private final EntityManagerFactory entityManagerFactory;

    public UserDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("OutfitManager");
    }

    public void closeEntityManagerFactory() {
        this.entityManagerFactory.close();
    }

    public void save(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(UserEntity.fromUser(user));
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public boolean existsByName(String username) {
        return get(username).isPresent();
    }

    public Optional<User> get(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        UserEntity userEntity = entityManager.find(UserEntity.class, username);

        entityManager.close();

        return Optional.ofNullable(UserEntity.toUser(userEntity));
    }

    public void updateUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.merge(UserEntity.fromUser(user));
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public void deleteUser(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserEntity userEntity = entityManager.find(UserEntity.class, username);

        if (userEntity != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(userEntity);
            entityManager.getTransaction().commit();
        }

        entityManager.close();
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}

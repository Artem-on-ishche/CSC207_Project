package data_access.persistence;

import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Optional;

public class UserDao {

    private final EntityManagerFactory entityManagerFactory;

    public UserDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("OutfitManager");
    }

    public void closeEntityManagerFactory() {
        this.entityManagerFactory.close();
    }

    public void saveUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(UserEntity.fromUser(user));
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public boolean existsByUsername(String username) {
        return getByUsername(username).isPresent();
    }

    public Optional<User> getByUsername(String username) {
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

    public void deleteUser(User user) {
        UserEntity userEntity = UserEntity.fromUser(user);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(userEntity) ? userEntity : entityManager.merge(userEntity));
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public static void main(String[] args) {
        var userDao = new UserDao();
        var user = userDao.getByUsername("test");

        userDao.deleteUser(user.get());

        System.out.println(userDao.getByUsername("test").isPresent());
    }
}

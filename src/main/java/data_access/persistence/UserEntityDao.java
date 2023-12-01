package data_access.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Optional;

public class UserEntityDao {

    private final EntityManagerFactory entityManagerFactory;

    public UserEntityDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("OutfitManager");
    }

    public void closeEntityManagerFactory() {
        this.entityManagerFactory.close();
    }

    public void saveUser(UserEntity userEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(userEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserEntity user = entityManager.find(UserEntity.class, username);
        entityManager.close();
        return Optional.ofNullable(user);
    }

    public void updateUser(UserEntity userEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(userEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteUser(UserEntity userEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(userEntity) ? userEntity : entityManager.merge(userEntity));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void main(String[] args) {
        var userDao = new UserEntityDao();
        var user = userDao.getUserByUsername("test");

        System.out.println(user.get().getUsername());
    }
}

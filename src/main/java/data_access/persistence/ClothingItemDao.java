package data_access.persistence;

import data_access.FileImageCreator;
import model.ClothingItem;
import model.ClothingType;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class ClothingItemDao implements AutoCloseable {

    private final EntityManagerFactory entityManagerFactory;

    public ClothingItemDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("OutfitManager");
    }

    public void closeEntityManagerFactory() {
        this.entityManagerFactory.close();
    }

    public Long saveClothingItem(ClothingItem clothingItem, String ownerUsername) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        var ownerEntity = entityManager.find(UserEntity.class, ownerUsername);
        var owner = UserEntity.toUser(ownerEntity);
        var clothingItemEntity = ClothingItemEntity.fromClothingItemAndOwner(clothingItem, owner);

        entityManager.getTransaction().begin();
        entityManager.persist(clothingItemEntity);
        entityManager.getTransaction().commit();
        entityManager.close();

        return clothingItemEntity.getId();
    }

    public Optional<ClothingItem> getClothingItemById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ClothingItemEntity clothingItemEntity = entityManager.find(ClothingItemEntity.class, id);
        entityManager.close();

        return Optional.ofNullable(ClothingItemEntity.toClothingItem(clothingItemEntity));
    }

    public void updateClothingItem(ClothingItem clothingItem, String ownerUsername) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        var ownerEntity = entityManager.find(UserEntity.class, ownerUsername);
        var owner = UserEntity.toUser(ownerEntity);
        var clothingItemEntity = ClothingItemEntity.fromClothingItemAndOwner(clothingItem, owner);

        entityManager.getTransaction().begin();
        entityManager.merge(clothingItemEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteClothingItem(ClothingItem clothingItem, String ownerUsername) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        var ownerEntity = entityManager.find(UserEntity.class, ownerUsername);
        var owner = UserEntity.toUser(ownerEntity);
        var clothingItemEntity = ClothingItemEntity.fromClothingItemAndOwner(clothingItem, owner);

        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(clothingItemEntity) ? clothingItemEntity : entityManager.merge(clothingItemEntity));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void main(String[] args) {
        var imageCreator = new FileImageCreator();
        var dao = new ClothingItemDao();

        var image = imageCreator.fromImageSrc("docs/img.png");
        var item = new ClothingItem(null, "name", image, ClothingType.HEAD, 1, Optional.empty());
        var owner = new User("test", "test");
//        var entity = ClothingItemEntity.fromClothingItemAndOwner(item, owner);

        var id = dao.saveClothingItem(item, owner.getUsername());
        var retrievedItem = dao.getClothingItemById(id);

        System.out.println(retrievedItem);
    }

    @Override
    public void close() throws Exception {
        closeEntityManagerFactory();
    }
}


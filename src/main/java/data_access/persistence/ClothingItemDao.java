package data_access.persistence;

import model.ClothingItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;


public class ClothingItemDao implements AutoCloseable {
    public static final String IMAGES_DIRECTORY = "images";

    private final EntityManagerFactory entityManagerFactory;

    public ClothingItemDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("OutfitManager");

        createImagesDirectory();
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

    public void updateClothingItem(ClothingItem updatedClothingItem) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        var savedClothingItemEntity = entityManager.find(ClothingItemEntity.class, updatedClothingItem.id());
        var updatedClothingItemEntity = savedClothingItemEntity.toBuilder()
                .name(updatedClothingItem.name())
                .minimumAppropriateTemperature(updatedClothingItem.minimumAppropriateTemperature())
                .description(updatedClothingItem.description().orElse(null))
                .build();

        entityManager.getTransaction().begin();
        entityManager.merge(updatedClothingItemEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteClothingItem(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var clothingItemEntity = entityManager.find(ClothingItemEntity.class, id);

        if (clothingItemEntity != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(clothingItemEntity);
            entityManager.getTransaction().commit();
        }

        entityManager.close();
    }

    @Override
    public void close() throws Exception {
        closeEntityManagerFactory();
        deleteImagesDirectory();
    }

    private void createImagesDirectory() {
        new File(IMAGES_DIRECTORY).mkdirs();
    }

    private void deleteImagesDirectory() throws IOException {
        FileUtils.cleanDirectory(new File(IMAGES_DIRECTORY));
    }
}


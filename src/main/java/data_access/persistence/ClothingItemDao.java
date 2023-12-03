package data_access.persistence;

import data_access.FileImageCreator;
import model.ClothingItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

        saveImageToFile(clothingItemEntity);

        entityManager.getTransaction().begin();
        entityManager.persist(clothingItemEntity);
        entityManager.getTransaction().commit();
        entityManager.close();

        return clothingItemEntity.getId();
    }

    public List<ClothingItem> getClothingItemsByUser(String username) {
        String sql = "SELECT * FROM clothing_item WHERE username = :username ORDER BY clothing_item_id";

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery(sql, ClothingItemEntity.class);
        query.setParameter("username", username);

        var resultList = (List<ClothingItemEntity>) query.getResultList();
        return resultList.stream()
                .map(ClothingItemEntity::toClothingItem)
                .toList();
    }


    public Optional<ClothingItem> getClothingItemById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ClothingItemEntity clothingItemEntity = entityManager.find(ClothingItemEntity.class, id);
        entityManager.close();

        return Optional.ofNullable(ClothingItemEntity.toClothingItem(clothingItemEntity));
    }

    public void updateClothingItem(ClothingItem updatedClothingItem) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        var savedClothingItemEntity = entityManager.find(ClothingItemEntity.class, updatedClothingItem.getId());
        var updatedClothingItemEntity = savedClothingItemEntity.toBuilder()
                .name(updatedClothingItem.getName())
                .minimumAppropriateTemperature(updatedClothingItem.getMinimumAppropriateTemperature())
                .description(updatedClothingItem.getDescription().orElse(null))
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

    private void saveImageToFile(ClothingItemEntity clothingItemEntity) {
        FileImageCreator.saveToFile(clothingItemEntity.getImageData(), getImageEntityFilename(clothingItemEntity));
    }

    static String getImageEntityFilename(ClothingItemEntity clothingItemEntity) {
        return IMAGES_DIRECTORY + "/" + clothingItemEntity.getId() + " - " + clothingItemEntity.getName() + ".png";
    }

    private void createImagesDirectory() {
        new File(IMAGES_DIRECTORY).mkdirs();
    }

    private void deleteImagesDirectory() throws IOException {
        FileUtils.cleanDirectory(new File(IMAGES_DIRECTORY));
    }
}


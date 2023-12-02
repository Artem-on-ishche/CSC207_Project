package data_access.persistence;

import data_access.FileImageCreator;
import model.ClothingItem;
import model.ClothingType;
import model.Image;
import model.User;
import org.junit.jupiter.api.*;
import use_case.create_wardrobe.ImageCreator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClothingItemDaoTest {
    private static final User owner = new User("test", "test");
    private static final String itemName1 = "test item";
    private static final String itemName2 = "test item 2";
    private static final UserDao userDao = new UserDao();
    private static final ClothingItemDao clothingItemDao = new ClothingItemDao();
    private static final ImageCreator imageCreator = new FileImageCreator();
    private static final Image image = imageCreator.fromImageSrc("docs/img.png");

    @BeforeAll
    static void setUp() {
        userDao.saveUser(owner);
    }

    @AfterAll
    static void tearDown() throws Exception {
        userDao.deleteUser(owner.getUsername());
        userDao.closeEntityManagerFactory();
        clothingItemDao.close();
    }

    @Test
    void givenManySequentialOperations_thenAllCorrect() {
        // create
        var item = new ClothingItem(null, itemName1, image, ClothingType.HEAD, 1, Optional.empty());
        var id = clothingItemDao.saveClothingItem(item, owner.getUsername());

        // read
        var queriedItem = clothingItemDao.getClothingItemById(id);
        assertTrue(queriedItem.isPresent());
        assertEquals(queriedItem.get().getName(), itemName1);

        // update
        item = new ClothingItem(id, itemName2, image, ClothingType.HEAD, 1, Optional.empty());
        clothingItemDao.updateClothingItem(item);

        queriedItem = clothingItemDao.getClothingItemById(id);
        assertTrue(queriedItem.isPresent());
        assertEquals(queriedItem.get().getName(), itemName2);

        // delete
        clothingItemDao.deleteClothingItem(id);
        queriedItem = clothingItemDao.getClothingItemById(id);
        assertFalse(queriedItem.isPresent());
    }

    @Test
    void givenMultipleItemsForOneUser_thenGetAllIsCorrect() {
        var clothingItems = List.of(
                new ClothingItem(null, itemName1, image, ClothingType.HEAD, 1, Optional.empty()),
                new ClothingItem(null, itemName2, image, ClothingType.LOWER_BODY, -5, Optional.empty())
        );

        clothingItems.forEach(clothingItem ->
                clothingItem.setId(clothingItemDao.saveClothingItem(clothingItem, owner.getUsername())));

        var queriedClothingItems = clothingItemDao.getClothingItemsByUser(owner.getUsername());

        assertEquals(clothingItems, queriedClothingItems);
    }
}
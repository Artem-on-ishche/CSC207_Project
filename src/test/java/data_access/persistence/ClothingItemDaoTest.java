package data_access.persistence;

import data_access.FileImageCreator;
import model.ClothingItem;
import model.ClothingType;
import model.Image;
import model.User;
import org.junit.jupiter.api.*;
import use_case.create_wardrobe.ImageCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClothingItemDaoTest {
    private static final User owner = new User("test", "test");
    private static final ClothingItemDao clothingItemDao = new ClothingItemDao();
    private static final ImageCreator imageCreator = new FileImageCreator();
    private static final Image image = imageCreator.fromImageSrc("docs/img.png");


    @AfterAll
    static void tearDown() throws Exception {
        clothingItemDao.close();
    }

    @Test
    void givenManySequentialOperations_AllCorrect() {
        // create
        var item = new ClothingItem(null, "test item", image, ClothingType.HEAD, 1, Optional.empty());

//        var id = clothingItemDao.saveClothingItem(item, owner.getUsername());
//        var retrievedItem = dao.getClothingItemById(id);
//
//        System.out.println(retrievedItem);
//
//
//        // create
////        var clothingItem = new
//
//        var user = new User(username, password1);
//        userDao.saveUser(user);
//        assertTrue(userDao.existsByUsername(username));
//
//        // read
//        var queriedUser = userDao.getByUsername(username);
//        assertTrue(queriedUser.isPresent());
//        assertEquals(queriedUser.get().getPassword(), password1);
//
//        // update
//        user = new User(username, password2);
//        userDao.updateUser(user);
//
//        queriedUser = userDao.getByUsername(username);
//        assertTrue(queriedUser.isPresent());
//        assertEquals(queriedUser.get().getPassword(), password2);
//
//        // delete
//        userDao.deleteUser(username);
//        queriedUser = userDao.getByUsername(username);
//        assertFalse(queriedUser.isPresent());
//        assertFalse(userDao.existsByUsername(username));
    }
}
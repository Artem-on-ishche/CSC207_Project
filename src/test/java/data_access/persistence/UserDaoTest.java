package data_access.persistence;

import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private static final UserDao userDao = new UserDao();
    private static final String username = "test";
    private static final String password1 = "test";
    private static final String password2 = "test new";

    @AfterAll
    static void tearDown() throws Exception {
        userDao.close();
    }

    @Test
    void givenManySequentialOperations_AllCorrect() {
        // create
        var user = new User(username, password1);
        userDao.saveUser(user);
        assertTrue(userDao.existsByUsername(username));

        // read
        var queriedUser = userDao.getByUsername(username);
        assertTrue(queriedUser.isPresent());
        assertEquals(queriedUser.get().getPassword(), password1);

        // update
        user = new User(username, password2);
        userDao.updateUser(user);

        queriedUser = userDao.getByUsername(username);
        assertTrue(queriedUser.isPresent());
        assertEquals(queriedUser.get().getPassword(), password2);

        // delete
        userDao.deleteUser(username);
        queriedUser = userDao.getByUsername(username);
        assertFalse(queriedUser.isPresent());
        assertFalse(userDao.existsByUsername(username));
    }
}
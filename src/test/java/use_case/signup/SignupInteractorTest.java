package use_case.signup;

import business_rules.PasswordEncryptionService;
import model.User;
import org.junit.Before;
import org.junit.Test;


import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class SignupInteractorTest {

    private SignupDataAccessInterface DataAccess;

    private PasswordEncryptionService EncryptionService;

    private SignupInteractor signupInteractor;


    @Before
    public void setUp() throws NoSuchPaddingException, NoSuchAlgorithmException {
        DataAccess = new SignupDataAccessInterface() {
            @Override
            public boolean existsByName(String identifier) {
                return false;
            }

            @Override
            public void save(User user) {

            }
        };
        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {

            }

            @Override
            public void prepareFailView(String error) {

            }
        };


        EncryptionService = new PasswordEncryptionService();

        signupInteractor = new SignupInteractor(DataAccess, presenter, EncryptionService);
    }

    @Test
    public void testSuccessfulSignup() {
        // Arrange
        SignupInputData input = new SignupInputData("newUser", "password", "password");

        assertFalse(DataAccess.existsByName("newUser"));

        signupInteractor.execute(input);
        assertTrue(DataAccess.existsByName("newUser"));


    }

    @Test
    public void testUserAlreadyExists() {
        // Arrange
        SignupInputData input = new SignupInputData("existingUser", "password", "password");

        assertTrue(DataAccess.existsByName("existingUser"));
    }

    @Test
    public void testPasswordMismatch() {
        // Arrange
        SignupInputData input = new SignupInputData("newUser", "password", "differentPassword");

        assertFalse(DataAccess.existsByName("newUser"));

        signupInteractor.execute(input);

        assertFalse(DataAccess.existsByName("newUser"));

    }

    @Test
    public void testPasswordEncryption() {
        // Arrange

        String password = "password";
        String encryptedPassword = EncryptionService.encryptMessage(password);

        // Act
        String result = EncryptionService.encryptMessage(password);

        // Assert
        assertEquals(encryptedPassword, result);
    }
}

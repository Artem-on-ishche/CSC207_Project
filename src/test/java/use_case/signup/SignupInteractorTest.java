package use_case.signup;

import business_rules.PasswordEncryptionService;
import model.User;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SignupInteractorTest {

    private SignupDataAccessInterface DataAccess;

    private PasswordEncryptionService EncryptionService;

    private SignupInteractor signupInteractor;



    @Before
    public void setUp() throws NoSuchPaddingException, NoSuchAlgorithmException {
        DataAccess = new SignupDataAccessInterface() {
            private List<String> usernames = new ArrayList<>();
            @Override
            public boolean existsByName(String identifier) {
                return usernames.contains(identifier);
            }

            @Override
            public void save(User user) {
                usernames.add(user.getUsername());
            }
        };
        SignupOutputBoundary mockPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {

            }

            @Override
            public void prepareFailView(String error) {

            }
        };


        EncryptionService = new PasswordEncryptionService();

        signupInteractor = new SignupInteractor(DataAccess, mockPresenter, EncryptionService);
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

        SignupInputData input = new SignupInputData("existingUser", "password", "password");
        signupInteractor.execute(input);
        assertTrue(DataAccess.existsByName("existingUser"));

        SignupInputData same_input = new SignupInputData("existingUser", "password", "password");



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

package use_case.login;

import business_rules.PasswordEncryption;
import model.User;
import business_rules.PasswordEncryptionService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Simple implementations of the required interfaces for testing purposes
class TestDataAccess implements LoginDataAccessInterface {
    User user = new User("username", "encryptedPassword");

    TestDataAccess(User user) {
        this.user = user;
    }

    @Override
    public void save(User user) {

    }

    public Optional<User> get(String username) {
        if (username.equals("username"))
            return Optional.ofNullable(user);
        else
            return Optional.empty();
    }
}

class TestPresenter implements LoginOutputBoundary {
    private String result;

    @Override
    public void prepareSuccessView(LoginOutputData data) {
        result = "Success";
    }

    @Override
    public void prepareFailView(String message) {
        result = "Failure";
    }

    String getResult() {
        return result;
    }
}

class TestEncryptionService implements PasswordEncryption{
    private final String encrypted;

    TestEncryptionService(String encrypted) {
        this.encrypted = encrypted;
    }

    @Override
    public String encryptMessage(String message) {
        return encrypted;
    }
}

class LoginInteractorTest {
    private static final User user = new User("username", "encryptedPassword");
    TestDataAccess dataAccess = new TestDataAccess(user);
    TestPresenter presenter = new TestPresenter();
//    TestEncryptionService encryptionService = new TestEncryptionService("encryptedPassword");

    LoginInteractor loginInteractor = new LoginInteractor(dataAccess, presenter, encryptionService);
    private static final PasswordEncryption encryptionService = message -> message;

    @Test
    void testSuccessfulLogin() {
        // When
        loginInteractor.execute(new LoginInputData("username", "encryptedPassword"));

        // Then
        assertEquals("Success", presenter.getResult());
    }

    @Test
    void testIncorrectPassword() {

        // When
        loginInteractor.execute(new LoginInputData("username", "pfafafassword"));

        // Then
        assertEquals("Failure", presenter.getResult());
    }

    @Test
    void testNoAccountFound() {
        // When
        loginInteractor.execute(new LoginInputData("fadfa", "password"));

        // Then
        assertEquals("Failure", presenter.getResult());
    }
}


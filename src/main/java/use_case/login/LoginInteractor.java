package use_case.login;

import business_rules.PasswordEncryption;
import business_rules.PasswordEncryptionService;
import model.User;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class LoginInteractor implements LoginInputBoundary {
    final LoginDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;

    final PasswordEncryption passwordEncryption;

    public LoginInteractor(LoginDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary,
                           PasswordEncryption passwordEncryption) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
        this.passwordEncryption = passwordEncryption;
    }

    @Override
    public void execute(LoginInputData loginData) {
        String username = loginData.getUsername();
        String password = loginData.getPassword();
        String encrypted = passwordEncryption.encryptMessage(password);

        var optionalUser = userDataAccessObject.get(username);
        if (optionalUser.isEmpty()) {
            loginPresenter.prepareFailView(username + ": No account found.");
        } else {
            User user = optionalUser.get();
            String correctEncrypted = user.getPassword();

            if (!encrypted.equals(correctEncrypted)) {
                loginPresenter.prepareFailView("Incorrect password.");
            } else {
                LoginOutputData loginOutputData = new LoginOutputData(user.getUsername(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}

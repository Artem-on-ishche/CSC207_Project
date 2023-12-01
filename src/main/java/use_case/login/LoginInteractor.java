package use_case.login;

import entity.User;

public class LoginInteractor implements LoginInputBoundary {
    final LoginDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginData) {
        String username = loginData.getUsername();
        String password = loginData.getPassword();
        if (!userDataAccessObject.existsByUserame(username)) {
            loginPresenter.prepareFailView(username + ": No account found.");
        } else {
            String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password.");
            } else {

                User user = userDataAccessObject.get(loginData.getUsername());

                LoginOutputData loginOutputData = new LoginOutputData(user.getUsername(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }

}

package use_case.login;

import use_case.login.LoginDataOutput;

public interface LoginOutputBoundary {

    void prepareSuccessView(LoginDataOutput user);

    void prepareFailView(String error);
}

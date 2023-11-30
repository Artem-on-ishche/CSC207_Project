package use_case_login;

public interface LoginOutputBoundary {

    void prepareSuccessView(LoginDataOutput user);

    void prepareFailView(String error);
}

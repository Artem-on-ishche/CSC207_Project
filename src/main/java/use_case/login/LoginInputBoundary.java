package use_case.login;

import use_case.login.LoginDataInput;

public interface LoginInputBoundary {
    void execute(LoginDataInput LoginDataInput);
}

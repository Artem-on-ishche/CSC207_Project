package use_case.signup;

import model.User;
public interface SignupDataAccessInterface {
    boolean existsByName(String identifier);

    void save(User user);
}

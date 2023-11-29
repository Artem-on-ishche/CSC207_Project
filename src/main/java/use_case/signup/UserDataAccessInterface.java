package use_case.signup;

import entity.user;
public interface UserDataAccessInterface {
    boolean existsByName(String identifier);

    void save(User user);
}

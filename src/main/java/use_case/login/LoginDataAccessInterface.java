package use_case.login;

import model.User;

import java.util.Optional;

public interface LoginDataAccessInterface {


    void save(User user);

    Optional<User> get(String username);
}

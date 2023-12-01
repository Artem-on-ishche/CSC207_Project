package use_case.login;

import entity.User;

import java.util.Optional;

public interface LoginDataAccessInterface {


    void save(User user);

    Optional<User> get(String username);
}

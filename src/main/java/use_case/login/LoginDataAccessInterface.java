package use_case.login;

import entity.User;
public interface LoginDataAccessInterface {

    boolean existsByUserame(String identifier);

    void save(User user);

    User get(String username);
}

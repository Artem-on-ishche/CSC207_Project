package use_case_login;

import entity.User;
public interface LoginDataAccessInterface {

    boolean existsByName(String identifier);

    void save(User user);

    User get(String username);
}

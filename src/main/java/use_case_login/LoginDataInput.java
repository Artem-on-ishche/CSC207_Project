package use_case_login;

public class LoginDataInput {
    final private String username;
    final private String password;

    public LoginDataInput(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }
}

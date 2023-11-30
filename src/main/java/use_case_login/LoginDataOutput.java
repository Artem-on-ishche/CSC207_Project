package use_case_login;

public class LoginDataOutput {
    private final String username;
    private final boolean useCaseFailed;

    public LoginDataOutput(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }
}

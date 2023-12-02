package use_case.signup;

public class SignupOutputData {
    private final String username;
    private String creationTime;

    private boolean useCaseFailed;

    public SignupOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}

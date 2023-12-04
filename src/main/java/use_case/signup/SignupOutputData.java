package use_case.signup;

import lombok.Getter;

@Getter
public class SignupOutputData {
    private final String username;

    public SignupOutputData(String username) {
        this.username = username;
    }

}

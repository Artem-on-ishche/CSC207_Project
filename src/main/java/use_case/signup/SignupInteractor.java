package use_case.signup;
import business_rules.PasswordEncryptionService;
import model.User;

public class SignupInteractor implements SignupInputBoundary {
    final SignupDataAccessInterface userDataAccessObject;
    final SignupOutputBoundary userPresenter;
    final PasswordEncryptionService passwordEncryptionService;

    public SignupInteractor(SignupDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            PasswordEncryptionService passwordEncryptionService) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.passwordEncryptionService = passwordEncryptionService;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        String newUser = signupInputData.getUsername();
        String newPassword = signupInputData.getPassword();
        String encrypted = passwordEncryptionService.encryptMessage(newPassword);
        if (userDataAccessObject.existsByName(newUser)) {
            userPresenter.prepareFailView("User already exists.");
        } else if (!newPassword.equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        } else {

            User user = new User(newUser, encrypted);
            userDataAccessObject.save(user);

            SignupOutputData signupOutputData = new SignupOutputData(user.getUsername());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

}

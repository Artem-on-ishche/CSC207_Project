package entity;

public class PasswordValidationService implements PasswordValidator{
    public boolean passwordIsValid(String password){
        return password != null && password.length() >= 8;
    }
}

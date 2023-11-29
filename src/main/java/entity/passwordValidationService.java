package entity;

public class passwordValidationService implements PasswordValidator{
    public boolean IsValid(String password){
        return password != null && password.length() >= 8;
    }
}

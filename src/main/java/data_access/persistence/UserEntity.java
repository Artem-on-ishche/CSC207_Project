package data_access.persistence;

import entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static UserEntity fromUser(User user) {
        return new UserEntity(user.getUsername(), user.getPassword());
    }

    public static User toUser(UserEntity userEntity) {
        if (userEntity == null)
            return null;

        return new User(userEntity.username, userEntity.password);
    }
}

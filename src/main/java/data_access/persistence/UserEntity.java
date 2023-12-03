package data_access.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ClothingItemEntity> clothingItemEntities;

    public UserEntity(String username, String password) {
        this.username = username;
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

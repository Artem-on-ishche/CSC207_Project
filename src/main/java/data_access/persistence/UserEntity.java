package data_access.persistence;

import entity.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
class UserEntity {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<ClothingItemEntity> clothingItemEntities;

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

    public List<ClothingItemEntity> getClothingItemEntities() {
        return clothingItemEntities;
    }

    public void setClothingItemEntities(List<ClothingItemEntity> clothingItemEntities) {
        this.clothingItemEntities = clothingItemEntities;
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

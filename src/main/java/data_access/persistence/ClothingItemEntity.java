package data_access.persistence;

import data_access.FileImageCreator;
import model.ClothingItem;
import model.ClothingType;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "clothing_item")
public class ClothingItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clothing_item_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "clothing_type")
    private String clothingType;

    @Column(name = "minimum_appropriate_temperature")
    private int minimumAppropriateTemperature;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "username")
    private UserEntity userEntity;

    public ClothingItemEntity() {
    }

    public ClothingItemEntity(Long id, String name, byte[] imageData, String clothingType, int minimumAppropriateTemperature, String description) {
        this.id = id;
        this.name = name;
        this.imageData = imageData;
        this.clothingType = clothingType;
        this.minimumAppropriateTemperature = minimumAppropriateTemperature;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getClothingType() {
        return clothingType;
    }

    public void setClothingType(String clothingType) {
        this.clothingType = clothingType;
    }

    public int getMinimumAppropriateTemperature() {
        return minimumAppropriateTemperature;
    }

    public void setMinimumAppropriateTemperature(int minimumAppropriateTemperature) {
        this.minimumAppropriateTemperature = minimumAppropriateTemperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    UserEntity getUserEntity() {
        return userEntity;
    }

    void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    static ClothingItem toClothingItem(ClothingItemEntity clothingItemEntity) {
        var filename = clothingItemEntity.id + " " + clothingItemEntity.name + ".jpg";
        var image = FileImageCreator.convertByteArrayAndSaveToFile(clothingItemEntity.imageData, filename);

        return new ClothingItem(
                clothingItemEntity.id,
                clothingItemEntity.name,
                image,
                ClothingType.valueOf(clothingItemEntity.clothingType),
                clothingItemEntity.minimumAppropriateTemperature,
                Optional.ofNullable(clothingItemEntity.description)
        );
    }
}

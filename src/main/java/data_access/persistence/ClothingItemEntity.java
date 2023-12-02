package data_access.persistence;

import data_access.FileImageCreator;
import model.ClothingItem;
import model.ClothingType;
import model.User;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
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
    @Type(type = "org.hibernate.type.BinaryType")
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

    static ClothingItemEntity fromClothingItemAndOwner(ClothingItem clothingItem, User owner) {
        return new ClothingItemEntity(
                clothingItem.id(),
                clothingItem.name(),
                clothingItem.image().imageData(),
                clothingItem.clothingType().toString(),
                clothingItem.minimumAppropriateTemperature(),
                clothingItem.description().orElse(null),
                UserEntity.fromUser(owner)
        );
    }

    static ClothingItem toClothingItem(ClothingItemEntity clothingItemEntity) {
        if (clothingItemEntity == null)
            return null;

        var filename = ClothingItemDao.IMAGES_DIRECTORY + "/" + clothingItemEntity.id + " " + clothingItemEntity.name + ".png";
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

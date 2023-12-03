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

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "clothing_type", nullable = false)
    private String clothingType;

    @Column(name = "minimum_appropriate_temperature", nullable = false)
    private int minimumAppropriateTemperature;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private UserEntity userEntity;

    static ClothingItemEntity fromClothingItemAndOwner(ClothingItem clothingItem, User owner) {
        return new ClothingItemEntity(
                clothingItem.getId(),
                clothingItem.getName(),
                clothingItem.getImage().getImageData(),
                clothingItem.getClothingType().toString(),
                clothingItem.getMinimumAppropriateTemperature(),
                clothingItem.getDescription().orElse(null),
                UserEntity.fromUser(owner)
        );
    }

    static ClothingItem toClothingItem(ClothingItemEntity clothingItemEntity) {
        if (clothingItemEntity == null)
            return null;

        var filename = ClothingItemDao.getImageEntityFilename(clothingItemEntity);
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

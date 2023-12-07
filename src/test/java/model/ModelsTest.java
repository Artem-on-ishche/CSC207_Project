package model;

import data_access.persistence.FileImageCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.create_wardrobe.ImageCreator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ModelsTest {
    @Nested
    class ClothingItemTest {
        private static ClothingItem clothingItem;

        @BeforeAll
        static void setUp() {
            clothingItem = new ClothingItem(
                    1L,
                    "TestClothing",
                    new Image(new File("testImage"), new byte[]{}),
                    ClothingType.HEAD,
                    10,
                    Optional.of("Test Description")
            );
        }

        @Test
        void testIsAppropriateForTemperature() {
            assertTrue(clothingItem.isAppropriateForTemperature(15));
            assertFalse(clothingItem.isAppropriateForTemperature(5));
        }

        @Test
        void testEquals() {
            ClothingItem sameClothingItem = new ClothingItem(
                    1L,
                    "TestClothing",
                    new Image(clothingItem.getImage().getImageFile(), new byte[]{}),
                    ClothingType.HEAD,
                    10,
                    Optional.of("Test Description")
            );
            ClothingItem differentClothingItem = new ClothingItem(
                    2L,
                    "DifferentClothing",
                    new Image(new File("testImage"), new byte[]{}),
                    ClothingType.INNER_UPPER_BODY,
                    20,
                    Optional.of("Different Description")
            );

            assertEquals(clothingItem, sameClothingItem);
            assertNotEquals(clothingItem, differentClothingItem);
        }

        @Test
        void testSetDifferentFields() {
            ClothingItem clothingItem1 = new ClothingItem(
                    2L,
                    "DifferentClothing",
                    new Image(new File("testImage"), new byte[]{}),
                    ClothingType.INNER_UPPER_BODY,
                    20,
                    Optional.of("Different Description")
            );

            clothingItem1.setId(10L);
            clothingItem1.setClothingType(ClothingType.HEAD);
            clothingItem1.setDescription(Optional.of("Description"));
            clothingItem1.setImage(new Image(new File("testImage"), new byte[]{}));
            clothingItem1.setMinimumAppropriateTemperature(10);
            clothingItem1.setName("dasha");
        }

        @Test
        void testHashCode() {
            ClothingItem sameClothingItem = new ClothingItem(
                    1L,
                    "TestClothing",
                    new Image(new File("testImage"), new byte[]{}),
                    ClothingType.HEAD,
                    10,
                    Optional.of("Test Description")
            );
            assertEquals(clothingItem.hashCode(), sameClothingItem.hashCode());
        }

        @Test
        void testToString() {
            String expectedToString = "ClothingItem[id=1, name=TestClothing, image=Image{imageFile=testImage, imageData=[]}, " +
                    "clothingType=HEAD, minimumAppropriateTemperature=10, " +
                    "description=Optional[Test Description]]";

            assertEquals(expectedToString, clothingItem.toString());
        }
    }

    @Nested
    class ImageTest {
        private static class MockImageIO {
            static BufferedImage read(ByteArrayInputStream input) {
                return new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            }

            static boolean write(BufferedImage image, String formatName, ByteArrayOutputStream output) throws IOException {
                return ImageIO.write(image, formatName, output);
            }
        }

        private static final ImageCreator imageCreator = new FileImageCreator();
        public static final Image IMAGE = imageCreator.fromImageSrc("docs/img.png");

        @Test
        void testGetScaledInstance() throws IOException {
            Image newImage = IMAGE.getScaledInstance(100, 100, 1);

            assertEquals(newImage.getImageFile(), IMAGE.getImageFile());
        }
    }

    @Nested
    class OutfitTest {
        public static final ClothingItem CLOTHING_ITEM = new ClothingItem(
                1L,
                "TestClothing",
                new Image(new File("testImage"), new byte[]{}),
                ClothingType.HEAD,
                10,
                Optional.of("Test Description")
        );

        @Test
        void testEquals() {
            Map<ClothingType, ClothingItem> items1 = new HashMap<>();
            items1.put(ClothingType.HEAD, CLOTHING_ITEM);
            Outfit outfit1 = new Outfit(items1, true);

            Map<ClothingType, ClothingItem> items2 = new HashMap<>();
            items2.put(ClothingType.HEAD, CLOTHING_ITEM);
            Outfit outfit2 = new Outfit(items2, true);

            assertEquals(outfit1, outfit2);
        }

        @Test
        void testNotEquals() {
            Map<ClothingType, ClothingItem> items1 = new HashMap<>();
            items1.put(ClothingType.HEAD, CLOTHING_ITEM);
            Outfit outfit1 = new Outfit(items1, false);

            Map<ClothingType, ClothingItem> items2 = new HashMap<>();
            items2.put(ClothingType.INNER_UPPER_BODY, CLOTHING_ITEM);
            Outfit outfit2 = new Outfit(items2, false);

            assertNotEquals(outfit1, outfit2);
        }

        @Test
        void testHashCode() {
            Map<ClothingType, ClothingItem> items = new HashMap<>();
            items.put(ClothingType.HEAD, CLOTHING_ITEM);
            Outfit outfit = new Outfit(items, false);

            int expectedHashCode = Objects.hash(items, false);
            assertEquals(expectedHashCode, outfit.hashCode());
        }

        @Test
        void testToString() {
            Map<ClothingType, ClothingItem> items = new HashMap<>();
            items.put(ClothingType.INNER_UPPER_BODY, CLOTHING_ITEM);
            Outfit outfit = new Outfit(items, true);

            String expectedToString = "Outfit{clothingItems=" + items + ", isUmbrellaRequired=true}";
            assertEquals(expectedToString, outfit.toString());
        }
    }

}

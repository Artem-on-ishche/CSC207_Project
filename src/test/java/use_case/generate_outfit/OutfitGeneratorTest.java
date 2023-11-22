package use_case.generate_outfit;

import entity.ClothingItem;
import entity.ClothingType;
import entity.Outfit;
import entity.Weather;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public class OutfitGeneratorTest {
    private static final OutfitGenerator outfitGenerator = new OutfitGenerator(clothingItems -> clothingItems.get(0));

    private static final Weather basicWeather = new Weather(15, false);
    private static final List<ClothingItem> basicWardrobe = new ArrayList<>();

    @BeforeAll
    static void setup() {
        for (int i = 1; i <= 10; i++) {
            for (var clothingType : ClothingType.values()) {
                basicWardrobe.add(new ClothingItem(
                        (long) i,
                        "item",
                        null,
                        clothingType,
                        -i,
                        Optional.empty()
                ));
            }
        }
    }

    @Test
    public void givenNoClothingItems_shouldThrowException() {
        assertThrows(OutfitGenerationException.class, () -> outfitGenerator.generateOutfit(basicWeather, List.of()));
    }

    static Stream<Arguments> categoriesTestParameters() {
        return Stream.of(
                Arguments.arguments(40, List.of(false, false, true, false, false, true, false, true)),
                Arguments.arguments(10, List.of(false, false, true, true, false, true, false, true)),
                Arguments.arguments(2.5, List.of(true, false, true, true, true, true, false, true)),
                Arguments.arguments(-5, List.of(true, true, true, true, true, true, true, true))
        );
    }

    @ParameterizedTest
    @MethodSource("categoriesTestParameters")
    public void givenDifferentTemperatures_shouldHaveDifferentCategoriesPresent(
            double temperature,
            List<Boolean> categoryRequirements
    ) {
        try {
            var outfit = outfitGenerator.generateOutfit(new Weather(temperature, false), basicWardrobe);

            var categoriesPresent = outfit.getClothingItems().keySet();
            for (int i = 0; i < categoryRequirements.size(); i++) {
                var category = ClothingType.values()[i];
                var isPresent = categoriesPresent.contains(category);
                var isExpected = categoryRequirements.get(i);

                assertEquals(isExpected, isPresent);
            }
        } catch (OutfitGenerationException e) {
            fail("Shouldn't throw an exception");
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, - 2, -3, -4, -5, -6, -7, -8, -9, -10})
    public void givenDifferentTemperatures_shouldReturnDifferentItems(int temperature) {
        try {
            var outfit = outfitGenerator.generateOutfit(new Weather(temperature, false), basicWardrobe);

            for (var clothingItem : outfit.getClothingItems().values()) {
                assertEquals(-temperature, clothingItem.id());
            }
        } catch (OutfitGenerationException e) {
            fail("Shouldn't throw an exception");
        }
    }

    @Test
    public void shouldCallClothingItemSelectionStrategy() {
        outfitGenerator.clothingItemSelectionStrategy = clothingItems -> {
            assertTrue(true, "Function correctly called");
            return clothingItems.get(0);
        };

        try {
            outfitGenerator.generateOutfit(basicWeather, basicWardrobe);
        } catch (OutfitGenerationException e) {
            fail("Shouldn't throw an exception");
        }
    }
}

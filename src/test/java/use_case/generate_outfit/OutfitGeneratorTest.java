package use_case.generate_outfit;

import model.ClothingType;
import model.Weather;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static use_case.generate_outfit.OutfitGenerationConstants.*;

import java.util.List;
import java.util.stream.Stream;


public class OutfitGeneratorTest {

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
            var outfit = outfitGenerator.generateOutfit(new Weather(temperature, false), getBasicWardrobe());

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
    @ValueSource(ints = {-1, -2, -3, -4, -5, -6, -7, -8, -9, -10})
    public void givenDifferentTemperatures_shouldReturnDifferentItems(int temperature) {
        try {
            var outfit = outfitGenerator.generateOutfit(new Weather(temperature, false), getBasicWardrobe());

            for (var clothingItem : outfit.getClothingItems().values()) {
                assertEquals(-temperature, clothingItem.getId());
            }
        } catch (OutfitGenerationException e) {
            fail("Shouldn't throw an exception");
        }
    }

    @Test
    public void givenItIsRaining_shouldRequireUmbrella() {
        try {
            var outfit = outfitGenerator.generateOutfit(new Weather(basicWeather.temperature(), true), getBasicWardrobe());

            assertTrue(outfit.isUmbrellaRequired());
        } catch (OutfitGenerationException e) {
            fail("Shouldn't throw an exception");
        }
    }

    @Test
    public void shouldCallClothingItemSelectionStrategy() {
        var outfitGeneratorWithSpyStrategy = new OutfitGenerator(clothingItems -> {
            assertTrue(true, "Function correctly called");
            return clothingItems.get(0);
        });

        try {
            outfitGeneratorWithSpyStrategy.generateOutfit(basicWeather, getBasicWardrobe());
        } catch (OutfitGenerationException e) {
            fail("Shouldn't throw an exception");
        }
    }
}

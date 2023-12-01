package use_case.create_wardrobe;

import entity.ClothingItem;
import entity.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WardrobeGeneratingTest {

    private static final ClothingItem defaultClothingItem = new ClothingItem(
            null,
            "item",
            null,
            ClothingType.INNER_UPPER_BODY,
            20,
            Optional.of("Default Description")
    );
    private static final CreateInputData defaultInputData = new CreateInputData(defaultClothingItem);
    private static CreateDataAccess createDataAccess;
    private static CreateOutputBoundary createPresenter;
    private static ClothingIdentificationService clothingIdentificationService;
    private static CreateInteractor createInteractor;

    @AfterEach
    void cleanup() {
        createDataAccess = null;
        createPresenter = null;
        clothingIdentificationService = null;
        createInteractor = null;
    }

    @Test
    void givenValidInput_shouldPrepareSuccessView() {
        createDataAccess = new CreateDataAccess() {
            @Override
            public void addClothingItem(ClothingItem clothingItem) {
                assertEquals(defaultClothingItem, clothingItem);
            }
        };

        createPresenter = new CreateOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateOutputData outputData) {
                assertEquals(defaultClothingItem, outputData.editClothingItem());
                assertFalse(outputData.useCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail();
            }
        };

        clothingIdentificationService = image -> ClothingType.INNER_UPPER_BODY.toString();

        createInteractor = new CreateInteractor(
                createDataAccess,
                createPresenter,
                clothingIdentificationService
        );

        createInteractor.execute(defaultInputData);
    }

    @Test
    void givenInvalidInput_shouldPrepareFailView() {
        createDataAccess = new CreateDataAccess() {
            @Override
            public void addClothingItem(ClothingItem clothingItem) {
                fail("Should not be called");
            }
        };

        createPresenter = new CreateOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateOutputData outputData) {
                fail("Should not be called");
            }

            @Override
            public void prepareFailView(String error) {
                assertTrue(error.contains("Error adding clothing item"));
            }
        };

        clothingIdentificationService = image -> {
            throw new RuntimeException("Identification error");
        };

        createInteractor = new CreateInteractor(
                createDataAccess,
                createPresenter,
                clothingIdentificationService
        );

        createInteractor.execute(defaultInputData);
    }
}

package use_case.create_wardrobe;

import model.ClothingItem;
import model.ClothingType;
import model.Image;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CreateInteractorTest {
    private static CreateDataAccess createDataAccess;
    private static CreateOutputBoundary createPresenter;
    private static ClothingIdentificationService clothingIdentificationService;
    private static CreateInteractor createInteractor;
    private static ImageCreator createImage;

    @AfterEach
    void cleanup() {
        createDataAccess = null;
        createPresenter = null;
        clothingIdentificationService = null;
        createInteractor = null;
        createImage = null;
    }

    @Test
    void thrownException_shouldPrepareFailView() {
        createDataAccess = new CreateDataAccess() {
            @Override
            public Long addClothingItem(ClothingItem clothingItem, String username) {
                fail("Should not be called");
                return null;
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
            throw new RuntimeException("Runtime exception thrown.");
        };

        createImage = src -> new Image(new File(src), new byte[]{});

        createInteractor = new CreateInteractor(
                createDataAccess,
                createPresenter,
                clothingIdentificationService,
                createImage);

        CreateInputData invalidInputData = new CreateInputData(null,
                null, null, null, 0)
        ;

        createInteractor.execute(invalidInputData);
    }

    @Test
    void givenValidInput_shouldPrepareSuccessView() {
        final boolean[] wasEverythingCalled = {false, false};

        createDataAccess = new CreateDataAccess() {
            @Override
            public Long addClothingItem(ClothingItem clothingItem, String username) {
                wasEverythingCalled[0] = true;
                return null;
            }
        };

        createPresenter = new CreateOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateOutputData outputData) {
                assertFalse(outputData.useCaseFailed());
                wasEverythingCalled[1] = true;
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not be called");
            }
        };

        clothingIdentificationService = image -> ClothingType.LOWER_BODY;

        createImage = src -> new Image(new File(src), new byte[]{});

        createInteractor = new CreateInteractor(
                createDataAccess,
                createPresenter,
                clothingIdentificationService,
                createImage);

        CreateInputData validInputData = new CreateInputData("anna", "Boots", "\\image.jpg", Optional.of("this is a description"), 10);

        createInteractor.execute(validInputData);
    }
}
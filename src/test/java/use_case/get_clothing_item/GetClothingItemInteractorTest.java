package use_case.get_clothing_item;

import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class GetClothingItemInteractorTest {
    private static final ClothingItem CLOTHING_ITEM1 = new ClothingItem(1L, "Hat", null, ClothingType.HEAD, 1, Optional.empty());
    private static GetClothingItemOutputBoundary outputBoundary;
    private static GetClothingItemInteractor interactor;

    @AfterEach
    void tearDown() {
        outputBoundary = null;
        interactor = null;
    }

    @Test
    void givenCorrectData_shouldPrepareSuccessView() {
        GetClothingItemAccess dao = username -> Optional.of(CLOTHING_ITEM1);
        outputBoundary = new GetClothingItemOutputBoundary() {
            @Override
            public void prepareSuccessView(GetClothingItemOutputData outputData) {
                assertTrue(true);
            }

            @Override
            public void prepareFailView(String error) {
                fail();
            }
        };

        interactor = new GetClothingItemInteractor(dao, outputBoundary);
        interactor.execute(new GetClothingItemInputData(1L));
    }

    @Test
    void givenEmptyData_shouldPrepareSuccessView() {
        GetClothingItemAccess dao = username -> Optional.empty();

        outputBoundary = new GetClothingItemOutputBoundary() {
            @Override
            public void prepareSuccessView(GetClothingItemOutputData outputData) {
                fail();
            }

            @Override
            public void prepareFailView(String error) {assertTrue(true);}
        };

        interactor = new GetClothingItemInteractor(dao, outputBoundary);
        interactor.execute(new GetClothingItemInputData(1L));
    }

    @Test
    void givenIncorrectData_shouldPrepareFailureView() {
        GetClothingItemAccess dao = username -> {
            throw new RuntimeException();
        };

        outputBoundary = new GetClothingItemOutputBoundary() {
            @Override
            public void prepareSuccessView(GetClothingItemOutputData outputData) {
                fail();
            }

            @Override
            public void prepareFailView(String error) {
                assertTrue(true);
            }
        };

        interactor = new GetClothingItemInteractor(dao, outputBoundary);
        interactor.execute(new GetClothingItemInputData(10L));
    }
}

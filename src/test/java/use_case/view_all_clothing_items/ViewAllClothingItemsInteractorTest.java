package use_case.view_all_clothing_items;

import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ViewAllClothingItemsInteractorTest {

    private static final String correctUsername = "correct";
    private static final String incorrectUsername = "incorrect";
    private static final List<ClothingItem> clothingItems = List.of(
            new ClothingItem(1L, "item1", null, ClothingType.HEAD, 1, Optional.empty()),
            new ClothingItem(2L, "item2", null, ClothingType.LOWER_BODY, -5, Optional.empty())
    );
    private static final AllItemsOfUserDataAccess dao = username -> {
        if (!username.equals(correctUsername))
            throw new IllegalArgumentException();

        return clothingItems;
    };

    private static ViewAllClothingItemsOutputBoundary outputBoundary;
    private static ViewAllClothingItemsInteractor interactor;

    @AfterEach
    void tearDown() {
        outputBoundary = null;
        interactor = null;
    }

    @Test
    void givenCorrectData_shouldPrepareSuccessView() {
        outputBoundary = new ViewAllClothingItemsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewAllClothingItemsOutputData outputData) {
                assertTrue(true);
            }

            @Override
            public void prepareFailView(String error) {
                fail();
            }
        };

        interactor = new ViewAllClothingItemsInteractor(dao, outputBoundary);
        interactor.execute(new ViewAllClothingItemsInputData(correctUsername));
    }

    @Test
    void givenEmptyData_shouldPrepareSuccessView() {
        AllItemsOfUserDataAccess dao = username -> {
            if (!username.equals(correctUsername))
                throw new IllegalArgumentException();

            return new ArrayList<>();
        };
        outputBoundary = new ViewAllClothingItemsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewAllClothingItemsOutputData outputData) {
                assertTrue(true);
            }

            @Override
            public void prepareFailView(String error) {
                fail();
            }
        };

        interactor = new ViewAllClothingItemsInteractor(dao, outputBoundary);
        interactor.execute(new ViewAllClothingItemsInputData(correctUsername));
    }

    @Test
    void givenIncorrectData_shouldPrepareFailureView() {
        outputBoundary = new ViewAllClothingItemsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewAllClothingItemsOutputData outputData) {
                fail();
            }

            @Override
            public void prepareFailView(String error) {
                assertTrue(true);
            }
        };

        interactor = new ViewAllClothingItemsInteractor(dao, outputBoundary);
        interactor.execute(new ViewAllClothingItemsInputData(incorrectUsername));
    }
}
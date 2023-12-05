package use_case.update_clothing_item;

import lombok.Getter;
import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateInteractorTest {
    private static final ClothingItem CLOTHING_ITEM1 = new ClothingItem(1L, "invalid", null, null, 1, Optional.empty());
    private static final ClothingItem CLOTHING_ITEM2 = new ClothingItem(2L, "valid", null, ClothingType.LOWER_BODY, -5, Optional.empty());

    private static final UpdateDataAccess updateDataAccess = clothingItem -> {
        if (clothingItem.getClothingType() == null)
            throw new IllegalArgumentException();
    };
    private static UpdateInputBoundary interactor;

    @AfterEach
    void tearDown() {
        interactor = null;
    }

    @Test
    void givenInvalidData_shouldPrepareFailView() {
        @Getter
        class MockPresenter implements UpdateOutputBoundary {
            private String error;

            @Override
            public void prepareSuccessView(UpdateOutputData outputData) {
                fail("Unexpected call to prepareSuccessView");
            }

            @Override
            public void prepareFailView(String error) {
                this.error = error;
            }
        };

        MockPresenter mockPresenter = new MockPresenter();
        interactor = new UpdateInteractor(mockPresenter, updateDataAccess);

        interactor.execute(new UpdateInputData(CLOTHING_ITEM1));
        assertEquals("Error updating clothing item with id " + CLOTHING_ITEM1.getId() +".", mockPresenter.getError());
    }

    @Test
    void givenValidData_shouldPrepareSuccessView() {
        @Getter
        class MockPresenter implements UpdateOutputBoundary {
            private UpdateOutputData updateOutputData;

            @Override
            public void prepareSuccessView(UpdateOutputData outputData) {
                this.updateOutputData = outputData;
            }

            @Override
            public void prepareFailView(String error) {
                fail("Unexpected call to prepareFailView");
            }
        };

        MockPresenter mockPresenter = new MockPresenter();
        interactor = new UpdateInteractor(mockPresenter, updateDataAccess);

        interactor.execute(new UpdateInputData(CLOTHING_ITEM2));
        assertEquals(CLOTHING_ITEM2, mockPresenter.getUpdateOutputData().updatedClothingItem());
    }
}

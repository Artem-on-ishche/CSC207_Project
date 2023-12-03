package use_case.get_item_by_id;

import model.ClothingItem;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static junit.framework.Assert.*;

public class GettingItemByIdTest {
    @Test
    void testGetClothingItem_Success() {
        long itemId = 1L;
        ClothingItem expectedClothingItem = new ClothingItem(itemId, "item", null, null, 20, Optional.of("test"));

        class MockDataAccess implements GetItemByIdDataAccess {
            @Override
            public ClothingItem getClothingItemById(Long itemId) {
                return expectedClothingItem;
            }
        }

        class MockPresenter implements GetItemByIdOutputBoundary {
            private GetItemByIdOutputData outputData;

            @Override
            public void prepareSuccessView(GetItemByIdOutputData outputData) {
                this.outputData = outputData;
            }

            @Override
            public void prepareFailView(String error) {
                fail("Unexpected call to prepareFailView");
            }

            public GetItemByIdOutputData getOutputData() {
                return outputData;
            }
        }

        GetItemByIdDataAccess mockDataAccess = new MockDataAccess();
        MockPresenter mockPresenter = new MockPresenter();

        GetItemByIdInputData inputData = new GetItemByIdInputData(itemId);
        GetItemByIdInteractor interactor = new GetItemByIdInteractor(mockDataAccess, mockPresenter);

        interactor.execute(inputData);

        assertEquals(expectedClothingItem, mockPresenter.getOutputData().clothingItem());
        assertFalse(mockPresenter.getOutputData().useCaseFailed());
    }

}

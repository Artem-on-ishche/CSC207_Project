package use_case.delete_clothing_item;

import lombok.Getter;
import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import use_case.update_clothing_item.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteInteractorTest {
    private static final ClothingItem CLOTHING_ITEM1 = new ClothingItem(1L, "Hat", null, ClothingType.HEAD, 1, Optional.empty());
    private static final ClothingItem CLOTHING_ITEM2 = new ClothingItem(2L, "Trousers", null, ClothingType.LOWER_BODY, -5, Optional.empty());
    private static final ClothingItem CLOTHING_ITEM3 = new ClothingItem(3L, "T-Shirt", null, ClothingType.INNER_UPPER_BODY, 10, Optional.empty());

    private static List<ClothingItem> CLOTHING_ITEMS = new ArrayList<>(List.of(CLOTHING_ITEM1, CLOTHING_ITEM2, CLOTHING_ITEM3));

    private static final DeleteDataAccess deleteDataAccess = clothingItemId -> {
        if (clothingItemId == null)
            throw new NullPointerException();

        if(clothingItemId == -1000)
            throw new RuntimeException();

        boolean isValidId = false;
        for (ClothingItem clothingItem : CLOTHING_ITEMS) {
            if (clothingItem.getId().equals(clothingItemId)) {
                isValidId = true;
                CLOTHING_ITEMS.remove(clothingItem);
                break;
            }
        }
        if(!isValidId)
            throw new IllegalArgumentException();
    };
    private static DeleteInputBoundary interactor;

    @AfterEach
    void tearDown() {
        interactor = null;
    }

    @Test
    void givenNullId_shouldPrepareFailView() {
        @Getter
        class MockPresenter implements DeleteOutputBoundary {
            private String error;

            @Override
            public void prepareSuccessView(DeleteOutputData outputData) {
                fail("Unexpected call to prepareSuccessView");
            }

            @Override
            public void prepareFailView(String error) {
                this.error = error;
            }
        };

        MockPresenter mockPresenter = new MockPresenter();
        interactor = new DeleteInteractor(mockPresenter, deleteDataAccess);

        interactor.execute(new DeleteInputData(null));

        assertEquals( 3, CLOTHING_ITEMS.size());
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM1));
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM2));
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM3));

        assertEquals("Error deleting clothing item. Id cannot be null.", mockPresenter.getError());
    }

    @Test
    void givenInvalidId_shouldPrepareFailView() {
        @Getter
        class MockPresenter implements DeleteOutputBoundary {
            private String error;

            @Override
            public void prepareSuccessView(DeleteOutputData outputData) {
                fail("Unexpected call to prepareSuccessView");
            }

            @Override
            public void prepareFailView(String error) {
                this.error = error;
            }
        };

        MockPresenter mockPresenter = new MockPresenter();
        interactor = new DeleteInteractor(mockPresenter, deleteDataAccess);

        interactor.execute(new DeleteInputData(5L));

        assertEquals( 3, CLOTHING_ITEMS.size());
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM1));
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM2));
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM3));

        assertEquals("Error deleting clothing item. There is no item with " + 5L + " id in the wardrobe.", mockPresenter.getError());
    }

    @Test
    void ifDataAccessThrowsException_shouldPrepareFailView() {
        @Getter
        class MockPresenter implements DeleteOutputBoundary {
            private String error;

            @Override
            public void prepareSuccessView(DeleteOutputData outputData) {
                fail("Unexpected call to prepareSuccessView");
            }

            @Override
            public void prepareFailView(String error) {
                this.error = error;
            }
        };

        MockPresenter mockPresenter = new MockPresenter();
        interactor = new DeleteInteractor(mockPresenter, deleteDataAccess);

        interactor.execute(new DeleteInputData(-1000L));

        assertEquals( 3, CLOTHING_ITEMS.size());
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM1));
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM2));
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM3));

        assertEquals("Error deleting clothing item with id " + -1000L + ".", mockPresenter.getError());
    }

    @Test
    void givenValidData_shouldPrepareSuccessView() {
        @Getter
        class MockPresenter implements DeleteOutputBoundary {
            private DeleteOutputData deleteOutputData;

            @Override
            public void prepareSuccessView(DeleteOutputData outputData) {
                this.deleteOutputData = outputData;
            }

            @Override
            public void prepareFailView(String error) {
                fail("Unexpected call to prepareFailView");
            }
        };

        MockPresenter mockPresenter = new MockPresenter();
        interactor = new DeleteInteractor(mockPresenter, deleteDataAccess);

        interactor.execute(new DeleteInputData(CLOTHING_ITEM2.getId()));

        assertEquals(CLOTHING_ITEMS.size(), 2);
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM1));
        assertFalse(CLOTHING_ITEMS.contains(CLOTHING_ITEM2));
        assertTrue(CLOTHING_ITEMS.contains(CLOTHING_ITEM3));
    }
}

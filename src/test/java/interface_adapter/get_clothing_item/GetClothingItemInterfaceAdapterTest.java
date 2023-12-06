package interface_adapter.get_clothing_item;

import interface_adapter.ViewManagerModel;
import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.get_clothing_item.GetClothingItemInputBoundary;
import use_case.get_clothing_item.GetClothingItemOutputData;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class GetClothingItemInterfaceAdapterTest {
    public static final ClothingItem CLOTHING_ITEM = new ClothingItem(1L, "Hat", null, ClothingType.HEAD, 1, Optional.empty());

    @Nested
    class GetClothingItemControllerTest {
        @Test
        public void givenData_shouldExecuteAndNotThrowExceptions() {
            AtomicBoolean wasInteractorCalled = new AtomicBoolean(false);

            GetClothingItemInputBoundary mockGetClothingItemUseCaseInteractor = inputData -> {
                wasInteractorCalled.set(true);
                System.out.println("Entered execute method in mocked Interactor");
            };

            GetClothingItemController getClothingItemController = new GetClothingItemController(mockGetClothingItemUseCaseInteractor);

            assertDoesNotThrow(() -> {
                getClothingItemController.execute(1L);
            });

            assertTrue(wasInteractorCalled.get(), "The interactor should have been called");

            assertDoesNotThrow(() -> {
                getClothingItemController.execute(null);
            });
        }
    }

    @Nested
    class GetClothingItemPresenterTest {
        private ViewManagerModel mockViewManagerModel;
        private GetClothingItemViewModel mockGetClothingItemViewModel;

        @AfterEach
        public void tearDown() {
            mockGetClothingItemViewModel = null;
            mockViewManagerModel = null;
        }

        @Test
        public void shouldPrepareSuccessViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfViewAllItems = {false};

            class MockGetClothingItemViewModel extends interface_adapter.get_clothing_item.GetClothingItemViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfViewAllItems[0] = true;
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockGetClothingItemViewModel = new MockGetClothingItemViewModel();
            GetClothingItemPresenter getClothingItemPresenter = new GetClothingItemPresenter(mockGetClothingItemViewModel, mockViewManagerModel);
            GetClothingItemOutputData mockOutputData = new GetClothingItemOutputData(Optional.of(CLOTHING_ITEM), false);

            assertDoesNotThrow(() ->
                    getClothingItemPresenter.prepareSuccessView(mockOutputData)
            );
            assertEquals(mockViewManagerModel.getActiveView(), "get item");
            assertEquals(mockGetClothingItemViewModel.getState().getClothingItem(), CLOTHING_ITEM);
            assertTrue(hasChangedStateOfViewAllItems[0]);
        }

        @Test
        public void shouldPrepareFailViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfGetClothingItem = {false};

            class MockGetClothingItemViewModel extends interface_adapter.get_clothing_item.GetClothingItemViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfGetClothingItem[0] = true;
                }

            }

            mockViewManagerModel = new ViewManagerModel();
            mockGetClothingItemViewModel = new MockGetClothingItemViewModel();
            mockViewManagerModel.setActiveView("get item");

            GetClothingItemPresenter getClothingItemPresenter = new GetClothingItemPresenter(mockGetClothingItemViewModel, mockViewManagerModel);

            assertDoesNotThrow(() -> {
                getClothingItemPresenter.prepareFailView("Error message");
            });
            assertEquals(mockViewManagerModel.getActiveView(), "get item");
            assertEquals(mockGetClothingItemViewModel.getState().getGetItemError(), "Error message");
            assertTrue(hasChangedStateOfGetClothingItem[0]);
        }
    }

    @Nested
    class GetClothingItemStateTest {
        @Test
        public void getReturnsWhatSetInputted() {
            GetClothingItemState getClothingItemState = new GetClothingItemState();

            // Test the getter and setter methods
            getClothingItemState.setClothingItem(CLOTHING_ITEM);
            assertEquals(CLOTHING_ITEM, getClothingItemState.getClothingItem());

            getClothingItemState.setGetItemError("error");
            assertEquals("error", getClothingItemState.getGetItemError());
        }
    }

    @Nested
    class GetClothingItemViewModelTest {
        @Test
        public void firePropertyChangedShouldCallRegisteredListenersAdded() {
            AtomicReference<Object> registeredListenerEventState = new AtomicReference<>();

            GetClothingItemViewModel getClothingItemViewModel = new GetClothingItemViewModel();
            assertDoesNotThrow(() -> getClothingItemViewModel.addPropertyChangeListener(evt -> registeredListenerEventState.set(evt.getNewValue())));

            GetClothingItemState getClothingItemState = new GetClothingItemState();
            getClothingItemState.setGetItemError("error");
            getClothingItemViewModel.setState(getClothingItemState);
            assertDoesNotThrow(getClothingItemViewModel::firePropertyChanged);

            assertEquals(registeredListenerEventState.get(), getClothingItemState);
        }

        @Test
        public void getStateReturnsWhatSetStateInputted() {
            GetClothingItemViewModel getClothingItemViewModel = new GetClothingItemViewModel();
            GetClothingItemState getClothingItemState = new GetClothingItemState();
            getClothingItemState.setGetItemError("error");
            getClothingItemViewModel.setState(getClothingItemState);

            assertNotNull(getClothingItemViewModel.getState());
            assertEquals(getClothingItemState.getGetItemError(), "error");
        }
    }
}

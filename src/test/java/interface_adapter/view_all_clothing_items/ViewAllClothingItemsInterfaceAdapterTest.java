package interface_adapter.view_all_clothing_items;

import interface_adapter.ViewManagerModel;
import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.view_all_clothing_items.ViewAllClothingItemsInputBoundary;
import use_case.view_all_clothing_items.ViewAllClothingItemsOutputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class ViewAllClothingItemsInterfaceAdapterTest {
    public static final ClothingItem CLOTHING_ITEM1 = new ClothingItem(1L, "Hat", null, ClothingType.HEAD, 1, Optional.empty());
    public static final ClothingItem CLOTHING_ITEM2 = new ClothingItem(2L, "Trousers", null, ClothingType.LOWER_BODY, -5, Optional.empty());
    public static final ClothingItem CLOTHING_ITEM3 = new ClothingItem(3L, "T-Shirt", null, ClothingType.INNER_UPPER_BODY, 10, Optional.empty());

    public static List<ClothingItem> CLOTHING_ITEMS = new ArrayList<>(List.of(CLOTHING_ITEM1, CLOTHING_ITEM2, CLOTHING_ITEM3));

    @Nested
    class ViewAllClothingItemsControllerTest {
        @Test
        public void givenData_shouldExecuteAndNotThrowExceptions() {
            AtomicBoolean wasInteractorCalled = new AtomicBoolean(false);

            ViewAllClothingItemsInputBoundary mockViewAllClothingItemsUseCaseInteractor = inputData -> {
                wasInteractorCalled.set(true);
                System.out.println("Entered execute method in mocked Interactor");
            };

            ViewAllClothingItemsController viewAllClothingItemsController = new ViewAllClothingItemsController(mockViewAllClothingItemsUseCaseInteractor);

            assertDoesNotThrow(() -> {
                viewAllClothingItemsController.execute("anna");
            });

            assertTrue(wasInteractorCalled.get(), "The interactor should have been called");

            assertDoesNotThrow(() -> {
                viewAllClothingItemsController.execute(null);
            });
        }
    }

    @Nested
    class ViewAllClothingItemsPresenterTest {
        private ViewManagerModel mockViewManagerModel;
        private ViewAllClothingItemsViewModel mockViewAllClothingItemsViewModel;

        @AfterEach
        public void tearDown() {
            mockViewAllClothingItemsViewModel = null;
            mockViewManagerModel = null;
        }

        @Test
        public void shouldPrepareSuccessViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfViewAllClothingItems = {false};

            class MockViewAllClothingItemsViewModel extends interface_adapter.view_all_clothing_items.ViewAllClothingItemsViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfViewAllClothingItems[0] = true;
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockViewAllClothingItemsViewModel = new MockViewAllClothingItemsViewModel();
            ViewAllClothingItemsPresenter viewAllClothingItemsPresenter = new ViewAllClothingItemsPresenter(mockViewManagerModel, mockViewAllClothingItemsViewModel);
            ViewAllClothingItemsOutputData mockOutputData = new ViewAllClothingItemsOutputData(CLOTHING_ITEMS);

            assertDoesNotThrow(() ->
                    viewAllClothingItemsPresenter.prepareSuccessView(mockOutputData)
            );
            assertEquals(mockViewManagerModel.getActiveView(), "view all");
            assertEquals(mockViewAllClothingItemsViewModel.getState().getWardrobe(), CLOTHING_ITEMS);
            assertTrue(hasChangedStateOfViewAllClothingItems[0]);
        }

        @Test
        public void shouldPrepareFailViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfViewAllClothingItems = {false};

            class MockViewAllClothingItemsViewModel extends interface_adapter.view_all_clothing_items.ViewAllClothingItemsViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfViewAllClothingItems[0] = true;
                }

            }

            mockViewManagerModel = new ViewManagerModel();
            mockViewAllClothingItemsViewModel = new MockViewAllClothingItemsViewModel();
            mockViewManagerModel.setActiveView("view all");

            ViewAllClothingItemsPresenter viewAllClothingItemsPresenter = new ViewAllClothingItemsPresenter(mockViewManagerModel, mockViewAllClothingItemsViewModel);

            assertDoesNotThrow(() -> {
                viewAllClothingItemsPresenter.prepareFailView("Error message");
            });
            assertEquals(mockViewManagerModel.getActiveView(), "view all");
            assertEquals(mockViewAllClothingItemsViewModel.getState().getGetAllItemsError(), "Error message");
            assertTrue(hasChangedStateOfViewAllClothingItems[0]);
        }
    }

    @Nested
    class ViewAllClothingItemsStateTest {
        @Test
        public void getReturnsWhatSetInputted() {
            ViewAllClothingItemsState viewAllClothingItemsState = new ViewAllClothingItemsState();

            viewAllClothingItemsState.setWardrobe(CLOTHING_ITEMS);
            assertEquals(CLOTHING_ITEMS, viewAllClothingItemsState.getWardrobe());

            viewAllClothingItemsState.setGetAllItemsError("error");
            assertEquals("error", viewAllClothingItemsState.getGetAllItemsError());
        }
    }

    @Nested
    class ViewAllClothingItemsViewModelTest {
        @Test
        public void firePropertyChangedShouldCallRegisteredListenersAdded() {
            AtomicReference<Object> registeredListenerEventState = new AtomicReference<>();

            ViewAllClothingItemsViewModel viewAllClothingItemsViewModel = new ViewAllClothingItemsViewModel();
            assertDoesNotThrow(() -> viewAllClothingItemsViewModel.addPropertyChangeListener(evt -> registeredListenerEventState.set(evt.getNewValue())));

            ViewAllClothingItemsState viewAllClothingItemsState = new ViewAllClothingItemsState();
            viewAllClothingItemsState.setGetAllItemsError("error");
            viewAllClothingItemsViewModel.setState(viewAllClothingItemsState);
            assertDoesNotThrow(viewAllClothingItemsViewModel::firePropertyChanged);

            assertEquals(registeredListenerEventState.get(), viewAllClothingItemsState);
        }

        @Test
        public void getStateReturnsWhatSetStateInputted() {
            ViewAllClothingItemsViewModel viewAllClothingItemsViewModel = new ViewAllClothingItemsViewModel();
            ViewAllClothingItemsState viewAllClothingItemsState = new ViewAllClothingItemsState();
            viewAllClothingItemsState.setGetAllItemsError("error");
            viewAllClothingItemsViewModel.setState(viewAllClothingItemsState);

            assertNotNull(viewAllClothingItemsViewModel.getState());
            assertEquals(viewAllClothingItemsState.getGetAllItemsError(), "error");
        }
    }
}

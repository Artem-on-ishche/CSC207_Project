package interface_adapter.delete_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_all_items.ViewAllItemsState;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import use_case.delete_clothing_item.DeleteInputBoundary;
import use_case.delete_clothing_item.DeleteOutputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteInterfaceAdapterTest {
    @Nested
    class DeleteControllerTest {
        @Test
        public void givenValidData_shouldExecuteAndNotThrowExceptions() {
            AtomicBoolean wasInteractorCalled = new AtomicBoolean(false);

            DeleteInputBoundary mockDeleteUseCaseInteractor = inputData -> {
                wasInteractorCalled.set(true);
                System.out.println("Entered execute method in mocked Interactor");
            };

            DeleteController deleteController = new DeleteController(mockDeleteUseCaseInteractor);

            assertDoesNotThrow(() -> {
                deleteController.execute(1L);
            });

            assertTrue(wasInteractorCalled.get(), "The interactor should have been called");

            assertDoesNotThrow(() -> {
                deleteController.execute(null);
            });
        }
    }

    @Nested
    class DeletePresenterTest {
        private static final ClothingItem CLOTHING_ITEM1 = new ClothingItem(1L, "Hat", null, ClothingType.HEAD, 1, Optional.empty());
        private static final ClothingItem CLOTHING_ITEM2 = new ClothingItem(2L, "Trousers", null, ClothingType.LOWER_BODY, -5, Optional.empty());
        private static final ClothingItem CLOTHING_ITEM3 = new ClothingItem(3L, "T-Shirt", null, ClothingType.INNER_UPPER_BODY, 10, Optional.empty());

        private static List<ClothingItem> CLOTHING_ITEMS = new ArrayList<>(List.of(CLOTHING_ITEM1, CLOTHING_ITEM2, CLOTHING_ITEM3));
        private ViewManagerModel mockViewManagerModel;
        private DeleteViewModel mockDeleteViewModel;
        private ViewAllItemsViewModel mockViewAllItemsViewModel;

        @AfterEach
        public void tearDown() {
            mockDeleteViewModel = null;
            mockViewAllItemsViewModel = null;
            mockViewManagerModel = null;
        }

        @Test
        public void shouldPrepareSuccessViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfViewAllItems = {false};

            class MockDeleteViewModel extends interface_adapter.delete_clothing_item.DeleteViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of DeleteViewModel in this prepareSuccessView method.");
                }
            }

            class MockViewAllItemsViewModel extends interface_adapter.view_all_items.ViewAllItemsViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfViewAllItems[0] = true;
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockDeleteViewModel = new MockDeleteViewModel();
            DeleteState deleteState = new DeleteState();
            deleteState.setDeletedItem(2L);
            mockDeleteViewModel.setState(deleteState);
            mockViewAllItemsViewModel = new MockViewAllItemsViewModel();
            mockViewAllItemsViewModel.setState(new ViewAllItemsState(CLOTHING_ITEMS));
            DeletePresenter deletePresenter = new DeletePresenter(mockDeleteViewModel, mockViewManagerModel, mockViewAllItemsViewModel);
            DeleteOutputData mockOutputData = new DeleteOutputData();

            assertDoesNotThrow(() ->
                    deletePresenter.prepareSuccessView(mockOutputData)
            );
            assertEquals(mockViewManagerModel.getActiveView(), "view all");
            assertEquals(mockViewAllItemsViewModel.getState().getWardrobe().size(), 2);
            assertTrue(mockViewAllItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM1));
            assertFalse(mockViewAllItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM2));
            assertTrue(mockViewAllItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM3));
            assertTrue(hasChangedStateOfViewAllItems[0]);
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(longs = {10L, 5L, 0L})
        public void shouldPrepareFailViewAndNotThrowExceptions_ifIdToDeleteIsNullOrNotInsideTheList(Long id) {
            final boolean[] hasChangedStateOfDelete = {false};

            class MockDeleteViewModel extends interface_adapter.delete_clothing_item.DeleteViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfDelete[0] = true;
                }

            }

            class MockViewAllItemsViewModel extends interface_adapter.view_all_items.ViewAllItemsViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of ViewAllItemsViewModel in this prepareFailView method.");
                }

            }

            mockViewManagerModel = new ViewManagerModel();
            mockDeleteViewModel = new MockDeleteViewModel();
            DeleteState deleteState = new DeleteState();
            deleteState.setDeletedItem(id);
            mockDeleteViewModel.setState(deleteState);
            mockViewAllItemsViewModel = new MockViewAllItemsViewModel();
            mockViewAllItemsViewModel.setState(new ViewAllItemsState(CLOTHING_ITEMS));
            mockViewManagerModel.setActiveView("delete item");

            DeletePresenter deletePresenter = new DeletePresenter(mockDeleteViewModel, mockViewManagerModel, mockViewAllItemsViewModel);

            assertDoesNotThrow(() -> {
                deletePresenter.prepareFailView("Error message");
            });

            assertEquals(mockViewManagerModel.getActiveView(), "delete item");
            assertEquals(mockDeleteViewModel.getState().getDeleteError(), "Error message");

            assertEquals(mockViewAllItemsViewModel.getState().getWardrobe().size(), 3);
            assertTrue(mockViewAllItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM1));
            assertTrue(mockViewAllItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM2));
            assertTrue(mockViewAllItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM3));

            assertTrue(hasChangedStateOfDelete[0]);
        }
    }

    @Nested
    class DeleteStateTest {
        @Test
        public void getReturnsWhatSetInputted() {
            DeleteState deleteState = new DeleteState();

            deleteState.setDeletedItem(1L);
            assertEquals(1L, deleteState.getDeletedItemId());

            deleteState.setDeleteError("error");
            assertEquals("error", deleteState.getDeleteError());
        }
    }

    @Nested
    class DeleteViewModelTest {
        @Test
        public void firePropertyChangedShouldCallRegisteredListenersAdded() {
            AtomicReference<Object> registeredListenerEventState = new AtomicReference<>();

            DeleteViewModel deleteViewModel = new DeleteViewModel();
            assertDoesNotThrow(() -> deleteViewModel.addPropertyChangeListener(evt -> registeredListenerEventState.set(evt.getNewValue())));

            DeleteState deleteState = new DeleteState();
            deleteState.setDeletedItem(1L);
            deleteViewModel.setState(deleteState);
            assertDoesNotThrow(deleteViewModel::firePropertyChanged);

            assertEquals(registeredListenerEventState.get(), deleteState);
        }

        @Test
        public void getStateReturnsWhatSetStateInputted() {
            DeleteViewModel deleteViewModel = new DeleteViewModel();
            DeleteState deleteState = new DeleteState();
            deleteState.setDeletedItem(1L);
            deleteViewModel.setState(deleteState);

            assertNotNull(deleteViewModel.getState());
            assertEquals(deleteState.getDeletedItemId(), 1L);
        }
    }
}

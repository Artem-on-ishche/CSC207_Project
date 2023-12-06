package interface_adapter.update_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_clothing_item.GetClothingItemViewModel;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsState;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsViewModel;
import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import use_case.update_clothing_item.UpdateInputBoundary;
import use_case.update_clothing_item.UpdateOutputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateInterfaceAdapterTest {
    public static final ClothingItem CLOTHING_ITEM1 = new ClothingItem(1L, "Hat", null, ClothingType.HEAD, 1, Optional.empty());
    public static final ClothingItem CLOTHING_ITEM2 = new ClothingItem(2L, "Trousers", null, ClothingType.LOWER_BODY, -5, Optional.empty());
    public static final ClothingItem CLOTHING_ITEM_NEW = new ClothingItem(2L, "Pants", null, ClothingType.LOWER_BODY, -5, Optional.empty());
    public static final ClothingItem CLOTHING_ITEM3 = new ClothingItem(2L, "T-Shirt", null, ClothingType.INNER_UPPER_BODY, 10, Optional.empty());

    @Nested
    class UpdateControllerTest {
        @Test
        public void givenData_shouldExecuteAndNotThrowExceptions() {
            AtomicBoolean wasInteractorCalled = new AtomicBoolean(false);

            UpdateInputBoundary mockUpdateUseCaseInteractor = inputData -> {
                wasInteractorCalled.set(true);
                System.out.println("Entered execute method in mocked Interactor");
            };

            UpdateController updateController = new UpdateController(mockUpdateUseCaseInteractor);

            assertDoesNotThrow(() -> {
                updateController.execute(CLOTHING_ITEM1);
            });

            assertTrue(wasInteractorCalled.get(), "The interactor should have been called");

            assertDoesNotThrow(() -> {
                updateController.execute(null);
            });
        }
    }

    @Nested
    class UpdatePresenterTest {
        private static List<ClothingItem> CLOTHING_ITEMS = new ArrayList<>(List.of(CLOTHING_ITEM1, CLOTHING_ITEM2, CLOTHING_ITEM3));
        private ViewManagerModel mockViewManagerModel;
        private UpdateViewModel mockUpdateViewModel;
        private ViewAllClothingItemsViewModel mockViewAllClothingItemsViewModel;
        private GetClothingItemViewModel mockGetClothingItemViewModel;

        @AfterEach
        public void tearDown() {
            mockUpdateViewModel = null;
            mockViewAllClothingItemsViewModel = null;
            mockViewManagerModel = null;
        }

        @Test
        public void shouldPrepareSuccessViewAndNotThrowExceptions() {
            final boolean[] hasChangedState = {false, false, false};

            class MockUpdateViewModel extends interface_adapter.update_clothing_item.UpdateViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedState[0] = true;
                }
            }

            class MockGetClothingItemModel extends interface_adapter.get_clothing_item.GetClothingItemViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedState[1] = true;
                }
            }

            class MockViewAllClothingItemsViewModel extends ViewAllClothingItemsViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedState[2] = true;
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockUpdateViewModel = new MockUpdateViewModel();
            mockGetClothingItemViewModel = new MockGetClothingItemModel();
            mockViewAllClothingItemsViewModel = new MockViewAllClothingItemsViewModel();
            ViewAllClothingItemsState viewAllClothingItemsState = new ViewAllClothingItemsState();
            viewAllClothingItemsState.setWardrobe(CLOTHING_ITEMS);
            mockViewAllClothingItemsViewModel.setState(viewAllClothingItemsState);
            UpdatePresenter updatePresenter = new UpdatePresenter(mockViewManagerModel, mockUpdateViewModel, mockViewAllClothingItemsViewModel, mockGetClothingItemViewModel);
            UpdateOutputData mockOutputData = new UpdateOutputData(CLOTHING_ITEM_NEW);

            assertDoesNotThrow(() ->
                    updatePresenter.prepareSuccessView(mockOutputData)
            );

            assertEquals(mockUpdateViewModel.getState().getClothingItem(), CLOTHING_ITEM_NEW);
            assertEquals(mockGetClothingItemViewModel.getState().getClothingItem(), CLOTHING_ITEM_NEW);
            assertEquals(mockViewManagerModel.getActiveView(), "get item");
            assertEquals(mockViewAllClothingItemsViewModel.getState().getWardrobe().size(), 3);
            assertTrue(mockViewAllClothingItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM1));
            assertFalse(mockViewAllClothingItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM2));
            assertTrue(mockViewAllClothingItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM3));
            assertTrue(mockViewAllClothingItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM_NEW));
            assertArrayEquals(hasChangedState, new boolean[]{true, true, true});
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(longs = {10L, 5L, 0L})
        public void shouldPrepareFailViewAndNotThrowExceptions_ifIdToUpdateIsNullOrNotInsideTheList(Long id) {
            CLOTHING_ITEMS = new ArrayList<>(List.of(CLOTHING_ITEM1, CLOTHING_ITEM2, CLOTHING_ITEM3));
            final boolean[] hasChangedStateOfUpdate = {false};

            class MockUpdateViewModel extends interface_adapter.update_clothing_item.UpdateViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfUpdate[0] = true;
                }
            }

            class MockGetClothingItemModel extends interface_adapter.get_clothing_item.GetClothingItemViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of GetClothingItemViewModel in this prepareFailView method.");
                }
            }

            class MockViewAllClothingItemsViewModel extends ViewAllClothingItemsViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of ViewAllItemsViewModel in this prepareFailView method.");
                }

            }

            mockViewManagerModel = new ViewManagerModel();
            mockUpdateViewModel = new MockUpdateViewModel();
            mockGetClothingItemViewModel = new MockGetClothingItemModel();
            mockViewAllClothingItemsViewModel = new MockViewAllClothingItemsViewModel();
            ViewAllClothingItemsState viewAllClothingItemsState = new ViewAllClothingItemsState();
            viewAllClothingItemsState.setWardrobe(CLOTHING_ITEMS);
            mockViewAllClothingItemsViewModel.setState(viewAllClothingItemsState);
            mockViewManagerModel.setActiveView("update item");

            UpdatePresenter updatePresenter = new UpdatePresenter(mockViewManagerModel, mockUpdateViewModel, mockViewAllClothingItemsViewModel, mockGetClothingItemViewModel);

            assertDoesNotThrow(() -> {
                updatePresenter.prepareFailView("Error message");
            });

            assertEquals(mockViewManagerModel.getActiveView(), "update item");
            assertEquals(mockUpdateViewModel.getState().getUpdateError(), "Error message");

            assertEquals(mockViewAllClothingItemsViewModel.getState().getWardrobe().size(), 3);
            assertTrue(mockViewAllClothingItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM1));
            assertTrue(mockViewAllClothingItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM2));
            assertTrue(mockViewAllClothingItemsViewModel.getState().getWardrobe().contains(CLOTHING_ITEM3));

            assertTrue(hasChangedStateOfUpdate[0]);
        }
    }

    @Nested
    class UpdateStateTest {
        @Test
        public void getReturnsWhatSetInputted() {
            UpdateState updateState = new UpdateState();

            updateState.setClothingItem(CLOTHING_ITEM1);
            assertEquals(CLOTHING_ITEM1, updateState.getClothingItem());

            updateState.setUpdateError("error");
            assertEquals("error", updateState.getUpdateError());
        }
    }

    @Nested
    class UpdateViewModelTest {
        @Test
        public void firePropertyChangedShouldCallRegisteredListenersAdded() {
            AtomicReference<Object> registeredListenerEventState = new AtomicReference<>();

            UpdateViewModel updateViewModel = new UpdateViewModel();
            assertDoesNotThrow(() -> updateViewModel.addPropertyChangeListener(evt -> registeredListenerEventState.set(evt.getNewValue())));

            UpdateState updateState = new UpdateState();
            updateState.setClothingItem(CLOTHING_ITEM1);
            updateViewModel.setState(updateState);
            assertDoesNotThrow(updateViewModel::firePropertyChanged);

            assertEquals(registeredListenerEventState.get(), updateState);
        }

        @Test
        public void getStateReturnsWhatSetStateInputted() {
            UpdateViewModel updateViewModel = new UpdateViewModel();
            UpdateState updateState = new UpdateState();
            updateState.setClothingItem(CLOTHING_ITEM1);
            updateViewModel.setState(updateState);

            assertNotNull(updateViewModel.getState());
            assertEquals(updateState.getClothingItem(), CLOTHING_ITEM1);
        }
    }
}


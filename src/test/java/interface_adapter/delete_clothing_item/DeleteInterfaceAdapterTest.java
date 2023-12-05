package interface_adapter.delete_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_all_items.ViewAllItemsState;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.delete_clothing_item.DeleteInputBoundary;
import use_case.delete_clothing_item.DeleteOutputData;

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

//    @Nested
//    class DeletePresenterTest {
//        private ViewManagerModel mockViewManagerModel;
//        private DeleteViewModel mockDeleteViewModel;
//        private ViewAllItemsViewModel mockViewAllItemsViewModel;
//
//        @AfterEach
//        public void tearDown() {
//            mockDeleteViewModel = null;
//            mockViewAllItemsViewModel = null;
//            mockViewManagerModel = null;
//        }

//        @Test
//        public void givenValidData_prepareSuccessView() {
//            final boolean[] hasChangedStateOfViewAllItems = {false};
//
//            class MockDeleteViewModel extends interface_adapter.delete_clothing_item.DeleteViewModel {
//                @Override
//                public void firePropertyChanged() {
//                    fail("Should not change state of DeleteViewModel in this prepareSuccessView method.");
//                }
//            }
//
//            class MockViewAllItemsViewModel extends interface_adapter.view_all_items.ViewAllItemsViewModel {
//                @Override
//                public void firePropertyChanged() {
//                    hasChangedStateOfViewAllItems[0] = true;
//                }
//            }
//
//            mockViewManagerModel = new ViewManagerModel();
//            mockDeleteViewModel = new MockDeleteViewModel();
//            mockViewAllItemsViewModel = new MockViewAllItemsViewModel();
//            mockViewAllItemsViewModel.setState(new ViewAllItemsState());
//            DeletePresenter deletePresenter = new DeletePresenter(mockDeleteViewModel, mockViewManagerModel, mockViewAllItemsViewModel);
//            DeleteOutputData mockOutputData = new DeleteOutputData();
//
//            assertDoesNotThrow(() ->
//                    deletePresenter.prepareSuccessView(mockOutputData)
//            );
//            assertEquals(mockViewManagerModel.getActiveView(), "view all");
//            assertEquals(mockViewAllItemsViewModel.getState().getWardrobe().size(), 1);
//            assertTrue(hasChangedStateOfViewAllItems[0]);
//        }
//
//        @Test
//        public void testPrepareFailView() {
//            final boolean[] hasChangedStateOfDelete = {false};
//
//            class MockDeleteViewModel extends interface_adapter.delete_clothing_item.DeleteViewModel {
//                @Override
//                public void firePropertyChanged() {
//                    hasChangedStateOfDelete[0] = true;
//                }
//
//            }
//
//            class MockViewAllItemsViewModel extends interface_adapter.view_all_items.ViewAllItemsViewModel {
//                @Override
//                public void firePropertyChanged() {
//                    fail("Should not change state of ViewAllItemsViewModel in this prepareFailView method.");
//                }
//
//            }
//
//            mockViewManagerModel = new ViewManagerModel();
//            mockDeleteViewModel = new MockDeleteViewModel();
//            mockViewAllItemsViewModel = new MockViewAllItemsViewModel();
//            mockViewManagerModel.setActiveView("add item");
//
//            DeletePresenter deletePresenter = new DeletePresenter(mockDeleteViewModel, mockViewManagerModel, mockViewAllItemsViewModel);
//
//            assertDoesNotThrow(() -> {
//                deletePresenter.prepareFailView("Error message");
//            });
//            assertEquals(mockViewManagerModel.getActiveView(), "delete item");
//            assertEquals(mockDeleteViewModel.getState().getDeleteError(), "Error message");
//            assertTrue(hasChangedStateOfDelete[0]);
//        }
//    }

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

package interface_adapter.create_wardrobe;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import model.ClothingItem;
import model.ClothingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.create_wardrobe.CreateInputBoundary;
import use_case.create_wardrobe.CreateOutputData;


import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class CreateWardrobeInterfaceAdapterTest {
    @Nested
    class CreateWardrobeControllerTest {
        @Test
        public void givenValidData_shouldExecuteAndNotThrowExceptions() {
            AtomicBoolean wasInteractorCalled = new AtomicBoolean(false);

            CreateInputBoundary mockCreateUseCaseInteractor = inputData -> {
                wasInteractorCalled.set(true);
                System.out.println("Entered execute method in mocked Interactor");
            };

            CreateWardrobeController createWardrobeController = new CreateWardrobeController(mockCreateUseCaseInteractor);

            assertDoesNotThrow(() -> {
                createWardrobeController.execute("username", "name", "imageSrc", Optional.of("description"), 25);
            });

            assertTrue(wasInteractorCalled.get(), "The interactor should have been called");

            assertDoesNotThrow(() -> {
                createWardrobeController.execute("username", "name", "imageSrc", Optional.empty(), 25);
            });
        }
    }

    @Nested
    class CreateWardrobePresenterTest {
        private ViewManagerModel mockViewManagerModel;
        private CreateWardrobeViewModel mockCreateWardrobeViewModel;
        private ViewAllItemsViewModel mockViewAllItemsViewModel;

        @AfterEach
        public void tearDown() {
            mockCreateWardrobeViewModel = null;
            mockViewAllItemsViewModel = null;
            mockViewManagerModel = null;
        }

        @Test
        public void shouldPrepareSuccessViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfViewAllItems = {false};

            class MockCreateWardrobeViewModel extends interface_adapter.create_wardrobe.CreateWardrobeViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of CreateWardrobeViewModel in this prepareSuccessView method.");
                }
            }

            class MockViewAllItemsViewModel extends interface_adapter.view_all_items.ViewAllItemsViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfViewAllItems[0] = true;
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockCreateWardrobeViewModel = new MockCreateWardrobeViewModel();
            mockViewAllItemsViewModel = new MockViewAllItemsViewModel();
            CreateWardrobePresenter createWardrobePresenter = new CreateWardrobePresenter(mockViewManagerModel, mockCreateWardrobeViewModel, mockViewAllItemsViewModel);
            CreateOutputData mockOutputData = new CreateOutputData(new ClothingItem(1L, "Hat", null, ClothingType.HEAD, 1, Optional.empty()), false);

            assertDoesNotThrow(() ->
                    createWardrobePresenter.prepareSuccessView(mockOutputData)
            );
            assertEquals(mockViewManagerModel.getActiveView(), "view all");
            assertEquals(mockViewAllItemsViewModel.getState().getWardrobe().size(), 1);
            assertTrue(hasChangedStateOfViewAllItems[0]);
        }

        @Test
        public void shouldPrepareFailViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfCreate = {false};

            class MockCreateWardrobeViewModel extends interface_adapter.create_wardrobe.CreateWardrobeViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfCreate[0] = true;
                }

            }

            class MockViewAllItemsViewModel extends interface_adapter.view_all_items.ViewAllItemsViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of ViewAllItemsViewModel in this prepareFailView method.");
                }

            }

            mockViewManagerModel = new ViewManagerModel();
            mockCreateWardrobeViewModel = new MockCreateWardrobeViewModel();
            mockViewAllItemsViewModel = new MockViewAllItemsViewModel();
            mockViewManagerModel.setActiveView("add item");

            CreateWardrobePresenter createWardrobePresenter = new CreateWardrobePresenter(mockViewManagerModel, mockCreateWardrobeViewModel, mockViewAllItemsViewModel);

            assertDoesNotThrow(() -> {
                createWardrobePresenter.prepareFailView("Error message");
            });
            assertEquals(mockViewManagerModel.getActiveView(), "add item");
            assertEquals(mockCreateWardrobeViewModel.getState().getCreateError(), "Error message");
            assertTrue(hasChangedStateOfCreate[0]);
        }
    }

    @Nested
    class CreateWardrobeStateTest {
        @Test
        public void getReturnsWhatSetInputted() {
            CreateWardrobeState createWardrobeState = new CreateWardrobeState();

            // Test the getter and setter methods
            createWardrobeState.setImageSrc("imageSrc");
            assertEquals("imageSrc", createWardrobeState.getImageSrc());

            createWardrobeState.setName("name");
            assertEquals("name", createWardrobeState.getName());

            createWardrobeState.setDescription(Optional.of("description"));
            assertEquals(Optional.of("description"), createWardrobeState.getDescription());

            createWardrobeState.setMinimumAppropriateTemperature(25);
            assertEquals(25, createWardrobeState.getMinimumAppropriateTemperature());

            createWardrobeState.setCreateError("error");
            assertEquals("error", createWardrobeState.getCreateError());
        }
    }

    @Nested
    class CreateWardrobeViewModelTest {
        @Test
        public void firePropertyChangedShouldCallRegisteredListenersAdded() {
            AtomicReference<Object> registeredListenerEventState = new AtomicReference<>();

            CreateWardrobeViewModel createWardrobeViewModel = new CreateWardrobeViewModel();
            assertDoesNotThrow(() -> createWardrobeViewModel.addPropertyChangeListener(evt -> registeredListenerEventState.set(evt.getNewValue())));

            CreateWardrobeState createWardrobeState = new CreateWardrobeState();
            createWardrobeState.setName("Trousers");
            createWardrobeViewModel.setState(createWardrobeState);
            assertDoesNotThrow(createWardrobeViewModel::firePropertyChanged);

            assertEquals(registeredListenerEventState.get(), createWardrobeState);
        }

        @Test
        public void getStateReturnsWhatSetStateInputted() {
            CreateWardrobeViewModel createWardrobeViewModel = new CreateWardrobeViewModel();
            CreateWardrobeState createWardrobeState = new CreateWardrobeState();
            createWardrobeState.setName("Trousers");
            createWardrobeViewModel.setState(createWardrobeState);

            assertNotNull(createWardrobeViewModel.getState());
            assertEquals(createWardrobeState.getName(), "Trousers");
        }
    }
}

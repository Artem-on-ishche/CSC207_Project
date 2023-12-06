package interface_adapter.generate_outfit;

import interface_adapter.ViewManagerModel;
import model.Outfit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.generate_outfit.InputBoundary;
import use_case.generate_outfit.OutfitOutputData;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateOutfitInterfaceAdapterTest {
    public static final String USERNAME = "dancing_yoyo";

    public static final Outfit OUTFIT = new Outfit(Collections.emptyMap(), false);

    @Nested
    class GenerateOutfitControllerTest {
        @Test
        public void givenData_shouldExecuteAndNotThrowExceptions() {
            AtomicBoolean wasInteractorCalled = new AtomicBoolean(false);

            InputBoundary mockGenerateOutfitUseCaseInteractor = inputData -> {
                wasInteractorCalled.set(true);
                System.out.println("Entered execute method in mocked Interactor");
            };

            GenerateOutfitController generateOutfitController = new GenerateOutfitController(mockGenerateOutfitUseCaseInteractor);

            assertDoesNotThrow(() -> {
                generateOutfitController.execute(USERNAME);
            });

            assertTrue(wasInteractorCalled.get(), "The interactor should have been called");

            assertDoesNotThrow(() -> {
                generateOutfitController.execute(null);
            });
        }
    }

    @Nested
    class GenerateOutfitPresenterTest {
        private ViewManagerModel mockViewManagerModel;
        private GenerateOutfitViewModel mockGenerateOutfitViewModel;

        @AfterEach
        public void tearDown() {
            mockGenerateOutfitViewModel = null;
            mockViewManagerModel = null;
        }

        @Test
        public void shouldPrepareSuccessViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfViewAllItems = {false};

            class MockGenerateOutfitViewModel extends interface_adapter.generate_outfit.GenerateOutfitViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfViewAllItems[0] = true;
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockGenerateOutfitViewModel = new MockGenerateOutfitViewModel();
            GenerateOutfitPresenter generateOutfitPresenter = new GenerateOutfitPresenter(mockViewManagerModel, mockGenerateOutfitViewModel);
            OutfitOutputData mockOutputData = new OutfitOutputData(OUTFIT);

            assertDoesNotThrow(() ->
                    generateOutfitPresenter.prepareSuccessView(mockOutputData)
            );
            assertEquals(mockViewManagerModel.getActiveView(), "generate outfit");
            assertEquals(mockGenerateOutfitViewModel.getState().getOutfit(), OUTFIT);
            assertTrue(hasChangedStateOfViewAllItems[0]);
        }

        @Test
        public void shouldPrepareFailViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfGenerateOutfit = {false};

            class MockGenerateOutfitViewModel extends interface_adapter.generate_outfit.GenerateOutfitViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfGenerateOutfit[0] = true;
                }

            }

            mockViewManagerModel = new ViewManagerModel();
            mockGenerateOutfitViewModel = new MockGenerateOutfitViewModel();
            mockViewManagerModel.setActiveView("generate outfit");

            GenerateOutfitPresenter generateOutfitPresenter = new GenerateOutfitPresenter(mockViewManagerModel, mockGenerateOutfitViewModel);

            assertDoesNotThrow(() -> {
                generateOutfitPresenter.prepareFailView("Error message");
            });
            assertEquals(mockViewManagerModel.getActiveView(), "generate outfit");
            assertEquals(mockGenerateOutfitViewModel.getState().getGenerateOutfitError(), "Error message");
            assertTrue(hasChangedStateOfGenerateOutfit[0]);
        }
    }

    @Nested
    class GenerateOutfitStateTest {
        @Test
        public void getReturnsWhatSetInputted() {
            GenerateOutfitState generateOutfitState = new GenerateOutfitState();

            generateOutfitState.setOutfit(OUTFIT);
            assertEquals(OUTFIT, generateOutfitState.getOutfit());

            generateOutfitState.setGenerateOutfitError("error");
            assertEquals("error", generateOutfitState.getGenerateOutfitError());
        }
    }

    @Nested
    class GenerateOutfitViewModelTest {
        @Test
        public void firePropertyChangedShouldCallRegisteredListenersAdded() {
            AtomicReference<Object> registeredListenerEventState = new AtomicReference<>();

            GenerateOutfitViewModel generateOutfitViewModel = new GenerateOutfitViewModel();
            assertDoesNotThrow(() -> generateOutfitViewModel.addPropertyChangeListener(evt -> registeredListenerEventState.set(evt.getNewValue())));

            GenerateOutfitState generateOutfitState = new GenerateOutfitState();
            generateOutfitState.setGenerateOutfitError("error");
            generateOutfitViewModel.setState(generateOutfitState);
            assertDoesNotThrow(generateOutfitViewModel::firePropertyChanged);

            assertEquals(registeredListenerEventState.get(), generateOutfitState);
        }

        @Test
        public void getStateReturnsWhatSetStateInputted() {
            GenerateOutfitViewModel generateOutfitViewModel = new GenerateOutfitViewModel();
            GenerateOutfitState generateOutfitState = new GenerateOutfitState();
            generateOutfitState.setGenerateOutfitError("error");
            generateOutfitViewModel.setState(generateOutfitState);

            assertNotNull(generateOutfitViewModel.getState());
            assertEquals(generateOutfitState.getGenerateOutfitError(), "error");
        }
    }
}

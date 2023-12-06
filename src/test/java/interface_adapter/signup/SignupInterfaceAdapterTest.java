package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupOutputData;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class SignupInterfaceAdapterTest {
    public static final String USERNAME = "dasha_petrivna";
    public static final String PASSWORD = "12345678";

    @Nested
    class SignupControllerTest {
        @Test
        public void givenData_shouldExecuteAndNotThrowExceptions() {
            AtomicBoolean wasInteractorCalled = new AtomicBoolean(false);

            SignupInputBoundary mockSignupUseCaseInteractor = inputData -> {
                wasInteractorCalled.set(true);
                System.out.println("Entered execute method in mocked Interactor");
            };

            SignupController signupController = new SignupController(mockSignupUseCaseInteractor);

            assertDoesNotThrow(() -> {
                signupController.execute(USERNAME, PASSWORD, PASSWORD);
            });

            assertTrue(wasInteractorCalled.get(), "The interactor should have been called");

            assertDoesNotThrow(() -> {
                signupController.execute(null, null, null);
            });
        }
    }

    @Nested
    class SignupPresenterTest {
        private ViewManagerModel mockViewManagerModel;
        private SignupViewModel mockSignupViewModel;
        private LoginViewModel mockLoginViewModel;

        @AfterEach
        public void tearDown() {
            mockSignupViewModel = null;
            mockViewManagerModel = null;
            mockLoginViewModel = null;
        }

        @Test
        public void shouldPrepareSuccessViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfSignup = {false};

            class MockSignupViewModel extends interface_adapter.signup.SignupViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of SignupViewModel in this prepareSuccessView method.");
                }
            }

            class MockLoginViewModel extends interface_adapter.login.LoginViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfSignup[0] = true;
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockSignupViewModel = new MockSignupViewModel();
            mockLoginViewModel = new MockLoginViewModel();
            SignupPresenter signupPresenter = new SignupPresenter(mockViewManagerModel, mockSignupViewModel, mockLoginViewModel);
            SignupOutputData mockOutputData = new SignupOutputData(USERNAME);

            assertDoesNotThrow(() ->
                    signupPresenter.prepareSuccessView(mockOutputData)
            );
            assertEquals(mockViewManagerModel.getActiveView(), "log in");
            assertEquals(mockLoginViewModel.getState().getUsername(), USERNAME);
            assertTrue(hasChangedStateOfSignup[0]);
        }

        @Test
        public void shouldPrepareFailViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfSignup = {false};

            class MockSignupViewModel extends interface_adapter.signup.SignupViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfSignup[0] = true;
                }
            }

            class MockLoginViewModel extends interface_adapter.login.LoginViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of LoginViewModel in this prepareSuccessView method.");
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockSignupViewModel = new MockSignupViewModel();
            mockLoginViewModel = new MockLoginViewModel();
            mockViewManagerModel.setActiveView("sign in");

            SignupPresenter signupPresenter = new SignupPresenter(mockViewManagerModel, mockSignupViewModel, mockLoginViewModel);

            assertDoesNotThrow(() -> {
                signupPresenter.prepareFailView("Error message");
            });
            assertEquals(mockViewManagerModel.getActiveView(), "sign in");
            assertEquals(mockSignupViewModel.getState().getUsernameError(), "Error message");
            assertTrue(hasChangedStateOfSignup[0]);
        }
    }

    @Nested
    class SignupStateTest {
        @Test
        public void getReturnsWhatSetInputted() {
            SignupState signupState = new SignupState();

            signupState.setUsername(USERNAME);
            assertEquals(USERNAME, signupState.getUsername());

            signupState.setUsernameError("Username error");
            assertEquals("Username error", signupState.getUsernameError());

            signupState.setPassword(PASSWORD);
            assertEquals(PASSWORD, signupState.getPassword());

            signupState.setPasswordError("Password error");
            assertEquals("Password error", signupState.getPasswordError());

            signupState.setRepeatPassword(PASSWORD);
            assertEquals(PASSWORD, signupState.getRepeatPassword());

            signupState.setRepeatPasswordError("Repeat password error");
            assertEquals("Repeat password error", signupState.getRepeatPasswordError());
        }
    }

    @Nested
    class SignupViewModelTest {
        @Test
        public void firePropertyChangedShouldCallRegisteredListenersAdded() {
            AtomicReference<Object> registeredListenerEventState = new AtomicReference<>();

            SignupViewModel signupViewModel = new SignupViewModel();
            assertDoesNotThrow(() -> signupViewModel.addPropertyChangeListener(evt -> registeredListenerEventState.set(evt.getNewValue())));

            SignupState signupState = new SignupState();
            signupState.setUsernameError("error");
            signupViewModel.setState(signupState);
            assertDoesNotThrow(signupViewModel::firePropertyChanged);

            assertEquals(registeredListenerEventState.get(), signupState);
        }

        @Test
        public void getStateReturnsWhatSetStateInputted() {
            SignupViewModel signupViewModel = new SignupViewModel();
            SignupState signupState = new SignupState();
            signupState.setUsernameError("Username error");
            signupViewModel.setState(signupState);

            assertNotNull(signupViewModel.getState());
            assertEquals(signupState.getUsernameError(), "Username error");
        }
    }
}

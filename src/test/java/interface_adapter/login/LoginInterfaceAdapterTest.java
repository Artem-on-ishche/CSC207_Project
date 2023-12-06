package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginOutputData;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class LoginInterfaceAdapterTest {
    public static final String USERNAME = "dasha_petrivna";
    public static final String PASSWORD = "12345678";

    @Nested
    class LoginControllerTest {
        @Test
        public void givenData_shouldExecuteAndNotThrowExceptions() {
            AtomicBoolean wasInteractorCalled = new AtomicBoolean(false);

            LoginInputBoundary mockLoginUseCaseInteractor = inputData -> {
                wasInteractorCalled.set(true);
                System.out.println("Entered execute method in mocked Interactor");
            };

            LoginController loginController = new LoginController(mockLoginUseCaseInteractor);

            assertDoesNotThrow(() -> {
                loginController.execute(USERNAME, PASSWORD);
            });

            assertTrue(wasInteractorCalled.get(), "The interactor should have been called");

            assertDoesNotThrow(() -> {
                loginController.execute(null, null);
            });
        }
    }

    @Nested
    class LoginPresenterTest {
        private ViewManagerModel mockViewManagerModel;
        private LoginViewModel mockLoginViewModel;

        private LoggedInViewModel mockLoggedInViewModel;

        @AfterEach
        public void tearDown() {
            mockLoginViewModel = null;
            mockViewManagerModel = null;
            mockLoggedInViewModel = null;
        }

        @Test
        public void shouldPrepareSuccessViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfLogin = {false};

            class MockLoginViewModel extends interface_adapter.login.LoginViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of LoginViewModel in this prepareSuccessView method.");
                }
            }

            class MockLoggedInViewModel extends interface_adapter.logged_in.LoggedInViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfLogin[0] = true;
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockLoginViewModel = new MockLoginViewModel();
            mockLoggedInViewModel = new MockLoggedInViewModel();
            LoginPresenter loginPresenter = new LoginPresenter(mockViewManagerModel, mockLoggedInViewModel, mockLoginViewModel);
            LoginOutputData mockOutputData = new LoginOutputData(USERNAME, false);

            assertDoesNotThrow(() ->
                    loginPresenter.prepareSuccessView(mockOutputData)
            );
            assertEquals(mockViewManagerModel.getActiveView(), "logged in");
            assertEquals(mockLoggedInViewModel.getState().getUsername(), USERNAME);
            assertTrue(hasChangedStateOfLogin[0]);
        }

        @Test
        public void shouldPrepareFailViewAndNotThrowExceptions() {
            final boolean[] hasChangedStateOfLogin = {false};

            class MockLoginViewModel extends interface_adapter.login.LoginViewModel {
                @Override
                public void firePropertyChanged() {
                    hasChangedStateOfLogin[0] = true;
                }
            }

            class MockLoggedInViewModel extends interface_adapter.logged_in.LoggedInViewModel {
                @Override
                public void firePropertyChanged() {
                    fail("Should not change state of LoggedInViewModel in this prepareSuccessView method.");
                }
            }

            mockViewManagerModel = new ViewManagerModel();
            mockLoginViewModel = new MockLoginViewModel();
            mockLoggedInViewModel = new MockLoggedInViewModel();
            mockViewManagerModel.setActiveView("log in");

            LoginPresenter loginPresenter = new LoginPresenter(mockViewManagerModel, mockLoggedInViewModel, mockLoginViewModel);

            assertDoesNotThrow(() -> {
                loginPresenter.prepareFailView("Error message");
            });
            assertEquals(mockViewManagerModel.getActiveView(), "log in");
            assertEquals(mockLoginViewModel.getState().getUsernameError(), "Error message");
            assertTrue(hasChangedStateOfLogin[0]);
        }
    }

    @Nested
    class LoginStateTest {
        @Test
        public void getReturnsWhatSetInputted() {
            LoginState loginState = new LoginState();

            loginState.setUsername(USERNAME);
            assertEquals(USERNAME, loginState.getUsername());

            loginState.setUsernameError("Username error");
            assertEquals("Username error", loginState.getUsernameError());

            loginState.setPassword(PASSWORD);
            assertEquals(PASSWORD, loginState.getPassword());

            loginState.setPasswordError("Password error");
            assertEquals("Password error", loginState.getPasswordError());
        }
    }

    @Nested
    class LoginViewModelTest {
        @Test
        public void firePropertyChangedShouldCallRegisteredListenersAdded() {
            AtomicReference<Object> registeredListenerEventState = new AtomicReference<>();

            LoginViewModel loginViewModel = new LoginViewModel();
            assertDoesNotThrow(() -> loginViewModel.addPropertyChangeListener(evt -> registeredListenerEventState.set(evt.getNewValue())));

            LoginState loginState = new LoginState();
            loginState.setUsernameError("error");
            loginViewModel.setState(loginState);
            assertDoesNotThrow(loginViewModel::firePropertyChanged);

            assertEquals(registeredListenerEventState.get(), loginState);
        }

        @Test
        public void getStateReturnsWhatSetStateInputted() {
            LoginViewModel loginViewModel = new LoginViewModel();
            LoginState loginState = new LoginState();
            loginState.setUsernameError("Username error");
            loginViewModel.setState(loginState);

            assertNotNull(loginViewModel.getState());
            assertEquals(loginState.getUsernameError(), "Username error");
        }
    }
}

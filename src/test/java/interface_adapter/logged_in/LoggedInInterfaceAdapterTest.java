package interface_adapter.logged_in;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggedInInterfaceAdapterTest {
    @Nested
    class LoggedInStateTest {
        @Test
        public void getReturnsWhatSetInputted() {
            LoggedInState loggedInState = new LoggedInState();

            loggedInState.setUsername("anna");
            assertEquals("anna", loggedInState.getUsername());

        }
    }

    @Nested
    class LoggedInViewModelTest {
        @Test
        public void firePropertyChangedShouldCallRegisteredListenersAdded() {
            AtomicReference<Object> registeredListenerEventState = new AtomicReference<>();

            LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
            assertDoesNotThrow(() -> loggedInViewModel.addPropertyChangeListener(evt -> registeredListenerEventState.set(evt.getNewValue())));

            LoggedInState loggedInState = new LoggedInState();
            loggedInState.setUsername("anna");
            loggedInViewModel.setState(loggedInState);
            assertDoesNotThrow(loggedInViewModel::firePropertyChanged);

            assertEquals(registeredListenerEventState.get(), loggedInState);
        }

        @Test
        public void getStateReturnsWhatSetStateInputted() {
            LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
            LoggedInState loggedInState = new LoggedInState();
            loggedInState.setUsername("anna");
            loggedInViewModel.setLoggedInUser("anna101");
            loggedInViewModel.setState(loggedInState);

            assertNotNull(loggedInViewModel.getState());
            assertEquals(loggedInState.getUsername(), "anna");
            assertEquals(loggedInViewModel.getLoggedInUser(), "anna101");
        }

    }
}

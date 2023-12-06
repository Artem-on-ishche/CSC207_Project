package interface_adapter;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import static org.junit.jupiter.api.Assertions.*;


public class ViewModelTest {
    @Getter
    private static class TestViewModel extends ViewModel {

        private boolean propertyChangedCalled = false;
        private final PropertyChangeEvent lastEvent = null;

        public TestViewModel(String viewName) {
            super(viewName);
        }

        @Override
        public void firePropertyChanged() {
            propertyChangedCalled = true;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {}

    }

    @Test
    void getViewName_shouldReturnViewNameSetInConstructor() {
        String expectedViewName = "TestView";
        ViewModel viewModel = new TestViewModel(expectedViewName);

        String actualViewName = assertDoesNotThrow(viewModel::getViewName);

        assertEquals(expectedViewName, actualViewName);
    }

    @Test
    void firePropertyChanged_shouldChangeFireProperty() {
        TestViewModel testViewModel = new TestViewModel("TestView");
        testViewModel.firePropertyChanged();

        assertTrue(testViewModel.isPropertyChangedCalled());
    }

}

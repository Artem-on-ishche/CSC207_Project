package interface_adapter;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.jupiter.api.Assertions.*;

public class ViewManagerModelTest {
    private ViewManagerModel viewManagerModel;
    private TestPropertyChangeListener testListener;

    @BeforeEach
    void setUp() {
        viewManagerModel = new ViewManagerModel();
        testListener = new TestPropertyChangeListener();
    }

    @Test
    void getActiveViewTest() {
        viewManagerModel.setActiveView("TestView");

        String activeView = viewManagerModel.getActiveView();

        assertEquals("TestView", activeView);
    }

    @Test
    void setActiveViewTest() {
        viewManagerModel.addPropertyChangeListener(testListener);

        viewManagerModel.setActiveView("NewView");
        viewManagerModel.firePropertyChanged();

        assertEquals("NewView", viewManagerModel.getActiveView());
        assertTrue(testListener.isPropertyChangedCalled());
        assertEquals("view", testListener.getLastEvent().getPropertyName());
        assertNull(testListener.getLastEvent().getOldValue());
        assertEquals("NewView", testListener.getLastEvent().getNewValue());
    }

    @Test
    void firePropertyChangedTest() {
        viewManagerModel.addPropertyChangeListener(testListener);

        viewManagerModel.setActiveView("AnotherView");
        viewManagerModel.firePropertyChanged();

        assertTrue(testListener.isPropertyChangedCalled());
        assertEquals("view", testListener.getLastEvent().getPropertyName());
        assertNull(testListener.getLastEvent().getOldValue());
        assertEquals("AnotherView", testListener.getLastEvent().getNewValue());
    }

    @Getter
    private static class TestPropertyChangeListener implements PropertyChangeListener {
        private boolean propertyChangedCalled = false;
        private PropertyChangeEvent lastEvent = null;

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            propertyChangedCalled = true;
            lastEvent = evt;
        }

    }
}

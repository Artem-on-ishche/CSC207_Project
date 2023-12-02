package interface_adapter.create_wardrobe;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class CreateWardrobeViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Create Wardrobe";
    public static final String PHOTO_LABEL = "Upload photo";
    public static final String NAME_LABEL = "Name";
    public static final String MIN_TEMP_LABEL = "Min temp";

    public static final String ADD_BUTTON_LABEL = "Add item";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    private CreateWardrobeState state = new CreateWardrobeState();

    public CreateWardrobeViewModel() {
        super("add item");
    }

    public void setState(CreateWardrobeState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public CreateWardrobeState getState() {
        return state;
    }
}

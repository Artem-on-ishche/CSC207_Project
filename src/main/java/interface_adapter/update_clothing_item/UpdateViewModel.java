package interface_adapter.update_clothing_item;

import interface_adapter.ViewModel;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UpdateViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Update Clothing Item";
    public static final String DELETE_BUTTON_LABEL = "Update";

    @Getter
    private UpdateState state = new UpdateState();

    public UpdateViewModel() {
        super("update item");
    }

    public void setState(UpdateState state) {
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
}

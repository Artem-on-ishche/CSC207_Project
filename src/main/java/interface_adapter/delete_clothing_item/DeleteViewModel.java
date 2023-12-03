package interface_adapter.delete_clothing_item;

import interface_adapter.ViewModel;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DeleteViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Delete Clothing Item";
    public static final String DELETE_BUTTON_LABEL = "Delete";

    @Getter
    private DeleteState state = new DeleteState();

    public DeleteViewModel() {
        super("delete item");
    }

    public void setState(DeleteState state) {
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

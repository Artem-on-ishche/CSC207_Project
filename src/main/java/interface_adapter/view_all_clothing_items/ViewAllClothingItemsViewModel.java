package interface_adapter.view_all_clothing_items;

import interface_adapter.ViewModel;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewAllClothingItemsViewModel extends ViewModel {
    public final String TITLE_LABEL = "View All";

    @Getter
    private ViewAllClothingItemsState state = new ViewAllClothingItemsState();

    public ViewAllClothingItemsViewModel() {
        super("view all");
    }

    public void setState(ViewAllClothingItemsState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

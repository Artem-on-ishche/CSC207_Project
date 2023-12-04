package interface_adapter.view_all_items;

import interface_adapter.ViewModel;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewAllItemsViewModel extends ViewModel {
    public final String TITLE_LABEL = "View All";

    @Getter
    private ViewAllItemsState state = new ViewAllItemsState(wardrobe);

    public ViewAllItemsViewModel() {
        super("view all");
    }

    public void setState(ViewAllItemsState state) {
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

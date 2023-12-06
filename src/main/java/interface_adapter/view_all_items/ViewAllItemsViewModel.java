package interface_adapter.view_all_items;

import interface_adapter.ViewModel;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewAllItemsViewModel extends ViewModel {
    public final String TITLE_LABEL = "view all";
    public static final String ADD_CLOTHING_ITEM = "Add Item";
    public static final String BACK_TO_MAIN_VIEW = "Back";

    @Getter
    private ViewAllItemsState state = new ViewAllItemsState();

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

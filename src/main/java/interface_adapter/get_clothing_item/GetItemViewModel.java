package interface_adapter.get_clothing_item;

import interface_adapter.ViewModel;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetItemViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Get Clothing Item";
    public static final String Get_BUTTON_LABEL = "View more";

    @Getter
    private GetItemState state = new GetItemState();

    public GetItemViewModel() {
        super("get item");
    }

    public void setState(GetItemState state) {
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

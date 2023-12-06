package interface_adapter.get_clothing_item;

import interface_adapter.ViewModel;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetClothingItemViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Get Clothing Item";
    public static final String SAVE_CHANGES_LABEL = "Save changes";
    public static final String BACK_LABEL = "Back";

    @Getter
    private GetClothingItemState state = new GetClothingItemState();

    public GetClothingItemViewModel() {
        super("get item");
    }

    public void setState(GetClothingItemState state) {
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

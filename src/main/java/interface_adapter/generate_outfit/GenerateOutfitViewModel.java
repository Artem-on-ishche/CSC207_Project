package interface_adapter.generate_outfit;

import interface_adapter.ViewModel;
import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GenerateOutfitViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Generate Outfit";
    public static final String GENERATE_BUTTON_LABEL = "Generate Outfit";

    @Getter
    private GenerateOutfitState state = new GenerateOutfitState();

    public GenerateOutfitViewModel() {
        super("generate outfit");
    }

    public void setState(GenerateOutfitState state) {
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

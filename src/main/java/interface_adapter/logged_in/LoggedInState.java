package interface_adapter.logged_in;

import model.ClothingItem;

import java.util.List;

public class LoggedInState {
    private String username = "";

    private List<ClothingItem> wardrobe;

    public LoggedInState(LoggedInState copy) {
        username = copy.username;
        wardrobe = copy.wardrobe;
    }

    public LoggedInState() {}

    public List<ClothingItem> getWardrobe() {
        return wardrobe;
    }

    public void setWardrobe(List<ClothingItem> wardrobe) {
        this.wardrobe = wardrobe;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}

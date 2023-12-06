package interface_adapter.view_all_clothing_items;

import lombok.Getter;
import model.ClothingItem;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ViewAllClothingItemsState {
    private List<ClothingItem> wardrobe;

    private String getAllItemsError;

    public ViewAllClothingItemsState() {
        this.wardrobe = new ArrayList<>();
    }

    public void setWardrobe(List<ClothingItem> wardrobe) {
        this.wardrobe = wardrobe;
    }

    public void setGetAllItemsError(String getAllItemsError) {
        this.getAllItemsError = getAllItemsError;
    }
}

package interface_adapter.view_all_clothing_items;

import lombok.Getter;
import model.ClothingItem;

import java.util.List;

@Getter
public class ViewAllClothingItemsState {
    private List<ClothingItem> wardrobe;

    public ViewAllClothingItemsState(List<ClothingItem> wardrobe) {
        this.wardrobe = wardrobe;
    }

    public ViewAllClothingItemsState(){}

    public void setWardrobe(List<ClothingItem> wardrobe) {
        this.wardrobe = wardrobe;
    }
}

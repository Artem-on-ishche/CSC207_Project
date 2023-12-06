package interface_adapter.get_clothing_item;

import lombok.Getter;
import model.ClothingItem;

@Getter
public class GetClothingItemState {
    public void setClothingItem(ClothingItem clothingItem) {
        this.clothingItem = clothingItem;
    }

    public void setGetItemError(String getItemError) {
        this.getItemError = getItemError;
    }

    private ClothingItem clothingItem;
    private String getItemError;

    public GetClothingItemState() {
    }

    public GetClothingItemState(GetClothingItemState copy) {
        clothingItem = copy.clothingItem;
        getItemError = copy.getItemError;
    }
}

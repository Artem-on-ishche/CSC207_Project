package interface_adapter.get_clothing_item;

import lombok.Getter;
import model.ClothingItem;

@Getter
public class GetItemState {
    public void setClothingItem(ClothingItem clothingItem) {
        this.clothingItem = clothingItem;
    }

    public void setGetItemError(String getItemError) {
        this.getItemError = getItemError;
    }

    private ClothingItem clothingItem;
    private String getItemError;

    public GetItemState() {
    }

    public GetItemState(GetItemState copy) {
        clothingItem = copy.clothingItem;
        getItemError = copy.getItemError;
    }
}

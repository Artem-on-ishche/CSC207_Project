package interface_adapter.update_clothing_item;

import model.ClothingItem;

public class UpdateState {
    private String updateError;

    private ClothingItem clothingItem;

    public UpdateState(String updateError, ClothingItem clothingItem) {
        this.updateError = updateError;
        this.clothingItem = clothingItem;
    }

    public UpdateState() {}

    public String getUpdateError() {
        return updateError;
    }

    public void setUpdateError(String updateError) {
        this.updateError = updateError;
    }

    public ClothingItem getClothingItem() {
        return clothingItem;
    }

    public void setClothingItem(ClothingItem clothingItem) {
        this.clothingItem = clothingItem;
    }
}

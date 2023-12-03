package use_case.get_item_by_id;

import model.ClothingItem;

public record GetItemByIdOutputData (ClothingItem clothingItem, boolean useCaseFailed){
}

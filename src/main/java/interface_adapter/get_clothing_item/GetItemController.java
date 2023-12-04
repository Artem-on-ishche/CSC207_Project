package interface_adapter.get_clothing_item;


import use_case.get_clothing_item.GetClothingItemInputBoundary;
import use_case.get_clothing_item.GetClothingItemInputData;

public class GetItemController {
    private final GetClothingItemInputBoundary getClothingItemInputBoundary;
    public GetItemController(GetClothingItemInputBoundary getClothingItemInputBoundary) {
        this.getClothingItemInputBoundary = getClothingItemInputBoundary;
    }

    public void execute(Long itemId) {
        GetClothingItemInputData getClothingItemInputData = new GetClothingItemInputData(itemId);
        getClothingItemInputBoundary.execute(getClothingItemInputData);
    }
}

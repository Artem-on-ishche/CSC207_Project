package interface_adapter.get_clothing_item;


import use_case.get_clothing_item.GetClothingItemInputBoundary;
import use_case.get_clothing_item.GetClothingItemInputData;

public class GetClothingItemController {
    private final GetClothingItemInputBoundary getClothingItemInputBoundary;
    public GetClothingItemController(GetClothingItemInputBoundary getClothingItemInputBoundary) {
        this.getClothingItemInputBoundary = getClothingItemInputBoundary;
    }

    public void execute(Long itemId) {
        System.out.println(itemId);
        GetClothingItemInputData getClothingItemInputData = new GetClothingItemInputData(itemId);
        getClothingItemInputBoundary.execute(getClothingItemInputData);
    }
}

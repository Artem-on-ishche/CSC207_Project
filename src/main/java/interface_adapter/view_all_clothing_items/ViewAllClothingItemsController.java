package interface_adapter.view_all_clothing_items;

import use_case.view_all_clothing_items.ViewAllClothingItemsInputBoundary;
import use_case.view_all_clothing_items.ViewAllClothingItemsInputData;

public class ViewAllClothingItemsController {
    private final ViewAllClothingItemsInputBoundary viewAllClothingItemsInteractor;
    public ViewAllClothingItemsController(ViewAllClothingItemsInputBoundary viewAllClothingItemsInteractor) {
        this.viewAllClothingItemsInteractor = viewAllClothingItemsInteractor;
    }

    public void execute(String username) {
        ViewAllClothingItemsInputData viewAllClothingItemsInputData = new ViewAllClothingItemsInputData(username);

        viewAllClothingItemsInteractor.execute(viewAllClothingItemsInputData);
    }
}

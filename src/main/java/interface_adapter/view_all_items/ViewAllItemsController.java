package interface_adapter.view_all_items;

import use_case.view_all_clothing_items.ViewAllClothingItemsInputBoundary;
import use_case.view_all_clothing_items.ViewAllClothingItemsInputData;

public class ViewAllItemsController {
    private final ViewAllClothingItemsInputBoundary viewAllClothingItemsInteractor;
    public ViewAllItemsController(ViewAllClothingItemsInputBoundary viewAllClothingItemsInteractor) {
        this.viewAllClothingItemsInteractor = viewAllClothingItemsInteractor;
    }

    public void execute(String username) {
        ViewAllClothingItemsInputData viewAllClothingItemsInputData = new ViewAllClothingItemsInputData(username);

        viewAllClothingItemsInteractor.execute(viewAllClothingItemsInputData);
    }
}

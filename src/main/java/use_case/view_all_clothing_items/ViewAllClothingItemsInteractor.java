package use_case.view_all_clothing_items;

import java.util.Collections;

public class ViewAllClothingItemsInteractor implements ViewAllClothingItemsInputBoundary {

    private final AllItemsOfUserDataAccess dao;
    private final ViewAllClothingItemsOutputBoundary outputBoundary;

    public ViewAllClothingItemsInteractor(AllItemsOfUserDataAccess dao, ViewAllClothingItemsOutputBoundary outputBoundary) {
        this.dao = dao;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ViewAllClothingItemsInputData inputData) {
        try {
            var clothingItems = dao.getAllClothingItemsByUsername(inputData.username());

            if (clothingItems.isEmpty()) {
                var emptyOutputData = new ViewAllClothingItemsOutputData(Collections.emptyList());
                outputBoundary.prepareSuccessView(emptyOutputData);
            } else {
                var outputData = new ViewAllClothingItemsOutputData(clothingItems);
                outputBoundary.prepareSuccessView(outputData);
            }
        } catch (Exception e) {
            outputBoundary.prepareFailureView("Error when trying to get all clothing items of user with id = " + inputData.username() + ": " + e.getMessage());
        }
    }
}

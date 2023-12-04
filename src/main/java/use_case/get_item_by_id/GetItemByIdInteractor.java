package use_case.get_item_by_id;

import model.ClothingItem;

public class GetItemByIdInteractor implements GetItemByIdInputBoundary {
    private final GetItemByIdDataAccess getItemByIdDataAccess;
    private final GetItemByIdOutputBoundary getPresenter;

    public GetItemByIdInteractor(GetItemByIdDataAccess getItemByIdDataAccess,
                                     GetItemByIdOutputBoundary getPresenter) {
        this.getItemByIdDataAccess = getItemByIdDataAccess;
        this.getPresenter = getPresenter;
    }

    @Override
    public void execute(GetItemByIdInputData inputData) {
        try {
            long itemId = inputData.itemId();
            ClothingItem clothingItem = getItemByIdDataAccess.getClothingItemById(itemId);

            if (clothingItem != null) {
                getPresenter.prepareSuccessView(new GetItemByIdOutputData(clothingItem, false));
            } else {
                getPresenter.prepareFailView("Clothing item not found");
            }

        } catch (RuntimeException e) {
            getPresenter.prepareFailView("Error getting clothing item " + e.getMessage());
        }
    }
}

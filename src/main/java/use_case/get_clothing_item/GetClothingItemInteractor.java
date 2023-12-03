package use_case.get_clothing_item;

import model.ClothingItem;

import java.util.Optional;

public class GetClothingItemInteractor implements GetClothingItemInputBoundary {
    private final GetClothingItemDataAccess getClothingItemDataAccess;
    private final GetClothingItemOutputBoundary getPresenter;

    public GetClothingItemInteractor(GetClothingItemDataAccess getClothingItemDataAccess,
                                     GetClothingItemOutputBoundary getPresenter) {
        this.getClothingItemDataAccess = getClothingItemDataAccess;
        this.getPresenter = getPresenter;
    }

    @Override
    public void execute(GetClothingItemInputData inputData) {
        try {
            long itemId = inputData.itemId();
            Optional<ClothingItem> clothingItem = getClothingItemDataAccess.getClothingItemById(itemId);

            if (clothingItem.isPresent()) {
                getPresenter.prepareSuccessView(new GetClothingItemOutputData(clothingItem, false));
            } else {
                getPresenter.prepareFailView("Clothing item not found");
            }

        } catch (RuntimeException e) {
            getPresenter.prepareFailView("Error getting clothing item " + e.getMessage());
        }
    }
}

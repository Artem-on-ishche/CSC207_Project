package use_case.get_clothing_item;

import model.ClothingItem;

import java.util.Optional;

public class GetClothingItemInteractor implements GetClothingItemInputBoundary {
    private final GetIClothingItemAccess getIClothingItemAccess;
    private final GetClothingItemOutputBoundary getPresenter;

    public GetClothingItemInteractor(GetIClothingItemAccess getIClothingItemAccess,
                                     GetClothingItemOutputBoundary getPresenter) {
        this.getIClothingItemAccess = getIClothingItemAccess;
        this.getPresenter = getPresenter;
    }

    @Override
    public void execute(GetClothingItemInputData inputData) {
        try {
            long itemId = inputData.itemId();
            Optional<ClothingItem> clothingItem = getIClothingItemAccess.getClothingItemById(itemId);

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

package use_case.edit_wardrobe.update_clothing_item;

import use_case.edit_wardrobe.EditInputBoundary;
import use_case.edit_wardrobe.EditInputData;
import use_case.edit_wardrobe.EditOutputBoundary;
import use_case.edit_wardrobe.EditOutputData;

public class UpdateInteractor implements EditInputBoundary {
    private final EditOutputBoundary updatePresenter;
    private final UpdateDataAccess updateDataAccessObject;

    public UpdateInteractor(EditOutputBoundary updatePresenter, UpdateDataAccess updateDataAccessObject) {
        this.updatePresenter = updatePresenter;
        this.updateDataAccessObject = updateDataAccessObject;
    }
    @Override
    public void execute(EditInputData inputData) {
        try {
            updateDataAccessObject.updateClothingItem(inputData.clothingItemToEdit());
            updatePresenter.prepareSuccessView(new EditOutputData(inputData.clothingItemToEdit(), false));
        } catch (RuntimeException e) {
            updatePresenter.prepareFailView("Error updating clothing item with id " + inputData.clothingItemToEdit().getId() + ". \n" + e.getMessage());
        }
    }
}

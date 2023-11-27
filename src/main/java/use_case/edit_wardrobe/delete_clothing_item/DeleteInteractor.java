package use_case.edit_wardrobe.delete_clothing_item;

import use_case.edit_wardrobe.EditInputBoundary;
import use_case.edit_wardrobe.EditInputData;
import use_case.edit_wardrobe.EditOutputBoundary;
import use_case.edit_wardrobe.EditOutputData;

public class DeleteInteractor implements EditInputBoundary {
    private final EditOutputBoundary deletePresenter;
    private final DeleteDataAccess deleteDataAccessObject;

    public DeleteInteractor(EditOutputBoundary deletePresenter, DeleteDataAccess deleteDataAccessObject) {
        this.deletePresenter = deletePresenter;
        this.deleteDataAccessObject = deleteDataAccessObject;
    }
    @Override
    public void execute(EditInputData inputData) {
        try {
            deleteDataAccessObject.deleteClothingItem(inputData.clothingItemToEdit().id());
            deletePresenter.prepareSuccessView(new EditOutputData(inputData.clothingItemToEdit(), false));
        } catch (RuntimeException e) {
            deletePresenter.prepareFailView("Error deleting clothing item with id " + inputData.clothingItemToEdit().id() + ". \n" + e.getMessage());
        }
    }
}

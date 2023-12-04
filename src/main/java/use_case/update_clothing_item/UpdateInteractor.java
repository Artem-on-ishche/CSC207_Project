package use_case.update_clothing_item;

public class UpdateInteractor implements UpdateInputBoundary {
    private final UpdateOutputBoundary updatePresenter;
    private final UpdateDataAccess updateDataAccessObject;

    public UpdateInteractor(UpdateOutputBoundary updatePresenter, UpdateDataAccess updateDataAccessObject) {
        this.updatePresenter = updatePresenter;
        this.updateDataAccessObject = updateDataAccessObject;
    }
    @Override
    public void execute(UpdateInputData inputData) {
        try {
            updateDataAccessObject.updateClothingItem(inputData.clothingItemToUpdate());
            updatePresenter.prepareSuccessView(new UpdateOutputData(inputData.clothingItemToUpdate()));
        } catch (Exception e) {
            updatePresenter.prepareFailView("Error updating clothing item with id " + inputData.clothingItemToUpdate().getId() + ".");
        }
    }
}

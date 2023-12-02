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
            updatePresenter.prepareSuccessView(
                    new UpdateOutputData(updateDataAccessObject.updateClothingItem(inputData.clothingItemToUpdate()), false)
            );
        } catch (RuntimeException e) {
            updatePresenter.prepareFailView("Error updating clothing item with id " + inputData.clothingItemToUpdate().id() + ". \n" + e.getMessage());
        }
    }
}

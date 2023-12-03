package use_case.delete_clothing_item;

public class DeleteInteractor implements DeleteInputBoundary {
    private final DeleteOutputBoundary deletePresenter;
    private final DeleteDataAccess deleteDataAccessObject;

    public DeleteInteractor(DeleteOutputBoundary deletePresenter, DeleteDataAccess deleteDataAccessObject) {
        this.deletePresenter = deletePresenter;
        this.deleteDataAccessObject = deleteDataAccessObject;
    }

    @Override
    public void execute(DeleteInputData inputData) {
        try {
            deleteDataAccessObject.deleteClothingItem(inputData.clothingItemToDeleteId());
            deletePresenter.prepareSuccessView(new DeleteOutputData());
        } catch (RuntimeException e) {
            deletePresenter.prepareFailView("Error deleting clothing item with id " + inputData.clothingItemToDeleteId() + ". \n" + e.getMessage());
        }
    }
}

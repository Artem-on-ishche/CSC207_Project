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
        } catch (IllegalArgumentException illegalArgumentException) {
            deletePresenter.prepareFailView("Error deleting clothing item. There is no item with " + inputData.clothingItemToDeleteId() + " id in the wardrobe.");
        } catch (NullPointerException nullPointerException){
            deletePresenter.prepareFailView("Error deleting clothing item. Id cannot be null.");
        } catch (Exception e) {
            deletePresenter.prepareFailView("Error deleting clothing item with id " + inputData.clothingItemToDeleteId() + ".");
        }
    }
}

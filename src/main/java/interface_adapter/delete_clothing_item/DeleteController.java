package interface_adapter.delete_clothing_item;

import use_case.delete_clothing_item.DeleteInputBoundary;
import use_case.delete_clothing_item.DeleteInputData;

public class DeleteController {
    final DeleteInputBoundary deleteUseCaseInteractor;
    public DeleteController(DeleteInputBoundary deleteInputBoundary) {
        this.deleteUseCaseInteractor = deleteInputBoundary;
    }

    public void execute(Long clothingItemToDeleteId) {
        DeleteInputData deleteInputData = new DeleteInputData(clothingItemToDeleteId);
        deleteUseCaseInteractor.execute(deleteInputData);
    }
}

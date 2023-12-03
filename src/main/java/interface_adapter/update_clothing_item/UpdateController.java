package interface_adapter.update_clothing_item;

import use_case.update_clothing_item.UpdateInputBoundary;
import use_case.update_clothing_item.UpdateInputData;

public class UpdateController {
    private final UpdateInputBoundary updateUseCaseInteractor;
    public UpdateController(UpdateInputBoundary updateInputBoundary) {
        this.updateUseCaseInteractor = updateInputBoundary;
    }

    public void execute(Long clothingItemToDeleteId) {
        UpdateInputData updateInputData = new UpdateInputData();
        updateUseCaseInteractor.execute(updateInputData);
    }
}

package interface_adapter.update_clothing_item;

import model.ClothingItem;
import use_case.update_clothing_item.UpdateInputBoundary;
import use_case.update_clothing_item.UpdateInputData;

public class UpdateController {
    private final UpdateInputBoundary updateUseCaseInteractor;
    public UpdateController(UpdateInputBoundary updateInputBoundary) {
        this.updateUseCaseInteractor = updateInputBoundary;
    }

    public void execute(ClothingItem clothingItem) {
        UpdateInputData updateInputData = new UpdateInputData(clothingItem);
        updateUseCaseInteractor.execute(updateInputData);
    }
}

package interface_adapter.generate_outfit;

import use_case.generate_outfit.InputBoundary;
import use_case.generate_outfit.InputData;
import use_case.get_item_by_id.GetItemByIdInputBoundary;

public class GenerateOutfitController {
    private final InputBoundary inputBoundary;

    public GenerateOutfitController(InputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute(String username) {
        InputData inputData = new InputData(username);

        inputBoundary.execute(inputData);
    }

}

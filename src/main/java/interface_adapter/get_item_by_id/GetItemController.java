package interface_adapter.get_item_by_id;


import use_case.get_item_by_id.GetItemByIdInputBoundary;
import use_case.get_item_by_id.GetItemByIdInputData;

public class GetItemController {
    private final GetItemByIdInputBoundary getItemByIdInputBoundary;
    public GetItemController(GetItemByIdInputBoundary getItemByIdInputBoundary) {
        this.getItemByIdInputBoundary = getItemByIdInputBoundary;
    }

    public void execute(Long itemId) {
        GetItemByIdInputData getItemByIdInputData = new GetItemByIdInputData(itemId);
        getItemByIdInputBoundary.execute(getItemByIdInputData);
    }
}

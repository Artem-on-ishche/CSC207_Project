package use_case.get_item_by_id;

public interface GetItemByIdOutputBoundary {
    void prepareSuccessView(GetItemByIdOutputData outputData);

    void prepareFailView(String error);
}

package use_case.view_all_clothing_items;

public interface ViewAllClothingItemsOutputBoundary {
    void prepareSuccessView(ViewAllClothingItemsOutputData outputData);

    void prepareFailureView(String error);
}

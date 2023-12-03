package use_case.get_clothing_item;

public interface GetClothingItemOutputBoundary {
    void prepareSuccessView(GetClothingItemOutputData outputData);

    void prepareFailView(String error);
}

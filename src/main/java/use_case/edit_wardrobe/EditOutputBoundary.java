package use_case.edit_wardrobe;

public interface EditOutputBoundary {
    void prepareSuccessView(EditOutputData outputData);

    void prepareFailView(String error);
}

package use_case.delete_clothing_item;

public interface DeleteOutputBoundary {
    void prepareSuccessView(DeleteOutputData outputData);

    void prepareFailView(String error);
}

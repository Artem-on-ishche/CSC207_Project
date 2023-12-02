package use_case.update_clothing_item;

public interface UpdateOutputBoundary {
    void prepareSuccessView(UpdateOutputData outputData);

    void prepareFailView(String error);
}

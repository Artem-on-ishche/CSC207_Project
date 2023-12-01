package use_case.create_wardrobe;

public interface CreateOutputBoundary {
    void prepareSuccessView(CreateOutputData outputData);

    void prepareFailView(String error);
}

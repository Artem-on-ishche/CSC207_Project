package service;

public interface OutputBoundary {
    void prepareSuccessView(OutfitOutputData outfit);

    void prepareFailView(String error);
}

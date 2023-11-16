package use_case.generate_outfit;

public interface OutputBoundary {
    void prepareSuccessView(OutfitOutputData outfit);

    void prepareFailView(String error);
}

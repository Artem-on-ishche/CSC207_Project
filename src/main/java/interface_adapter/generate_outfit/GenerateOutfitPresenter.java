package interface_adapter.generate_outfit;

import interface_adapter.ViewManagerModel;
import use_case.generate_outfit.OutfitOutputData;
import use_case.generate_outfit.OutputBoundary;

public class GenerateOutfitPresenter implements OutputBoundary {
    private final ViewManagerModel viewManagerModel;

    public GenerateOutfitPresenter(ViewManagerModel viewManagerModel, GenerateOutfitViewModel generateOutfitViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.generateOutfitViewModel = generateOutfitViewModel;
    }

    private final GenerateOutfitViewModel generateOutfitViewModel;

    @Override
    public void prepareSuccessView(OutfitOutputData outfit) {
        GenerateOutfitState generateOutfitState = generateOutfitViewModel.getState();
        generateOutfitState.setOutfit(outfit.outfit());
        this.generateOutfitViewModel.setState(generateOutfitState);
        generateOutfitViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(generateOutfitViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GenerateOutfitState generateOutfitState = generateOutfitViewModel.getState();
        generateOutfitState.setGenerateOutfitError(error);
        generateOutfitViewModel.firePropertyChanged();
    }
}

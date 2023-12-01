package interface_adapter.create_wardrobe;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import use_case.create_wardrobe.CreateOutputBoundary;
import use_case.create_wardrobe.CreateOutputData;

public class CreateWardrobePresenter implements CreateOutputBoundary {
    private final CreateWardrobeViewModel createWardrobeViewModel;
    private final ViewManagerModel viewModel;

    public CreateWardrobePresenter(ViewManagerModel viewManagerModel, CreateWardrobeViewModel createWardrobeViewModel) {
        this.createWardrobeViewModel = createWardrobeViewModel;
        this.viewModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(CreateOutputData outputData) {


    }

    @Override
    public void prepareFailView(String error) {

    }
}

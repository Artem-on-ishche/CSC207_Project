package interface_adapter.create_wardrobe;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.delete_clothing_item.DeleteState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.create_wardrobe.CreateOutputBoundary;
import use_case.create_wardrobe.CreateOutputData;

public class CreateWardrobePresenter implements CreateOutputBoundary {
    private final CreateWardrobeViewModel createWardrobeViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewModel;

    public CreateWardrobePresenter(ViewManagerModel viewManagerModel,
                                   CreateWardrobeViewModel createWardrobeViewModel, LoggedInViewModel loggedInViewModel) {
        this.createWardrobeViewModel = createWardrobeViewModel;
        this.viewModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(CreateOutputData outputData) {
        CreateWardrobeState createWardrobeState = createWardrobeViewModel.getState();
        createWardrobeState.setDescription(outputData.newClothingItem().getDescription());
        createWardrobeState.setName(outputData.newClothingItem().getName());
//        createWardrobeState.setImageSrc(outputData.newClothingItem().getImage());
        createWardrobeState.setMinimumAppropriateTemperature(outputData.newClothingItem().getMinimumAppropriateTemperature());
        this.createWardrobeViewModel.setState(createWardrobeState);
        createWardrobeViewModel.firePropertyChanged();

        viewModel.setActiveView(loggedInViewModel.getViewName());
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        CreateWardrobeState createWardrobeState = createWardrobeViewModel.getState();
        createWardrobeState.setCreateError(error);
        createWardrobeViewModel.firePropertyChanged();
    }
}

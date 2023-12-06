package interface_adapter.create_wardrobe;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.delete_clothing_item.DeleteState;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.view_all_items.ViewAllItemsState;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import model.ClothingItem;
import use_case.create_wardrobe.CreateOutputBoundary;
import use_case.create_wardrobe.CreateOutputData;

import java.util.List;

public class CreateWardrobePresenter implements CreateOutputBoundary {
    private final CreateWardrobeViewModel createWardrobeViewModel;
    private final ViewAllItemsViewModel viewAllItemsViewModel;
    private final ViewManagerModel viewModel;

    public CreateWardrobePresenter(ViewManagerModel viewManagerModel,
                                   CreateWardrobeViewModel createWardrobeViewModel, ViewAllItemsViewModel viewAllItemsViewModel) {
        this.createWardrobeViewModel = createWardrobeViewModel;
        this.viewModel = viewManagerModel;
        this.viewAllItemsViewModel = viewAllItemsViewModel;
    }

    @Override
    public void prepareSuccessView(CreateOutputData outputData) {
        ViewAllItemsState viewAllItemsState = viewAllItemsViewModel.getState();
        viewAllItemsState.getWardrobe().add(outputData.newClothingItem());
        viewAllItemsViewModel.setState(viewAllItemsState);
        viewAllItemsViewModel.firePropertyChanged();

        viewModel.setActiveView(viewAllItemsViewModel.getViewName());
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        CreateWardrobeState createWardrobeState = createWardrobeViewModel.getState();
        createWardrobeState.setCreateError(error);
        createWardrobeViewModel.firePropertyChanged();
    }
}

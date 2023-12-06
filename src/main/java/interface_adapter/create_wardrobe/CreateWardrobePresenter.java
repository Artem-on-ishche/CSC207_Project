package interface_adapter.create_wardrobe;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsState;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsViewModel;
import model.ClothingItem;
import use_case.create_wardrobe.CreateOutputBoundary;
import use_case.create_wardrobe.CreateOutputData;

import java.util.ArrayList;
import java.util.List;

public class CreateWardrobePresenter implements CreateOutputBoundary {
    private final CreateWardrobeViewModel createWardrobeViewModel;
    private final ViewAllClothingItemsViewModel viewAllClothingItemsViewModel;
    private final ViewManagerModel viewModel;

    public CreateWardrobePresenter(ViewManagerModel viewManagerModel,
                                   CreateWardrobeViewModel createWardrobeViewModel, ViewAllClothingItemsViewModel viewAllClothingItemsViewModel) {
        this.createWardrobeViewModel = createWardrobeViewModel;
        this.viewModel = viewManagerModel;
        this.viewAllClothingItemsViewModel = viewAllClothingItemsViewModel;
    }

    @Override
    public void prepareSuccessView(CreateOutputData outputData) {
        ViewAllClothingItemsState viewAllClothingItemsState = viewAllClothingItemsViewModel.getState();
        
        List<ClothingItem> newWardrobe = new ArrayList<>(viewAllClothingItemsState.getWardrobe());
        newWardrobe.add(outputData.newClothingItem());

        viewAllClothingItemsState.setWardrobe(newWardrobe);
        viewAllClothingItemsViewModel.setState(viewAllClothingItemsState);
        viewAllClothingItemsViewModel.firePropertyChanged();

        viewModel.setActiveView(viewAllClothingItemsViewModel.getViewName());
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        CreateWardrobeState createWardrobeState = createWardrobeViewModel.getState();
        createWardrobeState.setCreateError(error);
        createWardrobeViewModel.firePropertyChanged();
    }
}

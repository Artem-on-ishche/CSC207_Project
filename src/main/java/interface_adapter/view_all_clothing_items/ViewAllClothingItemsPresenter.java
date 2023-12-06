package interface_adapter.view_all_clothing_items;

import interface_adapter.ViewManagerModel;
import use_case.view_all_clothing_items.ViewAllClothingItemsOutputBoundary;
import use_case.view_all_clothing_items.ViewAllClothingItemsOutputData;

public class ViewAllClothingItemsPresenter implements ViewAllClothingItemsOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ViewAllClothingItemsViewModel viewAllClothingItemsViewModel;

    public ViewAllClothingItemsPresenter(ViewManagerModel viewManagerModel, ViewAllClothingItemsViewModel viewAllClothingItemsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewAllClothingItemsViewModel = viewAllClothingItemsViewModel;
    }
    @Override
    public void prepareSuccessView(ViewAllClothingItemsOutputData outputData) {
        ViewAllClothingItemsState viewAllClothingItemsState = viewAllClothingItemsViewModel.getState();
        viewAllClothingItemsState.setWardrobe(outputData.clothingItems());

        this.viewAllClothingItemsViewModel.setState(viewAllClothingItemsState);
        this.viewAllClothingItemsViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(viewAllClothingItemsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String error) {
        viewAllClothingItemsViewModel.firePropertyChanged();
    }
}

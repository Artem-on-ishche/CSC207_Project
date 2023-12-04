package interface_adapter.view_all_items;

import interface_adapter.ViewManagerModel;
import use_case.view_all_clothing_items.ViewAllClothingItemsOutputBoundary;
import use_case.view_all_clothing_items.ViewAllClothingItemsOutputData;

public class ViewAllItemsPresenter implements ViewAllClothingItemsOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ViewAllItemsViewModel viewAllItemsViewModel;

    public ViewAllItemsPresenter(ViewManagerModel viewManagerModel, ViewAllItemsViewModel viewAllItemsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewAllItemsViewModel = viewAllItemsViewModel;
    }
    @Override
    public void prepareSuccessView(ViewAllClothingItemsOutputData outputData) {
        ViewAllItemsState viewAllItemsState = viewAllItemsViewModel.getState();
        viewAllItemsState.setWardrobe(outputData.clothingItems());
        this.viewAllItemsViewModel.setState(viewAllItemsState);
        this.viewAllItemsViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(viewAllItemsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String error) {
        viewAllItemsViewModel.firePropertyChanged();
    }
}

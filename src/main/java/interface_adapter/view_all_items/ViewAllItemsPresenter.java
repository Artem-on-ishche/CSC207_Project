package interface_adapter.view_all_items;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.view_all_clothing_items.ViewAllClothingItemsOutputBoundary;
import use_case.view_all_clothing_items.ViewAllClothingItemsOutputData;

public class ViewAllItemsPresenter implements ViewAllClothingItemsOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewAllItemsViewModel viewAllItemsViewModel;

    public ViewAllItemsPresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel, ViewAllItemsViewModel viewAllItemsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.viewAllItemsViewModel = viewAllItemsViewModel;
    }
    @Override
    public void prepareSuccessView(ViewAllClothingItemsOutputData outputData) {
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setWardrobe(outputData.clothingItems());
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String error) {
        viewAllItemsViewModel.firePropertyChanged();
    }
}

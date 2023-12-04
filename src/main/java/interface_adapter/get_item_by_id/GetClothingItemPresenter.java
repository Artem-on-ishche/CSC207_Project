package interface_adapter.get_item_by_id;

import interface_adapter.ViewManagerModel;
import use_case.get_clothing_item.GetClothingItemOutputBoundary;
import use_case.get_clothing_item.GetClothingItemOutputData;

public class GetClothingItemPresenter implements GetClothingItemOutputBoundary {
    private final GetItemViewModel getItemViewModel;
    private final ViewManagerModel viewManagerModel;


    public GetClothingItemPresenter(GetItemViewModel getItemViewModel, ViewManagerModel viewManagerModel) {
        this.getItemViewModel = getItemViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(GetClothingItemOutputData response) {
        GetItemState getItemState = getItemViewModel.getState();
        getItemState.setClothingItem(response.clothingItem());
        this.getItemViewModel.setState(getItemState);
        getItemViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(getItemViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GetItemState getItemState = getItemViewModel.getState();
        getItemState.setGetItemError(error);
        getItemViewModel.firePropertyChanged();
    }
}

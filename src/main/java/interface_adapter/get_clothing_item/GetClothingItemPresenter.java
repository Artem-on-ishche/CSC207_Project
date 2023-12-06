package interface_adapter.get_clothing_item;

import interface_adapter.ViewManagerModel;
import use_case.get_clothing_item.GetClothingItemOutputBoundary;
import use_case.get_clothing_item.GetClothingItemOutputData;

public class GetClothingItemPresenter implements GetClothingItemOutputBoundary {
    private final GetClothingItemViewModel getClothingItemViewModel;
    private final ViewManagerModel viewManagerModel;


    public GetClothingItemPresenter(GetClothingItemViewModel getClothingItemViewModel, ViewManagerModel viewManagerModel) {
        this.getClothingItemViewModel = getClothingItemViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(GetClothingItemOutputData response) {
        GetClothingItemState getClothingItemState = getClothingItemViewModel.getState();
        if(response.clothingItem().isPresent()) {
            getClothingItemState.setClothingItem(response.clothingItem().get());
        }
        this.getClothingItemViewModel.setState(getClothingItemState);
        getClothingItemViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(getClothingItemViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GetClothingItemState getClothingItemState = getClothingItemViewModel.getState();
        getClothingItemState.setGetItemError(error);
        getClothingItemViewModel.firePropertyChanged();
    }
}

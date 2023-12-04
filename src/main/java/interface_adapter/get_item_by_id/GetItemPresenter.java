package interface_adapter.get_item_by_id;

import interface_adapter.ViewManagerModel;
import use_case.get_item_by_id.GetItemByIdOutputBoundary;
import use_case.get_item_by_id.GetItemByIdOutputData;

public class GetItemPresenter implements GetItemByIdOutputBoundary {
    private final GetItemViewModel getItemViewModel;
    private final ViewManagerModel viewManagerModel;


    public GetItemPresenter(GetItemViewModel getItemViewModel, ViewManagerModel viewManagerModel) {
        this.getItemViewModel = getItemViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(GetItemByIdOutputData response) {
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

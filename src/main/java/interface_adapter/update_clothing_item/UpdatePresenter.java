package interface_adapter.update_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_item_by_id.GetItemState;
import interface_adapter.get_item_by_id.GetItemViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import model.ClothingItem;
import use_case.update_clothing_item.UpdateOutputBoundary;
import use_case.update_clothing_item.UpdateOutputData;


public class UpdatePresenter implements UpdateOutputBoundary {
    private final UpdateViewModel updateViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewModel;

    private final GetItemViewModel getItemViewModel;

    public UpdatePresenter(ViewManagerModel viewManagerModel,
                                   UpdateViewModel updateViewModel, LoggedInViewModel loggedInViewModel, GetItemViewModel getItemViewModel) {
        this.updateViewModel = updateViewModel;
        this.viewModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.getItemViewModel = getItemViewModel;
    }

    @Override
    public void prepareSuccessView(UpdateOutputData outputData) {
        LoggedInState loggedInState = loggedInViewModel.getState();
        for (ClothingItem clothingItem : loggedInState.getWardrobe()) {
            if (clothingItem.getId().equals(outputData.updatedClothingItem().getId())) {
                loggedInState.getWardrobe().set(loggedInState.getWardrobe().indexOf(clothingItem), outputData.updatedClothingItem());
                break;
            }
        }
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();

        UpdateState updateState = updateViewModel.getState();
        updateState.setClothingItem(outputData.updatedClothingItem());
        updateViewModel.setState(updateState);
        updateViewModel.firePropertyChanged();

        GetItemState getItemState = getItemViewModel.getState();
        getItemState.setClothingItem(outputData.updatedClothingItem());
        getItemViewModel.setState(getItemState);
        getItemViewModel.firePropertyChanged();

        viewModel.setActiveView(getItemViewModel.getViewName());
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        UpdateState updateState = updateViewModel.getState();
        updateState.setUpdateError(error);
        updateViewModel.firePropertyChanged();
    }
}

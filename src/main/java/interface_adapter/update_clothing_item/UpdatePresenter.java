package interface_adapter.update_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_clothing_item.GetItemState;
import interface_adapter.get_clothing_item.GetItemViewModel;
import interface_adapter.view_all_items.ViewAllItemsState;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import model.ClothingItem;
import use_case.update_clothing_item.UpdateOutputBoundary;
import use_case.update_clothing_item.UpdateOutputData;


public class UpdatePresenter implements UpdateOutputBoundary {
    private final UpdateViewModel updateViewModel;
    private final ViewManagerModel viewModel;
    private final GetItemViewModel getItemViewModel;

    private final ViewAllItemsViewModel viewAllItemsViewModel;

    public UpdatePresenter(ViewManagerModel viewManagerModel,
                                   UpdateViewModel updateViewModel, ViewAllItemsViewModel viewAllItemsViewModel, GetItemViewModel getItemViewModel) {
        this.updateViewModel = updateViewModel;
        this.viewModel = viewManagerModel;
        this.viewAllItemsViewModel = viewAllItemsViewModel;
        this.getItemViewModel = getItemViewModel;
    }

    @Override
    public void prepareSuccessView(UpdateOutputData outputData) {
        ViewAllItemsState viewAllItemsState = viewAllItemsViewModel.getState();
        for (ClothingItem clothingItem : viewAllItemsState.getWardrobe()) {
            if (clothingItem.getId().equals(outputData.updatedClothingItem().getId())) {
                viewAllItemsState.getWardrobe().set(viewAllItemsState.getWardrobe().indexOf(clothingItem), outputData.updatedClothingItem());
                break;
            }
        }
        viewAllItemsViewModel.setState(viewAllItemsState);
        viewAllItemsViewModel.firePropertyChanged();

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

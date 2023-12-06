package interface_adapter.update_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_clothing_item.GetClothingItemState;
import interface_adapter.get_clothing_item.GetClothingItemViewModel;
import interface_adapter.view_all_items.ViewAllItemsState;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import model.ClothingItem;
import use_case.update_clothing_item.UpdateOutputBoundary;
import use_case.update_clothing_item.UpdateOutputData;


public class UpdatePresenter implements UpdateOutputBoundary {
    private final UpdateViewModel updateViewModel;
    private final ViewManagerModel viewModel;
    private final GetClothingItemViewModel getClothingItemViewModel;

    private final ViewAllItemsViewModel viewAllItemsViewModel;

    public UpdatePresenter(ViewManagerModel viewManagerModel,
                                   UpdateViewModel updateViewModel, ViewAllItemsViewModel viewAllItemsViewModel, GetClothingItemViewModel getClothingItemViewModel) {
        this.updateViewModel = updateViewModel;
        this.viewModel = viewManagerModel;
        this.viewAllItemsViewModel = viewAllItemsViewModel;
        this.getClothingItemViewModel = getClothingItemViewModel;
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

        GetClothingItemState getClothingItemState = getClothingItemViewModel.getState();
        getClothingItemState.setClothingItem(outputData.updatedClothingItem());
        getClothingItemViewModel.setState(getClothingItemState);
        getClothingItemViewModel.firePropertyChanged();

        viewModel.setActiveView(getClothingItemViewModel.getViewName());
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        UpdateState updateState = updateViewModel.getState();
        updateState.setUpdateError(error);
        updateViewModel.firePropertyChanged();
    }
}

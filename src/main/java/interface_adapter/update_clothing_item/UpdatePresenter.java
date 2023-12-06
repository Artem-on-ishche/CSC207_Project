package interface_adapter.update_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_clothing_item.GetClothingItemState;
import interface_adapter.get_clothing_item.GetClothingItemViewModel;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsState;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsViewModel;
import model.ClothingItem;
import use_case.update_clothing_item.UpdateOutputBoundary;
import use_case.update_clothing_item.UpdateOutputData;

import java.util.ArrayList;
import java.util.List;


public class UpdatePresenter implements UpdateOutputBoundary {
    private final UpdateViewModel updateViewModel;
    private final ViewManagerModel viewModel;
    private final GetClothingItemViewModel getClothingItemViewModel;

    private final ViewAllClothingItemsViewModel viewAllClothingItemsViewModel;

    public UpdatePresenter(ViewManagerModel viewManagerModel,
                           UpdateViewModel updateViewModel, ViewAllClothingItemsViewModel viewAllClothingItemsViewModel, GetClothingItemViewModel getClothingItemViewModel) {
        this.updateViewModel = updateViewModel;
        this.viewModel = viewManagerModel;
        this.viewAllClothingItemsViewModel = viewAllClothingItemsViewModel;
        this.getClothingItemViewModel = getClothingItemViewModel;
    }

    @Override
    public void prepareSuccessView(UpdateOutputData outputData) {
        ViewAllClothingItemsState viewAllClothingItemsState = viewAllClothingItemsViewModel.getState();

        List<ClothingItem> newWardrobe = new ArrayList<>(viewAllClothingItemsState.getWardrobe());
        for (ClothingItem clothingItem : viewAllClothingItemsState.getWardrobe()) {
            if (clothingItem.getId().equals(outputData.updatedClothingItem().getId())) {
                newWardrobe.set(viewAllClothingItemsState.getWardrobe().indexOf(clothingItem), outputData.updatedClothingItem());
                break;
            }
        }

        viewAllClothingItemsState.setWardrobe(newWardrobe);
        viewAllClothingItemsViewModel.setState(viewAllClothingItemsState);
        viewAllClothingItemsViewModel.firePropertyChanged();

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

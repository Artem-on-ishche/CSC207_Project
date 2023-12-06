package interface_adapter.delete_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsState;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsViewModel;
import model.ClothingItem;
import use_case.delete_clothing_item.DeleteOutputBoundary;
import use_case.delete_clothing_item.DeleteOutputData;

import java.util.ArrayList;
import java.util.List;

public class DeletePresenter implements DeleteOutputBoundary {
    private final DeleteViewModel deleteViewModel;
    private final ViewManagerModel viewManagerModel;

    private final ViewAllClothingItemsViewModel viewAllClothingItemsViewModel;

    public DeletePresenter(DeleteViewModel deleteViewModel, ViewManagerModel viewManagerModel, ViewAllClothingItemsViewModel viewAllClothingItemsViewModel) {
        this.deleteViewModel = deleteViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewAllClothingItemsViewModel = viewAllClothingItemsViewModel;
    }

    @Override
    public void prepareSuccessView(DeleteOutputData response) {
        ViewAllClothingItemsState viewAllClothingItemsState = viewAllClothingItemsViewModel.getState();
        List<ClothingItem> newWardrobe = new ArrayList<>(viewAllClothingItemsState.getWardrobe());
        for (ClothingItem clothingItem : viewAllClothingItemsState.getWardrobe()) {
            if (clothingItem.getId().equals(deleteViewModel.getState().getDeletedItemId())) {
                newWardrobe.remove(clothingItem);
                break;
            }
        }

        viewAllClothingItemsState.setWardrobe(newWardrobe);
        this.viewAllClothingItemsViewModel.setState(viewAllClothingItemsState);
        this.viewAllClothingItemsViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(viewAllClothingItemsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        DeleteState deleteState = deleteViewModel.getState();
        deleteState.setDeleteError(error);
        deleteViewModel.firePropertyChanged();
    }
}

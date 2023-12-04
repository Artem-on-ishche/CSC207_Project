package interface_adapter.delete_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.view_all_items.ViewAllItemsState;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import model.ClothingItem;
import use_case.delete_clothing_item.DeleteOutputBoundary;
import use_case.delete_clothing_item.DeleteOutputData;

import java.util.ArrayList;
import java.util.List;

public class DeletePresenter implements DeleteOutputBoundary {
    private final DeleteViewModel deleteViewModel;
    private final ViewManagerModel viewManagerModel;

    private final ViewAllItemsViewModel viewAllItemsViewModel;

    public DeletePresenter(DeleteViewModel deleteViewModel, ViewManagerModel viewManagerModel, ViewAllItemsViewModel viewAllItemsViewModel) {
        this.deleteViewModel = deleteViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewAllItemsViewModel = viewAllItemsViewModel;
    }

    @Override
    public void prepareSuccessView(DeleteOutputData response) {
        ViewAllItemsState viewAllItemsState = viewAllItemsViewModel.getState();
        List<ClothingItem> newWardrobe = new ArrayList<>(viewAllItemsState.getWardrobe());
        for (ClothingItem clothingItem : viewAllItemsState.getWardrobe()) {
            if (clothingItem.getId().equals(deleteViewModel.getState().getDeletedItemId())) {
                newWardrobe.remove(clothingItem);
                break;
            }
        }

        viewAllItemsState.setWardrobe(newWardrobe);
        this.viewAllItemsViewModel.setState(viewAllItemsState);
        this.viewAllItemsViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(viewAllItemsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        DeleteState deleteState = deleteViewModel.getState();
        deleteState.setDeleteError(error);
        deleteViewModel.firePropertyChanged();
    }
}

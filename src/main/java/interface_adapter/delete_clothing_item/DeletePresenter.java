package interface_adapter.delete_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import model.ClothingItem;
import use_case.delete_clothing_item.DeleteOutputBoundary;
import use_case.delete_clothing_item.DeleteOutputData;

import java.util.ArrayList;
import java.util.List;

public class DeletePresenter implements DeleteOutputBoundary {
    private final DeleteViewModel deleteViewModel;
    private final ViewManagerModel viewManagerModel;

    private final LoggedInViewModel loggedInViewModel;

    public DeletePresenter(DeleteViewModel deleteViewModel, ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel) {
        this.deleteViewModel = deleteViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(DeleteOutputData response) {
        LoggedInState loggedInState = loggedInViewModel.getState();
        List<ClothingItem> newWardrobe = new ArrayList<>(loggedInState.getWardrobe());
        for (ClothingItem clothingItem : loggedInState.getWardrobe()) {
            if (clothingItem.getId().equals(deleteViewModel.getState().getDeletedItemId())) {
                newWardrobe.remove(clothingItem);
                break;
            }
        }

        loggedInState.setWardrobe(newWardrobe);
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        DeleteState deleteState = deleteViewModel.getState();
        deleteState.setDeleteError(error);
        deleteViewModel.firePropertyChanged();
    }
}

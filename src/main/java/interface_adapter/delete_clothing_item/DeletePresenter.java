package interface_adapter.delete_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.delete_clothing_item.DeleteOutputBoundary;
import use_case.delete_clothing_item.DeleteOutputData;

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
        DeleteState deleteState = deleteViewModel.getState();
        this.deleteViewModel.setState(deleteState);
        deleteViewModel.firePropertyChanged();

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

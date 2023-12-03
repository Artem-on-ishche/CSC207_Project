package interface_adapter.update_clothing_item;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.update_clothing_item.UpdateOutputBoundary;
import use_case.update_clothing_item.UpdateOutputData;

public class UpdatePresenter implements UpdateOutputBoundary {
    private final UpdateViewModel updateViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewModel;

    public UpdatePresenter(ViewManagerModel viewManagerModel,
                                   UpdateViewModel updateViewModel, LoggedInViewModel loggedInViewModel) {
        this.updateViewModel = updateViewModel;
        this.viewModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(UpdateOutputData outputData) {
        UpdateState updateState = updateViewModel.getState();
        updateState.setClothingItem(outputData.updatedClothingItem());
        this.updateViewModel.setState(updateState);
        updateViewModel.firePropertyChanged();

        viewModel.setActiveView(loggedInViewModel.getViewName());
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        UpdateState updateState = updateViewModel.getState();
        updateState.setUpdateError(error);
        updateViewModel.firePropertyChanged();
    }
}

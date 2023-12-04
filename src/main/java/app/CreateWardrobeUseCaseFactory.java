package app;

import data_access.FileImageCreator;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.CreateWardrobeController;
import interface_adapter.create_wardrobe.CreateWardrobePresenter;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.create_wardrobe.*;
import view.CreateWardrobeView;

import javax.swing.*;
import java.io.IOException;

public class CreateWardrobeUseCaseFactory {
    private CreateWardrobeUseCaseFactory() {
    }

    public static CreateWardrobeView create(
            ViewManagerModel viewManagerModel,
            CreateWardrobeViewModel createWardrobeViewModel,
            CreateDataAccess createDataAccess,
            ClothingIdentificationService clothingIdentificationService,
            LoggedInViewModel loggedInViewModel
    ) {
        try {
            CreateWardrobeController signupController = createUserSignupUseCase(viewManagerModel, createWardrobeViewModel, createDataAccess, clothingIdentificationService, loggedInViewModel);

            return new CreateWardrobeView(signupController, createWardrobeViewModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static CreateWardrobeController createUserSignupUseCase(ViewManagerModel viewManagerModel, CreateWardrobeViewModel createWardrobeViewModel, CreateDataAccess userDataAccessObject, ClothingIdentificationService clothingIdentificationService, LoggedInViewModel loggedInViewModel) throws IOException {

        CreateOutputBoundary createOutputBoundary = new CreateWardrobePresenter(viewManagerModel, createWardrobeViewModel, loggedInViewModel);
        ImageCreator imageCreator = new FileImageCreator();


        CreateInputBoundary userSignupInteractor = new CreateInteractor(
                userDataAccessObject, createOutputBoundary, clothingIdentificationService, imageCreator);

        return new CreateWardrobeController(userSignupInteractor);
    }
}

package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.CreateWardrobeController;
import interface_adapter.create_wardrobe.CreateWardrobePresenter;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;
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
            ClothingIdentificationService clothingIdentificationService
    ) {
        try {
            CreateWardrobeController signupController = createUserSignupUseCase(viewManagerModel, createWardrobeViewModel, createDataAccess, clothingIdentificationService  );

            return new CreateWardrobeView(signupController, createWardrobeViewModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static CreateWardrobeController createUserSignupUseCase(ViewManagerModel viewManagerModel, CreateWardrobeViewModel createWardrobeViewModel, CreateDataAccess userDataAccessObject, ClothingIdentificationService clothingIdentificationService) throws IOException {

        CreateOutputBoundary createOutputBoundary = new CreateWardrobePresenter(viewManagerModel, createWardrobeViewModel);


        CreateInputBoundary userSignupInteractor = new CreateInteractor(
                userDataAccessObject, createOutputBoundary, clothingIdentificationService);

        return new CreateWardrobeController(userSignupInteractor);
    }
}

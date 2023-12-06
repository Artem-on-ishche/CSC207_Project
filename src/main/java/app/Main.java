package app;

import business_rules.*;
import data_access.api.*;
import data_access.persistence.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.*;
import interface_adapter.delete_clothing_item.*;
import interface_adapter.generate_outfit.*;
import interface_adapter.get_clothing_item.*;
import interface_adapter.login.*;
import interface_adapter.signup.*;
import interface_adapter.update_clothing_item.*;
import interface_adapter.view_all_clothing_items.*;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.create_wardrobe.*;
import use_case.delete_clothing_item.*;
import use_case.generate_outfit.*;
import use_case.get_clothing_item.*;
import use_case.login.*;
import use_case.signup.*;
import use_case.update_clothing_item.*;
import use_case.view_all_clothing_items.*;
import view.*;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Outfit Generator");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        views.setPreferredSize(new Dimension(1450, 1000));

        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        ViewAllClothingItemsViewModel viewAllClothingItemsViewModel = new ViewAllClothingItemsViewModel();
        GenerateOutfitViewModel generateOutfitViewModel = new GenerateOutfitViewModel();
        CreateWardrobeViewModel createWardrobeViewModel = new CreateWardrobeViewModel();
        GetClothingItemViewModel getClothingItemViewModel = new GetClothingItemViewModel();
        DeleteViewModel deleteViewModel = new DeleteViewModel();

        PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionService();

        UserDao userDao = new UserDao();
        ClothingItemDao clothingItemDao = new ClothingItemDao();
        IpAddressLocationDataSource locationDao = new IpAddressLocationDataSource();
        OpenMeteoAPI openMeteoAPI = new OpenMeteoAPI();


        //Sign Up
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);

        SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDao, signupOutputBoundary, passwordEncryptionService);
        SignupController signupController = new SignupController(userSignupInteractor);
        SignupView signupView = new SignupView(signupController, signupViewModel, viewManagerModel, loginViewModel);

        //Log In
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel);

        LoginInputBoundary userLoginInteractor = new LoginInteractor(
                userDao, loginOutputBoundary, passwordEncryptionService);
        LoginController loginController = new LoginController(userLoginInteractor);
        LoginView loginView = new LoginView(loginViewModel, loginController);

        //View Wardrobe
        ViewAllClothingItemsOutputBoundary viewAllClothingItemsOutputBoundary = new ViewAllClothingItemsPresenter(viewManagerModel, viewAllClothingItemsViewModel);
        ViewAllClothingItemsInputBoundary viewAllClothingItemsInteractor = new ViewAllClothingItemsInteractor(
                clothingItemDao,
                viewAllClothingItemsOutputBoundary);
        ViewAllClothingItemsController viewAllClothingItemsController = new ViewAllClothingItemsController(viewAllClothingItemsInteractor);

        //Outfit Generator
        OutputBoundary outputBoundary = new GenerateOutfitPresenter(viewManagerModel, generateOutfitViewModel);
        RandomClothingItemSelectionStrategy selectionStrategy = new RandomClothingItemSelectionStrategy();
        OutfitGenerator outfitGenerator = new OutfitGenerator(selectionStrategy);
        InputBoundary interactor = new OutfitGenerationInteractor(
                locationDao,
                openMeteoAPI,
                clothingItemDao,
                outputBoundary,
                outfitGenerator
                );
        GenerateOutfitController generateOutfitController = new GenerateOutfitController(interactor);


        //Logged In
        LoggedInView loggedInView = new LoggedInView(loggedInViewModel, viewAllClothingItemsController, generateOutfitController, generateOutfitViewModel, viewManagerModel, loginViewModel);


        //Generate Outfit
        GenerateOutfitView generateOutfitView = new GenerateOutfitView(generateOutfitViewModel, viewManagerModel, loggedInViewModel);

        //Add Item
        CreateOutputBoundary createOutputBoundary = new CreateWardrobePresenter(viewManagerModel, createWardrobeViewModel, viewAllClothingItemsViewModel);
        FileImageCreator fileImageCreator = new FileImageCreator();
        FashionAPI fashionAPI = new FashionAPI();
        CreateInputBoundary createInputBoundary = new CreateInteractor(
                clothingItemDao,
                createOutputBoundary,
                fashionAPI,
                fileImageCreator)
                ;

        CreateWardrobeController  createWardrobeController = new CreateWardrobeController(createInputBoundary);
        CreateWardrobeView createWardrobeView = new CreateWardrobeView(createWardrobeController, createWardrobeViewModel, loggedInViewModel, viewManagerModel, viewAllClothingItemsViewModel);

        //Show one Item
        GetClothingItemOutputBoundary getClothingItemOutputBoundary = new GetClothingItemPresenter(getClothingItemViewModel, viewManagerModel);
        GetClothingItemInputBoundary getClothingItemInputBoundary = new GetClothingItemInteractor(
                clothingItemDao,
                getClothingItemOutputBoundary);

        GetClothingItemController getClothingItemController = new GetClothingItemController(getClothingItemInputBoundary);
        //Show Wardrobe
        ShowWardrobeView showWardrobeView = new ShowWardrobeView(viewAllClothingItemsViewModel, viewManagerModel, createWardrobeViewModel, loggedInViewModel, getClothingItemController);

        //Update Controller
        UpdateOutputBoundary updateOutputBoundary = new UpdatePresenter(viewManagerModel, new UpdateViewModel(), viewAllClothingItemsViewModel, getClothingItemViewModel);
        UpdateInputBoundary updateInputBoundary = new UpdateInteractor(updateOutputBoundary, clothingItemDao);
        UpdateController updateController = new UpdateController(updateInputBoundary);

        //Delete Controller
        DeleteOutputBoundary deleteOutputBoundary = new DeletePresenter(new DeleteViewModel(), viewManagerModel, viewAllClothingItemsViewModel);
        DeleteInputBoundary deleteInputBoundary = new DeleteInteractor(deleteOutputBoundary, clothingItemDao);
        DeleteController deleteController = new DeleteController(deleteInputBoundary);

        //Edit Item
        EditItemView editItemView = new EditItemView(getClothingItemViewModel, updateController, deleteController, viewManagerModel, viewAllClothingItemsViewModel, deleteViewModel);


        views.add(signupView, signupViewModel.getViewName());
        views.add(loginView, loginViewModel.getViewName());
        views.add(loggedInView, loggedInView.viewName);
        views.add(showWardrobeView, showWardrobeView.getViewName());
        views.add(createWardrobeView, createWardrobeView.viewName);
        views.add(editItemView, editItemView.viewName);
        views.add(generateOutfitView, generateOutfitView.viewName);

        viewManagerModel.setActiveView(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}

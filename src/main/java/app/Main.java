package app;

import business_rules.OutfitGenerator;
import business_rules.PasswordEncryptionService;
import data_access.api.FashionAPI;
import data_access.api.IpAddressLocationDataSource;
import data_access.api.OpenMeteoAPI;
import data_access.persistence.ClothingItemDao;
import data_access.persistence.FileImageCreator;
import data_access.persistence.UserDao;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.CreateWardrobeController;
import interface_adapter.create_wardrobe.CreateWardrobePresenter;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;
import interface_adapter.generate_outfit.GenerateOutfitController;
import interface_adapter.generate_outfit.GenerateOutfitPresenter;
import interface_adapter.generate_outfit.GenerateOutfitViewModel;
import interface_adapter.get_clothing_item.GetClothingItemPresenter;
import interface_adapter.get_clothing_item.GetItemController;
import interface_adapter.get_clothing_item.GetItemViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.update_clothing_item.UpdateController;
import interface_adapter.update_clothing_item.UpdatePresenter;
import interface_adapter.update_clothing_item.UpdateViewModel;
import interface_adapter.view_all_items.ViewAllItemsController;
import interface_adapter.view_all_items.ViewAllItemsPresenter;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import model.ClothingItem;
import model.ClothingType;
import interface_adapter.logged_in.LoggedInViewModel;
import model.User;
import use_case.create_wardrobe.*;
import use_case.generate_outfit.InputBoundary;
import use_case.generate_outfit.OutfitGenerationInteractor;
import use_case.generate_outfit.OutputBoundary;
import use_case.generate_outfit.RandomClothingItemSelectionStrategy;
import use_case.get_clothing_item.GetClothingItemInputBoundary;
import use_case.get_clothing_item.GetClothingItemInteractor;
import use_case.get_clothing_item.GetClothingItemOutputBoundary;
import use_case.login.LoginDataAccessInterface;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.signup.SignupDataAccessInterface;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.update_clothing_item.UpdateInputBoundary;
import use_case.update_clothing_item.UpdateInteractor;
import use_case.update_clothing_item.UpdateOutputBoundary;
import use_case.view_all_clothing_items.ViewAllClothingItemsInputBoundary;
import use_case.view_all_clothing_items.ViewAllClothingItemsInteractor;
import use_case.view_all_clothing_items.ViewAllClothingItemsOutputBoundary;
import view.*;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("ЛОХІ");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();


        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        views.setPreferredSize(new Dimension(500, 500));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screenSize.width - views.getPreferredSize().width) / 2;
        int centerY = (screenSize.height - views.getPreferredSize().height) / 2;
        views.setBounds(centerX, centerY, views.getPreferredSize().width, views.getPreferredSize().height);

        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        ViewAllItemsViewModel viewAllItemsViewModel = new ViewAllItemsViewModel();
        GenerateOutfitViewModel generateOutfitViewModel = new GenerateOutfitViewModel();
        CreateWardrobeViewModel createWardrobeViewModel = new CreateWardrobeViewModel();
        GetItemViewModel getItemViewModel = new GetItemViewModel();

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
        ViewAllClothingItemsOutputBoundary viewAllClothingItemsOutputBoundary = new ViewAllItemsPresenter(viewManagerModel, viewAllItemsViewModel);
        ViewAllClothingItemsInputBoundary viewAllClothingItemsInteractor = new ViewAllClothingItemsInteractor(
                clothingItemDao,
                viewAllClothingItemsOutputBoundary);
        ViewAllItemsController viewAllItemsController = new ViewAllItemsController(viewAllClothingItemsInteractor);

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
        LoggedInView loggedInView = new LoggedInView(loggedInViewModel, viewAllItemsController, generateOutfitController);


        //Generate Outfit
        GenerateOutgitView generateOutgitView = new GenerateOutgitView();

        //Add Item
        CreateOutputBoundary createOutputBoundary = new CreateWardrobePresenter(viewManagerModel, createWardrobeViewModel, viewAllItemsViewModel);
        FileImageCreator fileImageCreator = new FileImageCreator();
        FashionAPI fashionAPI = new FashionAPI();
        CreateInputBoundary createInputBoundary = new CreateInteractor(
                clothingItemDao,
                createOutputBoundary,
                fashionAPI,
                fileImageCreator)
                ;

        CreateWardrobeController  createWardrobeController = new CreateWardrobeController(createInputBoundary);
        CreateWardrobeView createWardrobeView = new CreateWardrobeView(createWardrobeController, createWardrobeViewModel, loggedInViewModel);

        //Show one Item
        GetClothingItemOutputBoundary getClothingItemOutputBoundary = new GetClothingItemPresenter(getItemViewModel, viewManagerModel);
        GetClothingItemInputBoundary getClothingItemInputBoundary = new GetClothingItemInteractor(
                clothingItemDao,
                getClothingItemOutputBoundary);

        GetItemController getItemController = new GetItemController(getClothingItemInputBoundary);
        //Show Wardrobe
        ShowWardrobeView showWardrobeView = new ShowWardrobeView(viewAllItemsViewModel, viewManagerModel, createWardrobeViewModel, loggedInViewModel,getItemController);

        //Update Controller
        UpdateOutputBoundary updateOutputBoundary = new UpdatePresenter(viewManagerModel, new UpdateViewModel(), viewAllItemsViewModel, getItemViewModel);
        UpdateInputBoundary updateInputBoundary = new UpdateInteractor(updateOutputBoundary, clothingItemDao);
        UpdateController updateController = new UpdateController(updateInputBoundary);

        //Edit Item
        EditItemView editItemView = new EditItemView(getItemViewModel, updateController);

        views.add(signupView, signupViewModel.getViewName());
        views.add(loginView, loginViewModel.getViewName());
        views.add(loggedInView, loggedInView.viewName);
        views.add(showWardrobeView, showWardrobeView.getViewName());
        views.add(createWardrobeView, createWardrobeView.viewName);
        views.add(editItemView, editItemView.viewName);

        viewManagerModel.setActiveView(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}

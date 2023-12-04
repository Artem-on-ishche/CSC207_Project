package app;

import business_rules.PasswordEncryptionService;
import data_access.InMemoryClothingDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import model.ClothingType;
import interface_adapter.logged_in.LoggedInViewModel;
import model.User;
import use_case.create_wardrobe.ClothingIdentificationService;
import use_case.create_wardrobe.CreateDataAccess;
import use_case.login.LoginDataAccessInterface;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.signup.SignupDataAccessInterface;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.CreateWardrobeView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;

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


        CreateDataAccess dataAccessObject = new InMemoryClothingDataAccessObject();

        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        CreateWardrobeViewModel createWardrobeViewModel = new CreateWardrobeViewModel();

        PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionService();
        ViewAllItemsViewModel viewAllItemsViewModel = new ViewAllItemsViewModel();

        ClothingIdentificationService clothingIdentificationService = new ClothingIdentificationService() {
            @Override
            public ClothingType identifyClothingItem(String imageSrc) {
                return null;
            }
        };

        SignupDataAccessInterface userDataAccessObject = new SignupDataAccessInterface() {
            @Override
            public boolean existsByName(String identifier) {
                return false;
            }

            @Override
            public void save(User user) {

            }
        };


        //Sign Up
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);

        SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, passwordEncryptionService);
        SignupController signupController = new SignupController(userSignupInteractor);
        SignupView signupView = new SignupView(signupController, signupViewModel);

        //Log In
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel);
        LoginDataAccessInterface loginDataAccessObject = new LoginDataAccessInterface() {
            @Override
            public void save(User user) {

            }

            @Override
            public Optional<User> get(String username) {
                return Optional.empty();
            }
        };
        LoginInputBoundary userLoginInteractor = new LoginInteractor(
                loginDataAccessObject, loginOutputBoundary, passwordEncryptionService);
        LoginController loginController = new LoginController(userLoginInteractor);
        LoginView loginView = new LoginView(loginViewModel, loginController);

        views.add(signupView, signupViewModel.getViewName());
        views.add(loginView, loginViewModel.getViewName());

        viewManagerModel.setActiveView(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}

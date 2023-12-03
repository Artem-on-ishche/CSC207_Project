package app;

import data_access.InMemoryClothingDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.create_wardrobe.ClothingIdentificationService;
import use_case.create_wardrobe.CreateDataAccess;
import view.CreateWardrobeView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
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
        CreateWardrobeViewModel createWardrobeViewModel = new CreateWardrobeViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();

        ClothingIdentificationService clothingIdentificationService = new ClothingIdentificationService() {
            @Override
            public String identifyClothingItem(Image image) {
                return null;
            }
        };

        CreateWardrobeView signupView = CreateWardrobeUseCaseFactory.create(
                viewManagerModel,
                createWardrobeViewModel,
                dataAccessObject,
                clothingIdentificationService,
                loggedInViewModel

        );
        views.add(signupView, signupView.viewName);


        viewManagerModel.setActiveView(signupView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}

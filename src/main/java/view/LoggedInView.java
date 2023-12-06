package view;

import interface_adapter.generate_outfit.GenerateOutfitController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    GenerateOutfitController generateOutfitController;

    JLabel username;

    final JButton logOut;
    private final JButton myWardrobe;
   // private final JButton addItem;
    private final JButton generateOutfit;


    public LoggedInView(LoggedInViewModel loggedInViewModel, ViewAllClothingItemsController viewAllClothingItemsController, GenerateOutfitController generateOutfitController) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.generateOutfitController = generateOutfitController;

        JLabel title = new JLabel("Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        JPanel buttons = new JPanel();
        myWardrobe = new JButton(LoggedInViewModel.MY_WARDROBE_BUTTON_LABEL);
        buttons.add(myWardrobe);
       /* addItem = new JButton(ViewAllItemsViewModel.ADD_CLOTHING_ITEM);
        buttons.add(addItem);*/
        generateOutfit = new JButton(LoggedInViewModel.GENERATE_OUTFIT_BUTTON_LABEL);
        buttons.add(generateOutfit);
        logOut = new JButton(loggedInViewModel.LOGOUT_BUTTON_LABEL);
        buttons.add(logOut);

        logOut.addActionListener(this);

       /* addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(addItem)) {
                    viewManagerModel.setActiveView(createWardrobeViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
            }
        });*/
        myWardrobe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(myWardrobe)) {
                    LoggedInState currentState = loggedInViewModel.getState();

                    String username = currentState.getUsername();
                    viewAllClothingItemsController.execute(username);
                }
            }
        });

        generateOutfit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(generateOutfit)) {
                    LoggedInState currentState = loggedInViewModel.getState();

                    String username = currentState.getUsername();
                    generateOutfitController.execute(username);
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(usernameInfo);
        this.add(username);
        this.add(buttons);
    }


    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        username.setText(state.getUsername());
    }
}
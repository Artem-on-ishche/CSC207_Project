package view;

import interface_adapter.generate_outfit.GenerateOutfitController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "logged in";
    Font buttonFont = new Font("SansSerif", Font.PLAIN, 18);
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

        this.setLayout(null);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int rectangleWidth = 500;
        int rectangleHeight = 400;

        int rectX = centerX - (rectangleWidth / 2);
        int rectY = centerY - (rectangleHeight / 2);

        JLabel usernameInfo = new JLabel("Welcome, ");
        usernameInfo.setFont(new Font("font", Font.PLAIN, 36));
        usernameInfo.setForeground(Color.BLUE);
        usernameInfo.setBounds(540,350,300,50);
        username = new JLabel();
        username.setFont(new Font("font", Font.PLAIN, 36));
        username.setBounds(740,350,485,50);

        JPanel buttons = new JPanel();
        myWardrobe = new JButton(LoggedInViewModel.MY_WARDROBE_BUTTON_LABEL);
        myWardrobe.setPreferredSize(new Dimension(150, 50));
        myWardrobe.setFont(buttonFont);
        buttons.add(myWardrobe);

        generateOutfit = new JButton(LoggedInViewModel.GENERATE_OUTFIT_BUTTON_LABEL);
        generateOutfit.setPreferredSize(new Dimension(300, 50));
        generateOutfit.setFont(buttonFont);
        generateOutfit.setBackground(Color.PINK);
        buttons.add(generateOutfit);

        logOut = new JButton(LoggedInViewModel.LOGOUT_BUTTON_LABEL);
        logOut.setPreferredSize(new Dimension(150, 50));
        logOut.setFont(buttonFont);
        buttons.add(logOut);

        int buttonsX = rectX + (rectangleWidth / 2) + 480;
        int buttonsY = rectY + rectangleHeight + 300;
        buttons.setBounds(buttonsX, buttonsY, 490, 200);
        buttons.setBackground(Color.white);

        logOut.addActionListener(this);

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

        this.add(usernameInfo);
        this.add(username);
        this.add(buttons);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int rectangleWidth = 500;
        int rectangleHeight = 500;

        int rectX = centerX - (rectangleWidth / 2);
        int rectY = centerY - (rectangleHeight / 2) - 20;

        g.fillRect(rectX, rectY, rectangleWidth, rectangleHeight);
        g.setColor(Color.BLACK);
        g.drawRect(rectX, rectY, rectangleWidth, rectangleHeight);
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
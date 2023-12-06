package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;
import interface_adapter.get_clothing_item.GetItemController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.signup.SignupState;
import interface_adapter.view_all_items.ViewAllItemsController;
import interface_adapter.view_all_items.ViewAllItemsState;
import interface_adapter.view_all_items.ViewAllItemsViewModel;
import model.ClothingItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class ShowWardrobeView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "view all";
    private final ViewAllItemsViewModel viewAllItemsViewModel;
    private JPanel clothingItemsPanel;
    private GetItemController getItemController;

    public String getViewName() {
        return viewName;
    }
    private final JButton addItem;

    private final JButton back;

    public ShowWardrobeView(ViewAllItemsViewModel viewAllItemsViewModel, ViewManagerModel viewManagerModel, CreateWardrobeViewModel createWardrobeViewModel, LoggedInViewModel loggedInViewModel, GetItemController getItemController) {
        this.viewAllItemsViewModel = viewAllItemsViewModel;
        this.viewAllItemsViewModel.addPropertyChangeListener(this);
        this.getItemController = getItemController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("My Wardrobe");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();

        addItem = new JButton(ViewAllItemsViewModel.ADD_CLOTHING_ITEM);
        buttons.add(addItem);

        back = new JButton(ViewAllItemsViewModel.BACK_TO_MAIN_VIEW);
        buttons.add(back);

        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(addItem)) {
                    viewManagerModel.setActiveView(createWardrobeViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
            }
        });

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(back)) {
                    viewManagerModel.setActiveView(loggedInViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
            }
        });

        this.add(title);
        this.add(buttons);

        clothingItemsPanel = new JPanel();
        clothingItemsPanel.setLayout(new BoxLayout(clothingItemsPanel, BoxLayout.Y_AXIS));
        this.add(clothingItemsPanel);


    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        /*var newState = evt.getNewValue();
        if (newState instanceof ViewAllItemsState state) {
            if (state.getWardrobe() != null) {
                JOptionPane.showMessageDialog(this, state.getWardrobe());
            }
        }*/
        ViewAllItemsState state = (ViewAllItemsState) evt.getNewValue();
        clothingItemsPanel.removeAll();

        if (state.getWardrobe() != null && !state.getWardrobe().isEmpty()) {
            for (ClothingItem clothingItem : state.getWardrobe()) {
                var image = clothingItem.getImage();
                String name = clothingItem.getName();

                int desiredWidth = 150;
                int desiredHeight = 150;

                try {

                    var scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage.getImageData()));

                    JPanel itemPanel = new JPanel();
                    itemPanel.add(imageLabel);

                    JLabel nameLabel = new JLabel(name);
                    itemPanel.add(nameLabel);

                    itemPanel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            getItemController.execute(clothingItem.getId());
                        }
                    });

                    clothingItemsPanel.add(itemPanel);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        } else {
        }

        clothingItemsPanel.revalidate();
        clothingItemsPanel.repaint();
    }

}

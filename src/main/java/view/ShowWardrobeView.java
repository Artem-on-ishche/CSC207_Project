package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;
import interface_adapter.get_clothing_item.GetClothingItemController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsState;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsViewModel;
import model.ClothingItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private final ViewAllClothingItemsViewModel viewAllClothingItemsViewModel;
    private JPanel clothingItemsPanel;
    private GetClothingItemController getClothingItemController;
    private String selectedClothingItemId;
    private JScrollPane clothingItemsScrollPane;

    private final int columns = 3;

    public String getViewName() {
        return viewName;
    }
    private final JButton addItem;
    private final JButton back;

    public ShowWardrobeView(ViewAllClothingItemsViewModel viewAllClothingItemsViewModel, ViewManagerModel viewManagerModel, CreateWardrobeViewModel createWardrobeViewModel, LoggedInViewModel loggedInViewModel, GetClothingItemController getClothingItemController) {
        this.viewAllClothingItemsViewModel = viewAllClothingItemsViewModel;
        this.viewAllClothingItemsViewModel.addPropertyChangeListener(this);
        this.getClothingItemController = getClothingItemController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JLabel title = new JLabel("My Wardrobe");
        Font titleFont = title.getFont();
        title.setFont(new Font(titleFont.getName(), Font.PLAIN, 50));
        title.setForeground(new Color(3, 13, 33));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();

        addItem = new JButton(ViewAllClothingItemsViewModel.ADD_CLOTHING_ITEM);
        buttons.add(addItem);

        back = new JButton(ViewAllClothingItemsViewModel.BACK_TO_MAIN_VIEW);
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

        clothingItemsPanel = new JPanel(new GridLayout(0, columns));
        clothingItemsScrollPane = new JScrollPane(clothingItemsPanel);
        clothingItemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        clothingItemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(clothingItemsScrollPane);

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
        ViewAllClothingItemsState state = (ViewAllClothingItemsState) evt.getNewValue();
        clothingItemsPanel.removeAll();

        if (state.getWardrobe() != null && !state.getWardrobe().isEmpty()) {
            for (ClothingItem clothingItem : state.getWardrobe()) {

                var image = clothingItem.getImage();
                String name = clothingItem.getName();

                int desiredWidth = 300;
                int desiredHeight = 300;

                try {

                    var scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage.getImageData()));

                    JPanel itemPanel = new JPanel();
                    itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));

                    int spacing = 10;
                    itemPanel.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));

                    itemPanel.add(imageLabel);

                    JLabel nameLabel = new JLabel(name);
                    itemPanel.add(nameLabel);

                    itemPanel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            selectedClothingItemId = String.valueOf(clothingItem.getId());
                            getClothingItemController.execute(clothingItem.getId());
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

        //SwingUtilities.invokeLater(() -> clothingItemsScrollPane.getVerticalScrollBar().setValue(0));

    }

}

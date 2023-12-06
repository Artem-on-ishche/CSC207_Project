package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.generate_outfit.GenerateOutfitState;
import interface_adapter.generate_outfit.GenerateOutfitViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsViewModel;
import model.ClothingItem;
import model.ClothingType;
import model.Outfit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GenerateOutfitView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "generate outfit";

    private final JTextField umbrellaTextField;
    private final ArrayList<JLabel> imageLabels = new ArrayList<>();
    Font buttonFont = new Font("SansSerif", Font.PLAIN, 18);
    private final JPanel imagePanel;
    private final JButton back;

    public GenerateOutfitView(GenerateOutfitViewModel generateOutfitViewModel, ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel) {
        generateOutfitViewModel.addPropertyChangeListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        imagePanel = new JPanel(new GridLayout(0, 1));

        JScrollPane clothingItemsScrollPane = new JScrollPane(imagePanel);
        clothingItemsScrollPane.setViewportView(imagePanel);
        clothingItemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttons = new JPanel();

        back = new JButton(ViewAllClothingItemsViewModel.BACK_TO_MAIN_VIEW);
        back.setPreferredSize(new Dimension(150, 50));
        back.setFont(buttonFont);
        buttons.add(back);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(back)) {
                    viewManagerModel.setActiveView(loggedInViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
            }
        });

        umbrellaTextField = new JTextField();
        umbrellaTextField.setEditable(false);
        umbrellaTextField.setFont(buttonFont);
        umbrellaTextField.setForeground(Color.red);
        umbrellaTextField.setPreferredSize(new Dimension(300, 30));
        buttons.add(umbrellaTextField);

        this.add(buttons);
        this.add(clothingItemsScrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GenerateOutfitState state = (GenerateOutfitState) evt.getNewValue();
        Outfit outfit = state.getOutfit();
        Map<ClothingType, ClothingItem> clothingItems = outfit.getClothingItems();

        var isRaining = outfit.isUmbrellaRequired();
        umbrellaTextField.setText(isRaining ? "Umbrella is required!" : "");


        imagePanel.removeAll();
        imageLabels.clear();

        ClothingType[] order = {ClothingType.HEAD, ClothingType.NECK, ClothingType.INNER_UPPER_BODY, ClothingType.MIDDLE_UPPER_BODY,
                ClothingType.OUTER_UPPER_BODY, ClothingType.LOWER_BODY, ClothingType.HANDS, ClothingType.FOOTWEAR};

        for (ClothingType clothingType : order) {
            if (clothingItems.containsKey(clothingType)) {
                ClothingItem clothingItem = clothingItems.get(clothingType);
                var image = clothingItem.getImage();

                int desiredWidth = 150;
                int desiredHeight = 150;
                try {
                    var scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage.getImageData()));
                    imagePanel.add(imageLabel);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        }

        revalidate();
        repaint();
    }
}

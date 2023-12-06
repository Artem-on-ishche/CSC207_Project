package view;

import interface_adapter.generate_outfit.GenerateOutfitState;
import interface_adapter.generate_outfit.GenerateOutfitViewModel;
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

    private final ArrayList<JLabel> imageLabels = new ArrayList<>();
    private final JPanel imagePanel;

    public GenerateOutfitView(GenerateOutfitViewModel generateOutfitViewModel) {
        generateOutfitViewModel.addPropertyChangeListener(this);
        imagePanel = new JPanel(new GridLayout(0, 1));

        this.add(imagePanel);

        JScrollPane clothingItemsScrollPane = new JScrollPane(imagePanel);
        clothingItemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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
        for (JLabel imageLabel : imageLabels) {
            imagePanel.add(imageLabel);
        }

        revalidate();
        repaint();
    }
}

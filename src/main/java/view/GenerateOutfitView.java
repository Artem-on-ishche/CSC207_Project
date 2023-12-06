package view;

import interface_adapter.generate_outfit.GenerateOutfitState;
import model.ClothingItem;
import model.ClothingType;
import model.Outfit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Map;

public class GenerateOutfitView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "generate outfit";
    private JPanel imagePanel;

    public GenerateOutfitView(){
        imagePanel = new JPanel();
        this.add(imagePanel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GenerateOutfitState state = (GenerateOutfitState) evt.getNewValue();
        Outfit outfit = state.getOutfit();
        Map<ClothingType, ClothingItem> clothingItems = outfit.getClothingItems();
        for (Map.Entry<ClothingType, ClothingItem> entry : clothingItems.entrySet()) {
            ClothingItem clothingItem = entry.getValue();
            var image = clothingItem.getImage();

            int desiredWidth = 150;
            int desiredHeight = 150;

            try {

                var scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage.getImageData()));

                imagePanel.removeAll();
                imagePanel.add(imageLabel);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            revalidate();
            repaint();

        }

    }
}

package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;
import interface_adapter.get_clothing_item.GetClothingItemController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.view_all_clothing_items.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowWardrobeView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "view all";
    private final ViewAllClothingItemsViewModel viewAllClothingItemsViewModel;
    private JPanel clothingItemsPanel;
    private GetClothingItemController getClothingItemController;
    private String selectedClothingItemId;
    private JScrollPane clothingItemsScrollPane;
    private Map<String, ArrayList<ClothingItem>> clothingItemsByType;

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
        clothingItemsByType = new HashMap<>();

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

        clothingItemsPanel = new JPanel();
        clothingItemsScrollPane = new JScrollPane(clothingItemsPanel);
       // clothingItemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        clothingItemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(clothingItemsScrollPane);

    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        ViewAllClothingItemsState state = (ViewAllClothingItemsState) evt.getNewValue();
        clothingItemsPanel.removeAll();
        clothingItemsByType.clear();

        if (state.getWardrobe() != null && !state.getWardrobe().isEmpty()) {
            for (ClothingItem clothingItem : state.getWardrobe()) {
                String clothingType = String.valueOf(clothingItem.getClothingType());
                clothingItemsByType.computeIfAbsent(clothingType, k -> new ArrayList<>()).add(clothingItem);
            }

            clothingItemsPanel.setLayout(new BoxLayout(clothingItemsPanel, BoxLayout.Y_AXIS));
            for (Map.Entry<String, ArrayList<ClothingItem>> entry : clothingItemsByType.entrySet()) {
                String clothingType = entry.getKey();
                ArrayList<ClothingItem> items = entry.getValue();

                JPanel typePanel = null;
                try {
                    typePanel = createTypePanel(clothingType, items);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                clothingItemsPanel.add(typePanel);
            }
        }

        clothingItemsPanel.revalidate();
        clothingItemsPanel.repaint();
    }

    private JPanel createTypePanel(String clothingType, ArrayList<ClothingItem> items) throws IOException {
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));

        JLabel typeLabel = new JLabel(clothingType);
        typeLabel.setFont(new Font(typeLabel.getFont().getName(), Font.BOLD, 14));
        typeLabel.setPreferredSize(new Dimension(200, 30));
        typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        typePanel.add(typeLabel);

        JPanel itemsPanel = new JPanel(new GridLayout(0, columns));

        for (ClothingItem item : items) {
            var image = item.getImage();
            String name = item.getName();

            int desiredWidth = 300;
            int desiredHeight = 300;

            JPanel itemPanel = new JPanel();
            try {
                var scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage.getImageData()));

                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));

                int spacing = 10;
                itemPanel.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));

                itemPanel.add(imageLabel);

                JLabel nameLabel = new JLabel(name);
                itemPanel.add(nameLabel);

                itemPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        selectedClothingItemId = String.valueOf(item.getId());
                        getClothingItemController.execute(item.getId());
                    }
                });
            } finally {

            }

            itemsPanel.add(itemPanel);
        }

        typePanel.add(itemsPanel);

        return typePanel;
    }


}

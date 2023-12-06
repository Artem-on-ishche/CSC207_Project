package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.delete_clothing_item.DeleteController;
import interface_adapter.delete_clothing_item.DeleteState;
import interface_adapter.delete_clothing_item.DeleteViewModel;
import interface_adapter.get_clothing_item.GetClothingItemState;
import interface_adapter.get_clothing_item.GetClothingItemViewModel;
import interface_adapter.update_clothing_item.UpdateController;
import interface_adapter.view_all_clothing_items.ViewAllClothingItemsViewModel;
import model.ClothingItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class EditItemView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "get item";
    Font buttonFont = new Font("SansSerif", Font.PLAIN, 18);
    private final JButton saveChanges;

    private final JButton back;
    private final JButton delete;
    private JPanel imagePanel;

    final JTextField nameInputField = new JTextField(18);
    final JTextField minTempInputField = new JTextField(18);
    final JTextField descriptonInputField = new JTextField(18);


    GetClothingItemViewModel getClothingItemViewModel;
    public EditItemView(GetClothingItemViewModel getClothingItemViewModel, UpdateController updateController, DeleteController deleteController, ViewManagerModel viewManagerModel, ViewAllClothingItemsViewModel viewAllClothingItemsViewModel, DeleteViewModel deleteViewModel) {
        this.getClothingItemViewModel = getClothingItemViewModel;
        this.getClothingItemViewModel.addPropertyChangeListener(this);


        JPanel buttons = new JPanel();
        saveChanges = new JButton(GetClothingItemViewModel.SAVE_CHANGES_LABEL);
        saveChanges.setPreferredSize(new Dimension(150, 50));
        saveChanges.setFont(buttonFont);
        buttons.add(saveChanges);

        back = new JButton(GetClothingItemViewModel.BACK_LABEL);
        back.setPreferredSize(new Dimension(150, 50));
        back.setFont(buttonFont);
        buttons.add(back);

        delete = new JButton(GetClothingItemViewModel.DELETE_LABEL);
        delete.setPreferredSize(new Dimension(150, 50));
        delete.setFont(buttonFont);
        buttons.add(delete);

        JPanel inputs = new JPanel();

        JLabel name = new JLabel("Name              ");
        name.setFont(buttonFont);
        LabelTextPanel nameInfo = new LabelTextPanel(
               name , nameInputField);

        JLabel temp = new JLabel("Min Temperature   ");
        temp.setFont(buttonFont);
        LabelTextPanel minTempInfo = new LabelTextPanel(
                temp, minTempInputField);

        JLabel desc = new JLabel("Description       ");
        desc.setFont(buttonFont);
        LabelTextPanel descriptionTempInfo = new LabelTextPanel(
                desc, descriptonInputField);

        inputs.setLayout(new GridLayout(0, 1));

        inputs.add(nameInfo);
        inputs.add(minTempInfo);
        inputs.add(descriptionTempInfo);

        imagePanel = new JPanel();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        saveChanges.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(saveChanges)) {
                            GetClothingItemState currentState = getClothingItemViewModel.getState();

                            updateController.execute(
                                    currentState.getClothingItem()
                            );
                        }
                    }
                }
        );

        delete.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(delete)) {
                            GetClothingItemState currentState = getClothingItemViewModel.getState();
                            DeleteState deleteState = new DeleteState();
                            deleteState.setDeletedItem(currentState.getClothingItem().getId());

                            deleteViewModel.setState(deleteState);

                            deleteController.execute(
                                    currentState.getClothingItem().getId()
                            );
                        }
                    }
                }
        );

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(back)) {
                    viewManagerModel.setActiveView(viewAllClothingItemsViewModel.getViewName());
                    viewManagerModel.firePropertyChanged();
                }
            }
        });

        nameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                GetClothingItemState currentState = getClothingItemViewModel.getState();
                currentState.getClothingItem().setName(nameInputField.getText() + e.getKeyChar());
                getClothingItemViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        minTempInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char inputChar = e.getKeyChar();
                if (!Character.isDigit(inputChar) && inputChar != '-') {
                    e.consume();
                    return;
                }

                String currentText = minTempInputField.getText();

                if (inputChar == '-' && currentText.contains("-")) {
                    e.consume();
                    return;
                }

                if (currentText.isEmpty() && inputChar == '-') {
                    return;
                }

                StringBuilder newText = new StringBuilder(currentText);
                newText.append(inputChar);

                try {
                GetClothingItemState currentState = getClothingItemViewModel.getState();
                currentState.getClothingItem().setMinimumAppropriateTemperature(Integer.parseInt(newText.toString()));
                getClothingItemViewModel.setState(currentState);
                } catch (NumberFormatException ex) {
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        descriptonInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                GetClothingItemState currentState = getClothingItemViewModel.getState();
                currentState.getClothingItem().setDescription((descriptonInputField.getText() + e.getKeyChar()).describeConstable());
                getClothingItemViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        this.add(imagePanel);
        this.add(inputs);
        this.add(buttons);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
       /* var newState = evt.getNewValue();
        if (newState instanceof GetItemState state) {
            if (state.getClothingItem() != null) {
                JOptionPane.showMessageDialog(this, state.getClothingItem());
            }

        }*/
        GetClothingItemState state = (GetClothingItemState) evt.getNewValue();
        ClothingItem clothingItem = state.getClothingItem();
        var image = clothingItem.getImage();

        int desiredWidth = 300;
        int desiredHeight = 300;

        try {

            var scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage.getImageData()));

            imagePanel.removeAll();
            imagePanel.add(imageLabel);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setFields(state);
        revalidate();
        repaint();
    }
    private void setFields(GetClothingItemState state) {
        nameInputField.setText(state.getClothingItem().getName());
        minTempInputField.setText(String.valueOf(state.getClothingItem().getMinimumAppropriateTemperature()));
        descriptonInputField.setText(state.getClothingItem().getDescription().orElse(null));


    }
}

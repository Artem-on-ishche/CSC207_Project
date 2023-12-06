package view;

import interface_adapter.get_clothing_item.GetItemState;
import interface_adapter.get_clothing_item.GetItemViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.update_clothing_item.UpdateController;
import interface_adapter.view_all_items.ViewAllItemsViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditItemView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "get item";
    private final JButton saveChanges;

    private final JButton back;

    final JTextField nameInputField = new JTextField(15);
    final JTextField minTempInputField = new JTextField(15);
    final JTextField descriptonInputField = new JTextField(15);


    GetItemViewModel getItemViewModel;
    public EditItemView(GetItemViewModel getItemViewModel, UpdateController updateController) {
        this.getItemViewModel = getItemViewModel;
        this.getItemViewModel.addPropertyChangeListener(this);

        JPanel buttons = new JPanel();
        saveChanges = new JButton(GetItemViewModel.SAVE_CHANGES_LABEL);
        buttons.add(saveChanges);

        back = new JButton(GetItemViewModel.BACK_LABEL);
        buttons.add(back);

        this.add(buttons);

        LabelTextPanel nameInfo = new LabelTextPanel(
                new JLabel("Name"), nameInputField);
        LabelTextPanel minTempInfo = new LabelTextPanel(
                new JLabel("Min Temperature"), minTempInputField);

        LabelTextPanel descriptionTempInfo = new LabelTextPanel(
                new JLabel("Min Temperature"), descriptonInputField);

        saveChanges.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(saveChanges)) {
                            GetItemState currentState = getItemViewModel.getState();

                            updateController.execute(
                                    currentState.getClothingItem()
                            );
                        }
                    }
                }
        );

        nameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                GetItemState currentState = getItemViewModel.getState();
                currentState.getClothingItem().setName(nameInputField.getText() + e.getKeyChar());
                getItemViewModel.setState(currentState);
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
                GetItemState currentState = getItemViewModel.getState();
                currentState.getClothingItem().setMinimumAppropriateTemperature(Integer.parseInt(minTempInputField.getText() + e.getKeyChar()));
                getItemViewModel.setState(currentState);
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
                GetItemState currentState = getItemViewModel.getState();
                currentState.getClothingItem().setDescription((descriptonInputField.getText() + e.getKeyChar()).describeConstable());
                getItemViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        this.add(nameInfo);
        this.add(minTempInfo);
        this.add(descriptionTempInfo);
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
        GetItemState state = (GetItemState) evt.getNewValue();
        setFields(state);
    }
    private void setFields(GetItemState state) {
      //  usernameInputField.setText(state.getClothingItem().getImage().getImageData());
        nameInputField.setText(state.getClothingItem().getName());
        minTempInputField.setText(String.valueOf(state.getClothingItem().getMinimumAppropriateTemperature()));
        descriptonInputField.setText(state.getClothingItem().getDescription().orElse(null));


    }
}

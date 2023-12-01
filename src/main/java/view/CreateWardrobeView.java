package view;

import interface_adapter.create_wardrobe.CreateWardrobeController;
import interface_adapter.create_wardrobe.CreateWardrobeState;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreateWardrobeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "add item";
    private final JButton imageInputField = new JButton("Upload Image");
    private final JTextField nameInputField = new JTextField(15);
    private final JTextField minTemp = new JTextField(15);

    private final CreateWardrobeController createWardrobeController;
    private final JButton addItem;
    private final JButton cancel;

    LabelTextPanel nameInfo = new LabelTextPanel(
            new JLabel(CreateWardrobeViewModel.NAME_LABEL), nameInputField);
    LabelTextPanel minTempInfo = new LabelTextPanel(
            new JLabel(CreateWardrobeViewModel.MIN_TEMP_LABEL), minTemp);



    public CreateWardrobeView(CreateWardrobeController createWardrobeController, CreateWardrobeViewModel createWardrobeViewModel) {

        this.createWardrobeController = createWardrobeController;

        createWardrobeViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(CreateWardrobeViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        addItem = new JButton(CreateWardrobeViewModel.ADD_BUTTON_LABEL);
        buttons.add(addItem);
        cancel = new JButton(CreateWardrobeViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        addItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(addItem)) {
                            CreateWardrobeState currentState = createWardrobeViewModel.getState();

                            CreateWardrobeView.this.createWardrobeController.execute(
                                    currentState.getName(),
                                    currentState.getPhoto(),
                                    currentState.getDescription(),
                                    currentState.getMinimumAppropriateTemperature()
                            );
                        }
                    }
                }
        );


        cancel.addActionListener(this);

        imageInputField.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame frame = new JFrame("Image Input Example");
                        JFileChooser fileChooser = new JFileChooser();
                        int result = fileChooser.showOpenDialog(frame);
                        CreateWardrobeState currentState = createWardrobeViewModel.getState();

                        if (result == JFileChooser.APPROVE_OPTION) {
                            String imagePath = fileChooser.getSelectedFile().getPath();
                          //  currentState.setPhoto();

                        }
                    }
                });

        nameInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        CreateWardrobeState currentState = createWardrobeViewModel.getState();
                        currentState.setName(nameInputField.getText() + e.getKeyChar());
                        createWardrobeViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        minTemp.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        CreateWardrobeState currentState = createWardrobeViewModel.getState();
                        currentState.setMinimumAppropriateTemperature(Double.parseDouble(minTemp.getText() + e.getKeyChar()));
                        createWardrobeViewModel.setState(currentState); // Hmm, is this necessary?
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(imageInputField);
        this.add(minTempInfo);
        this.add(nameInfo);
        this.add(buttons);
    }

    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}

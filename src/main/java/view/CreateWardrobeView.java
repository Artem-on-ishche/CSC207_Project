package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_wardrobe.CreateWardrobeController;
import interface_adapter.create_wardrobe.CreateWardrobeState;
import interface_adapter.create_wardrobe.CreateWardrobeViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.view_all_items.ViewAllItemsViewModel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;

public class CreateWardrobeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "add item";
    private final JButton imageInputField = new JButton("Upload Image");
    private final JTextField nameInputField = new JTextField(15);
    private final JTextField descriptionInputField = new JTextField(15);
    private final JTextField minTemp = new JTextField(15);

    private final CreateWardrobeController createWardrobeController;
    private final JButton addItem;
    private final JButton cancel;

    LabelTextPanel nameInfo = new LabelTextPanel(
            new JLabel(CreateWardrobeViewModel.NAME_LABEL), nameInputField);

    LabelTextPanel descriptionInfo = new LabelTextPanel(
            new JLabel(CreateWardrobeViewModel.DESCRIPTION_LABEL), descriptionInputField);
    LabelTextPanel minTempInfo = new LabelTextPanel(
            new JLabel(CreateWardrobeViewModel.MIN_TEMP_LABEL), minTemp);



    public CreateWardrobeView(CreateWardrobeController createWardrobeController, CreateWardrobeViewModel createWardrobeViewModel, LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel, ViewAllItemsViewModel viewAllItemsViewModel) {

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
                            LoggedInState loggedInState = loggedInViewModel.getState();

                            CreateWardrobeView.this.createWardrobeController.execute(
                                    loggedInState.getUsername(),
                                    currentState.getName(),
                                    currentState.getImageSrc(),
                                    currentState.getDescription(),
                                    currentState.getMinimumAppropriateTemperature()
                            );
                        }
                    }
                }
        );


        cancel.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(cancel)) {
                            viewManagerModel.setActiveView(viewAllItemsViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        imageInputField.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame frame = new JFrame("Image Input Example");
                        JFileChooser fileChooser = new JFileChooser();

                        FileFilter jpgFilter = new FileFilter() {
                            @Override
                            public boolean accept(java.io.File file) {
                                String fileName = file.getName().toLowerCase();
                                return file.isDirectory() || fileName.endsWith(".jpg");
                            }
                            @Override
                            public String getDescription() {
                                return "JPEG files (*.jpg)";
                            }
                        };

                        fileChooser.setFileFilter(jpgFilter);

                        int result = fileChooser.showOpenDialog(frame);
                        CreateWardrobeState currentState = createWardrobeViewModel.getState();

                        if (result == JFileChooser.APPROVE_OPTION) {
                            String imagePath = fileChooser.getSelectedFile().getPath();
                            currentState.setImageSrc(imagePath);

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

        descriptionInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        CreateWardrobeState currentState = createWardrobeViewModel.getState();

                        String userProvidedDescription = descriptionInputField.getText() + e.getKeyChar();

                        Optional<String> optionalDescription;

                        if (userProvidedDescription.isEmpty()) {
                            optionalDescription = Optional.empty();
                            System.out.println("dkfsekf");
                        } else {
                            optionalDescription = Optional.of(userProvidedDescription);
                            System.out.println(optionalDescription);
                        }

                        currentState.setDescription(optionalDescription);
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
                        char inputChar = e.getKeyChar();
                        if (!Character.isDigit(inputChar) && inputChar != '-') {
                            e.consume();
                            return;
                        }

                        String currentText = minTemp.getText();

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
                            CreateWardrobeState currentState = createWardrobeViewModel.getState();
                            currentState.setMinimumAppropriateTemperature(Integer.parseInt(newText.toString()));
                            createWardrobeViewModel.setState(currentState);
                        } catch (NumberFormatException ex) {
                        }
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
        this.add(descriptionInfo);
        this.add(buttons);
    }

    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}

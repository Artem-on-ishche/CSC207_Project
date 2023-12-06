package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "sign up";

    private final JTextField usernameInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(20);
    private final SignupController signupController;
    Font buttonFont = new Font("SansSerif", Font.PLAIN, 18);

    private final JButton signUp;
    private final JButton logIn;
    private final JButton cancel;


    public SignupView(SignupController signupController, SignupViewModel signupViewModel, ViewManagerModel viewManagerModel, LoginViewModel loginViewModel)  {

        this.signupController = signupController;

        signupViewModel.addPropertyChangeListener(this);

        this.setLayout(null);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int rectangleWidth = 500;
        int rectangleHeight = 400;

        int rectX = centerX - (rectangleWidth / 2);
        int rectY = centerY - (rectangleHeight / 2);

        int inputX = rectX + (rectangleWidth / 2) + 520;
        int inputY = rectY + rectangleHeight + 200;

        JLabel usernameLable = new JLabel(SignupViewModel.USERNAME_LABEL);
        usernameLable.setFont(new Font("SansSerif", Font.PLAIN, 18));
        LabelTextPanel usernameInfo = new LabelTextPanel(
                usernameLable, usernameInputField);

        usernameInfo.setBounds(inputX-33, inputY, 485, 40);
        usernameInfo.setBackground(Color.WHITE);
        this.add(usernameInfo);


        JLabel passwordLable = new JLabel(SignupViewModel.PASSWORD_LABEL);
        passwordLable.setFont(new Font("SansSerif", Font.PLAIN, 18));
        LabelTextPanel passwordInfo = new LabelTextPanel(
                passwordLable, passwordInputField);

        passwordInfo.setBounds(inputX-33, inputY + 50, 485, 40);
        passwordInfo.setBackground(Color.WHITE);
        this.add(passwordInfo);

        JLabel repeatLable = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);
        repeatLable.setFont(new Font("SansSerif", Font.PLAIN, 18));
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                repeatLable, repeatPasswordInputField);
        repeatPasswordInfo.setBounds(inputX-33, inputY + 100, 485, 40);
        repeatPasswordInfo.setBackground(Color.WHITE);
        this.add(repeatPasswordInfo);

        JPanel buttons = new JPanel();
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        signUp.setPreferredSize(new Dimension(150, 50));
        signUp.setFont(buttonFont);
        buttons.add(signUp);

        logIn = new JButton(SignupViewModel.LOGIN_BUTTON_LABEL);
        logIn.setPreferredSize(new Dimension(150, 50));
        logIn.setFont(buttonFont);
        buttons.add(logIn);

        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        cancel.setPreferredSize(new Dimension(150, 50));
        cancel.setFont(buttonFont);
        buttons.add(cancel);

        int buttonsX = rectX + (rectangleWidth / 2) + 470;
        int buttonsY = rectY + rectangleHeight + 410;
        buttons.setBounds(buttonsX, buttonsY, 510, 500);


        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            SignupState currentState = signupViewModel.getState();

                            SignupView.this.signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );
        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            // loginViewModel.firePropertyChanged();
                            viewManagerModel.setActiveView(loginViewModel.getViewName());
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );


        cancel.addActionListener(this);

        // This makes a new KeyListener implementing class, instantiates it, and
        // makes it listen to keystrokes in the usernameInputField.
        //
        // Notice how it has access to instance variables in the enclosing class!
        usernameInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        String text = usernameInputField.getText() + e.getKeyChar();
                        currentState.setUsername(text);
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

        passwordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        repeatPasswordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setRepeatPassword(repeatPasswordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState); // Hmm, is this necessary?
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );
        this.add(buttons);

    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(cancel)) {
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?", "Cancel Confirmation", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                usernameInputField.setText("");
                passwordInputField.setText("");
                repeatPasswordInputField.setText("");
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        var newState = evt.getNewValue();
        if (newState instanceof SignupState state) {
            if (state.getUsernameError() != null) {
                JOptionPane.showMessageDialog(this, state.getUsernameError());
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int rectangleWidth = 500;
        int rectangleHeight = 300;

        int rectX = centerX - (rectangleWidth / 2);
        int rectY = centerY - (rectangleHeight / 2) - 50;

        g.fillRect(rectX, rectY, rectangleWidth, rectangleHeight);
        g.setColor(Color.BLACK);
        g.drawRect(rectX, rectY, rectangleWidth, rectangleHeight);

        JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        Font titleFont = new Font("SansSerif", Font.BOLD, 22);
        title.setForeground(Color.BLUE);

        title.setFont(titleFont);
        title.setBounds(centerX - 33, rectY + 30, 150, 25);
        add(title);
    }

}
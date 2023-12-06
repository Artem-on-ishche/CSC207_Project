package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
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

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    final JTextField usernameInputField = new JTextField(18);
    private final JLabel usernameErrorField = new JLabel();

    final JPasswordField passwordInputField = new JPasswordField(18);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton cancel;
    private final LoginController loginController;

    public LoginView(LoginViewModel loginViewModel, LoginController controller) {

        this.loginController = controller;
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        this.setLayout(null);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int rectangleWidth = 500;
        int rectangleHeight = 400;

        int rectX = centerX - (rectangleWidth / 2);
        int rectY = centerY - (rectangleHeight / 2);

        int inputX = rectX + (rectangleWidth / 2) + 520;
        int inputY = rectY + rectangleHeight + 200;

        JLabel usernameLable = new JLabel("Username ");
        usernameLable.setFont(new Font("SansSerif", Font.PLAIN, 18));
        LabelTextPanel usernameInfo = new LabelTextPanel(
                usernameLable, usernameInputField);

        usernameInfo.setBounds(inputX-33, inputY, 485, 40);
        usernameInfo.setBackground(Color.WHITE);
        this.add(usernameInfo);

        JLabel passwordLable = new JLabel("Password ");
        passwordLable.setFont(new Font("SansSerif", Font.PLAIN, 18));
        LabelTextPanel passwordInfo = new LabelTextPanel(
                passwordLable, passwordInputField);

        passwordInfo.setBounds(inputX-33, inputY + 50, 485, 40);
        passwordInfo.setBackground(Color.WHITE);
        this.add(passwordInfo);

        JPanel buttons = new JPanel();
        logIn = new JButton(LoginViewModel.LOGIN_BUTTON_LABEL);
        buttons.add(logIn);
        cancel = new JButton(LoginViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        int buttonsX = rectX + (rectangleWidth / 2) + 470;
        int buttonsY = rectY + rectangleHeight + 450;
        buttons.setBounds(buttonsX, buttonsY, 510, 500);

        logIn.addActionListener(                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            LoginState currentState = loginViewModel.getState();

                                loginController.execute(
                                        currentState.getUsername(),
                                        currentState.getPassword()
                                );
                            if (currentState.getUsernameError() != null && !currentState.getUsernameError().isEmpty()) {
                                showErrorMessage("Username Error: " + currentState.getUsernameError());
                            }
                        }
                    }
                }
        );

        cancel.addActionListener(this);

        usernameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText() + e.getKeyChar());
                loginViewModel.setState(currentState);
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
                        LoginState currentState = loginViewModel.getState();
                        currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
                        loginViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
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

        JLabel title = new JLabel(LoginViewModel.TITLE_LABEL);
        Font titleFont = new Font("SansSerif", Font.BOLD, 22);
        title.setForeground(Color.BLUE);

        title.setFont(titleFont);
        title.setBounds(centerX - 33, rectY + 30, 150, 25);
        add(title);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(LoginView.this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }

}

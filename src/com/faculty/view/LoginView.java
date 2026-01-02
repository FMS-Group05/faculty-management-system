package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {

    // --- Colors & Fonts ---
    private final Color PRIMARY_COLOR = new Color(110, 56, 240); // Richer Purple
    private final Color HOVER_COLOR = new Color(90, 40, 210);
    private final Color BG_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(50, 50, 50);
    private final Color PLACEHOLDER_COLOR = new Color(150, 150, 150);
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);

    // --- Components ---
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    // Sign In Components
    private JTextField txtSignInUsername;
    private JPasswordField txtSignInPassword;
    private JButton btnSignIn;

    // Sign Up Components
    private JTextField txtSignUpUsername;
    private JPasswordField txtSignUpPassword;
    private JPasswordField txtSignUpConfirm;
    private JButton btnSignUp;

    // Roles
    private JRadioButton rbStudent, rbLecturer, rbAdmin;
    private ButtonGroup roleGroup;

    // Tabs
    private JLabel lblSignInTab, lblSignUpTab;

    public LoginView() {
        setTitle("Faculty Management System");
        setSize(1000, 650); // Slightly larger
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); // 50-50 Split

        add(createLeftPanel());
        add(createRightPanel());
    }

    // =========================================
    //            LEFT PANEL (BRANDING)
    // =========================================
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(138, 79, 255), 0, getHeight(), new Color(90, 20, 220));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);

        // Icon (Using a simple label, can be replaced with an Image)
        JLabel icon = new JLabel("FMS");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 80));
        icon.setForeground(new Color(255, 255, 255, 80)); // Semi-transparent
        panel.add(icon, gbc);

        gbc.gridy++;
        JLabel title = new JLabel("Welcome to Faculty Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title, gbc);

        gbc.gridy++;
        JLabel subtitle = new JLabel("Faculty of Computing & Technology");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(230, 230, 250));
        panel.add(subtitle, gbc);

        return panel;
    }

    // =========================================
    //            RIGHT PANEL (FORMS)
    // =========================================
    private JPanel createRightPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);

        // 1. Header (Tabs)
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 30));
        header.setBackground(BG_COLOR);
        
        lblSignInTab = createTab("Sign In", true);
        lblSignUpTab = createTab("Sign Up", false);
        
        header.add(lblSignInTab);
        header.add(lblSignUpTab);
        mainPanel.add(header, BorderLayout.NORTH);

        // 2. Cards (Forms)
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BG_COLOR);
        cardPanel.setBorder(new EmptyBorder(0, 50, 50, 50)); // Padding around form

        cardPanel.add(createSignInForm(), "signin");
        cardPanel.add(createSignUpForm(), "signup");

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JLabel createTab(String text, boolean isActive) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(isActive ? PRIMARY_COLOR : PLACEHOLDER_COLOR);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Underline effect for active tab
        if(isActive) {
            label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR));
        }

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean isSignIn = text.equals("Sign In");
                cardLayout.show(cardPanel, isSignIn ? "signin" : "signup");
                
                // Update styles
                lblSignInTab.setForeground(isSignIn ? PRIMARY_COLOR : PLACEHOLDER_COLOR);
                lblSignInTab.setBorder(isSignIn ? BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR) : null);
                
                lblSignUpTab.setForeground(!isSignIn ? PRIMARY_COLOR : PLACEHOLDER_COLOR);
                lblSignUpTab.setBorder(!isSignIn ? BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR) : null);
            }
        });
        return label;
    }

    // =========================================
    //               FORMS
    // =========================================
    
    private JPanel createSignInForm() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_COLOR);

        p.add(Box.createVerticalStrut(40)); // Top spacing
        p.add(createLabel("Username"));
        txtSignInUsername = new ModernTextField();
        p.add(txtSignInUsername);
        
        p.add(Box.createVerticalStrut(20));
        p.add(createLabel("Password"));
        txtSignInPassword = new ModernPasswordField();
        p.add(txtSignInPassword);

        p.add(Box.createVerticalStrut(40));
        btnSignIn = new ModernButton("Login to Dashboard");
        p.add(btnSignIn);

        return p;
    }

    private JPanel createSignUpForm() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_COLOR);

        p.add(Box.createVerticalStrut(10));
        p.add(createLabel("Username"));
        txtSignUpUsername = new ModernTextField();
        p.add(txtSignUpUsername);

        p.add(Box.createVerticalStrut(15));
        p.add(createLabel("Password"));
        txtSignUpPassword = new ModernPasswordField();
        p.add(txtSignUpPassword);

        p.add(Box.createVerticalStrut(15));
        p.add(createLabel("Confirm Password"));
        txtSignUpConfirm = new ModernPasswordField();
        p.add(txtSignUpConfirm);

        p.add(Box.createVerticalStrut(15));
        p.add(createLabel("Select Role"));
        p.add(createRolePanel());

        p.add(Box.createVerticalStrut(30));
        btnSignUp = new ModernButton("Create Account");
        p.add(btnSignUp);

        return p;
    }

    private JPanel createRolePanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        p.setBackground(BG_COLOR);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        roleGroup = new ButtonGroup();
        rbStudent = createRadio("Student");
        rbLecturer = createRadio("Lecturer");
        rbAdmin = createRadio("Admin");

        rbStudent.setSelected(true); // Default

        p.add(rbStudent);
        p.add(rbLecturer);
        p.add(rbAdmin);
        return p;
    }

    private JRadioButton createRadio(String text) {
        JRadioButton rb = new JRadioButton(text);
        rb.setBackground(BG_COLOR);
        rb.setFont(MAIN_FONT);
        rb.setFocusPainted(false);
        roleGroup.add(rb);
        return rb;
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(TEXT_COLOR);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setBorder(new EmptyBorder(0, 0, 5, 0));
        return l;
    }

    // =========================================
    //        CUSTOM MODERN COMPONENTS
    // =========================================

    // Custom Button with rounded corners and hover effect
    class ModernButton extends JButton {
        public ModernButton(String text) {
            super(text);
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { setBackground(HOVER_COLOR); repaint(); }
                public void mouseExited(MouseEvent e) { setBackground(PRIMARY_COLOR); repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) {
                g2.setColor(HOVER_COLOR.darker());
            } else if (getModel().isRollover()) {
                g2.setColor(HOVER_COLOR);
            } else {
                g2.setColor(PRIMARY_COLOR);
            }
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 2;
            g2.drawString(getText(), x, y);
            g2.dispose();
        }
    }

    // Custom Text Field with padding and nice border
    class ModernTextField extends JTextField {
        public ModernTextField() {
            setFont(MAIN_FONT);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            // Padding: Top, Left, Bottom, Right
            setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10), 
                new EmptyBorder(5, 10, 5, 10) 
            ));
        }
    }

    // Custom Password Field
    class ModernPasswordField extends JPasswordField {
        public ModernPasswordField() {
            setFont(MAIN_FONT);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10), 
                new EmptyBorder(5, 10, 5, 10) 
            ));
        }
    }

    // Rounded Border Logic
    class RoundedBorder extends EmptyBorder {
        private int radius;
        RoundedBorder(int radius) { super(0,0,0,0); this.radius = radius; }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(200, 200, 200)); // Border color
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }

    // =========================================
    //                GETTERS
    // =========================================
    public JButton getSignInButton() { return btnSignIn; }
    public JButton getSignUpButton() { return btnSignUp; }
    public String getSignInUsername() { return txtSignInUsername.getText().trim(); }
    public String getSignInPassword() { return new String(txtSignInPassword.getPassword()).trim(); }
    public String getSignUpUsername() { return txtSignUpUsername.getText().trim(); }
    public String getSignUpPassword() { return new String(txtSignUpPassword.getPassword()).trim(); }
    public String getSignUpConfirm() { return new String(txtSignUpConfirm.getPassword()).trim(); }

    public String getSelectedRole() {
        if (rbAdmin.isSelected()) return "Admin";
        if (rbLecturer.isSelected()) return "Lecturer";
        return "Student";
    }

   // -------------------- Clear Fields --------------------
    public void clearSignUpFields() {
        if (txtSignUpUsername != null) txtSignUpUsername.setText("");
        if (txtSignUpPassword != null) txtSignUpPassword.setText("");
        if (txtSignUpConfirm != null) txtSignUpConfirm.setText("");
    }

    public void clearSignInFields() {
        if (txtSignInUsername != null) txtSignInUsername.setText("");
        if (txtSignInPassword != null) txtSignInPassword.setText("");
    }
}
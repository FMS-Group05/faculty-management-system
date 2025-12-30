package com.faculty.view;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {

    private final Color PRIMARY = new Color(132, 84, 255);
    private final Color LIGHT_GRAY = new Color(230, 230, 230);
    private final Color TEXT_GRAY = new Color(150, 150, 150);

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirm;

    private JButton btnSignIn;
    private JButton btnSignUp;

    private JToggleButton btnAdmin, btnStudent, btnLecturer;
    private JLabel lblSignInTab, lblSignUpTab;

    public LoginView() {
        setTitle("Faculty Management System");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(false);
        setLayout(new BorderLayout());

        add(leftPanel(), BorderLayout.WEST);
        add(rightPanel(), BorderLayout.CENTER);
    }

    private JPanel leftPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
        };
        panel.setPreferredSize(new Dimension(450, 550));
        panel.setBackground(PRIMARY);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel icon = new JLabel("🎓");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        icon.setForeground(Color.WHITE);

        JLabel title = new JLabel("Faculty Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel faculty = new JLabel("Faculty of Computing & Technology");
        faculty.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        faculty.setForeground(new Color(240, 240, 240));

        JLabel subtitle = new JLabel("Manage your academic journey");
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitle.setForeground(new Color(220, 220, 220));

        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(icon, gbc);
        gbc.gridy = 1;
        panel.add(title, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(40, 0, 5, 0);
        panel.add(faculty, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(subtitle, gbc);

        return panel;
    }

    private JPanel rightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Header with Tabs
        JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
        header.setBackground(Color.WHITE);
        lblSignInTab = createTabLabel("Sign In", true);
        lblSignUpTab = createTabLabel("Sign Up", false);
        header.add(lblSignInTab);
        header.add(lblSignUpTab);
        panel.add(header, BorderLayout.NORTH);

        // Center Card Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);

        cardPanel.add(createFormWrapper(signInForm()), "signin");
        cardPanel.add(createFormWrapper(signUpForm()), "signup");

        panel.add(cardPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFormWrapper(JPanel form) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(form);
        return wrapper;
    }

    private JPanel signInForm() {
        JPanel p = baseForm();
        btnSignIn = createRoundedButton("Sign In");
        p.add(Box.createRigidArea(new Dimension(0, 30)));
        p.add(btnSignIn);
        return p;
    }

    private JPanel signUpForm() {
        JPanel p = baseForm();
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createInputLabel("Confirm Password"));
        txtConfirm = createPasswordField();
        p.add(txtConfirm);
        p.add(Box.createRigidArea(new Dimension(0, 30)));
        btnSignUp = createRoundedButton("Sign Up");
        p.add(btnSignUp);
        return p;
    }

    private JPanel baseForm() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setPreferredSize(new Dimension(350, 400));

        p.add(createInputLabel("Username"));
        txtUsername = createTextField();
        p.add(txtUsername);

        p.add(Box.createRigidArea(new Dimension(0, 15)));
        p.add(createInputLabel("Password"));
        txtPassword = createPasswordField();
        p.add(txtPassword);

        p.add(Box.createRigidArea(new Dimension(0, 15)));
        p.add(createInputLabel("Role"));
        p.add(roleSelectorPanel());

        return p;
    }

    private JPanel roleSelectorPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        p.setBackground(Color.WHITE);
        p.setMaximumSize(new Dimension(350, 50));

        btnAdmin = createRoleBtn("Admin", true);
        btnStudent = createRoleBtn("Student", false);
        btnLecturer = createRoleBtn("Lecturer", false);

        ButtonGroup group = new ButtonGroup();
        group.add(btnAdmin); group.add(btnStudent); group.add(btnLecturer);

        p.add(btnAdmin); p.add(btnStudent); p.add(btnLecturer);
        return p;
    }

    // --- Styling Helpers ---

    private JLabel createTabLabel(String text, boolean active) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(active ? PRIMARY : TEXT_GRAY);
        lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl.setBorder(active ? BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY) : null);

        lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                switchTab(text.toLowerCase().replace(" ", ""));
            }
        });
        return lbl;
    }

    private void switchTab(String name) {
        cardLayout.show(cardPanel, name);
        boolean isSignIn = name.equals("signin");
        lblSignInTab.setForeground(isSignIn ? PRIMARY : TEXT_GRAY);
        lblSignInTab.setBorder(isSignIn ? BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY) : null);
        lblSignUpTab.setForeground(!isSignIn ? PRIMARY : TEXT_GRAY);
        lblSignUpTab.setBorder(!isSignIn ? BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY) : null);
    }

    private JLabel createInputLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(PRIMARY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField createTextField() {
        JTextField f = new JTextField();
        styleField(f);
        return f;
    }

    private JPasswordField createPasswordField() {
        JPasswordField f = new JPasswordField();
        styleField(f);
        return f;
    }

    private void styleField(JTextField f) {
        f.setMaximumSize(new Dimension(350, 40));
        f.setPreferredSize(new Dimension(350, 40));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setBorder(new RoundedBorder(PRIMARY, 15));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton createRoundedButton(String text) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBackground(PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setMaximumSize(new Dimension(350, 45));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JToggleButton createRoleBtn(String text, boolean selected) {
        JToggleButton b = new JToggleButton(text, selected);
        b.setFocusPainted(false);
        b.setBackground(selected ? PRIMARY : LIGHT_GRAY);
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        b.addActionListener(e -> {
            btnAdmin.setBackground(btnAdmin.isSelected() ? PRIMARY : LIGHT_GRAY);
            btnStudent.setBackground(btnStudent.isSelected() ? PRIMARY : LIGHT_GRAY);
            btnLecturer.setBackground(btnLecturer.isSelected() ? PRIMARY : LIGHT_GRAY);
        });
        return b;
    }

    static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int radius;
        RoundedBorder(Color c, int r) { color = c; radius = r; }
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
        }
    }

    // Getters for Controller
    public JButton getSignInButton() { return btnSignIn; }
    public JButton getSignUpButton() { return btnSignUp; }
    public String getLoginUsername() { return txtUsername.getText(); }
    public String getLoginPassword() { return new String(txtPassword.getPassword()); }
    public String getConfirmPassword() { return txtConfirm != null ? new String(txtConfirm.getPassword()) : ""; }
    public String getSelectedRole() {
        if (btnAdmin.isSelected()) return "Admin";
        if (btnLecturer.isSelected()) return "Lecturer";
        return "Student";
    }
}

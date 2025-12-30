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
        setLayout(new BorderLayout());

        add(leftPanel(), BorderLayout.WEST);
        add(rightPanel(), BorderLayout.CENTER);
    }

    // ================= LEFT PANEL =================
    private JPanel leftPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(450, 550));
        panel.setBackground(PRIMARY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel icon = new JLabel("🎓");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        icon.setForeground(Color.WHITE);

        JLabel title = new JLabel("Faculty Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel faculty = new JLabel("Faculty of Computing & Technology");
        faculty.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        faculty.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Manage your academic journey");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);

        gbc.gridy = 0; panel.add(icon, gbc);
        gbc.gridy = 1; panel.add(title, gbc);
        gbc.gridy = 2; panel.add(faculty, gbc);
        gbc.gridy = 3; panel.add(subtitle, gbc);

        return panel;
    }

    // ================= RIGHT PANEL =================
    private JPanel rightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        panel.add(tabHeader(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);

        cardPanel.add(signInForm(), "signin");
        cardPanel.add(signUpForm(), "signup");

        panel.add(cardPanel, BorderLayout.CENTER);
        return panel;
    }

    // ================= TABS =================
    private JPanel tabHeader() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 25));
        panel.setBackground(Color.WHITE);

        lblSignInTab = tabLabel("Sign In", true);
        lblSignUpTab = tabLabel("Sign Up", false);

        panel.add(lblSignInTab);
        panel.add(lblSignUpTab);
        return panel;
    }

    private JLabel tabLabel(String text, boolean active) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(active ? PRIMARY : TEXT_GRAY);
        lbl.setBorder(active ? BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY) : null);
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                switchTab(text.equals("Sign In"));
            }
        });
        return lbl;
    }

    private void switchTab(boolean signIn) {
        cardLayout.show(cardPanel, signIn ? "signin" : "signup");

        lblSignInTab.setForeground(signIn ? PRIMARY : TEXT_GRAY);
        lblSignInTab.setBorder(signIn ? BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY) : null);

        lblSignUpTab.setForeground(!signIn ? PRIMARY : TEXT_GRAY);
        lblSignUpTab.setBorder(!signIn ? BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY) : null);
    }

    // ================= FORMS =================
    private JPanel signInForm() {
        JPanel p = baseForm();
        btnSignIn = roundedButton("Sign In");
        p.add(Box.createVerticalStrut(30));
        p.add(btnSignIn);
        return p;
    }

    private JPanel signUpForm() {
        JPanel p = baseForm();

        p.add(Box.createVerticalStrut(15));
        p.add(inputLabel("Confirm Password"));
        txtConfirm = passwordField();
        p.add(txtConfirm);

        p.add(Box.createVerticalStrut(30));
        btnSignUp = roundedButton("Sign Up");
        p.add(btnSignUp);

        return p;
    }

    private JPanel baseForm() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 40)); // <<< LEFT ALIGN FIX

        p.add(inputLabel("Username"));
        txtUsername = textField();
        p.add(txtUsername);

        p.add(Box.createVerticalStrut(15));
        p.add(inputLabel("Password"));
        txtPassword = passwordField();
        p.add(txtPassword);

        p.add(Box.createVerticalStrut(15));
        p.add(inputLabel("Role"));
        p.add(rolePanel());

        return p;
    }

    // ================= ROLE =================
    private JPanel rolePanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        p.setBackground(Color.WHITE);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnAdmin = roleButton("Admin", true);   // highlighted by default
        btnStudent = roleButton("Student", false);
        btnLecturer = roleButton("Lecturer", false);

        ButtonGroup g = new ButtonGroup();
        g.add(btnAdmin);
        g.add(btnStudent);
        g.add(btnLecturer);

        p.add(btnAdmin);
        p.add(btnStudent);
        p.add(btnLecturer);
        return p;
    }

    // ================= COMPONENTS =================
    private JLabel inputLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(PRIMARY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField textField() {
        JTextField f = new JTextField();
        styleField(f);
        return f;
    }

    private JPasswordField passwordField() {
        JPasswordField f = new JPasswordField();
        styleField(f);
        return f;
    }

    private void styleField(JTextField f) {
        f.setMaximumSize(new Dimension(320, 38));
        f.setPreferredSize(new Dimension(320, 38));
        f.setBorder(new RoundedBorder(PRIMARY, 15));
        f.setCaretColor(PRIMARY);
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton roundedButton(String text) {
        JButton b = new JButton(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(text,
                        (getWidth() - fm.stringWidth(text)) / 2,
                        (getHeight() + fm.getAscent()) / 2 - 3);
            }
        };
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setMaximumSize(new Dimension(320, 45));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        return b;
    }

    private JToggleButton roleButton(String text, boolean selected) {
        JToggleButton b = new JToggleButton(text, selected);
        b.setFocusPainted(false);
        b.setBackground(selected ? PRIMARY : LIGHT_GRAY);
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        b.addActionListener(e -> {
            btnAdmin.setBackground(btnAdmin.isSelected() ? PRIMARY : LIGHT_GRAY);
            btnStudent.setBackground(btnStudent.isSelected() ? PRIMARY : LIGHT_GRAY);
            btnLecturer.setBackground(btnLecturer.isSelected() ? PRIMARY : LIGHT_GRAY);
        });
        return b;
    }

    static class RoundedBorder extends AbstractBorder {
        Color c; int r;
        RoundedBorder(Color c, int r) { this.c = c; this.r = r; }
        public void paintBorder(Component comp, Graphics g, int x, int y, int w, int h) {
            g.setColor(c);
            g.drawRoundRect(x, y, w - 1, h - 1, r, r);
        }
    }

    // ================= GETTERS =================
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

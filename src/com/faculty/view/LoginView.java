package com.faculty.view;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {

    private final Color PRIMARY = new Color(132, 84, 255);
    private final Color LIGHT_GRAY = new Color(220, 220, 220);
    private final Color TEXT_GRAY = new Color(160, 160, 160);

    private CardLayout cardLayout;
    private JPanel cardPanel;

    // SIGN IN
    private JTextField txtSignInUsername;
    private JPasswordField txtSignInPassword;
    private JButton btnSignIn;

    // SIGN UP
    private JTextField txtSignUpUsername;
    private JPasswordField txtSignUpPassword;
    private JPasswordField txtSignUpConfirm;
    private JButton btnSignUp;

    // Role buttons
    private JToggleButton btnAdmin, btnStudent, btnLecturer;

    private JLabel lblSignInTab, lblSignUpTab;

    public LoginView() {
        setTitle("Faculty Management System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(leftPanel(), BorderLayout.WEST);
        add(rightPanel(), BorderLayout.CENTER);
    }

    private JPanel leftPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(450, 600));
        panel.setBackground(PRIMARY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel icon = new JLabel("🎓");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
        icon.setForeground(Color.WHITE);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(icon, gbc);

        JLabel title = new JLabel("Faculty Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        panel.add(title, gbc);

        JLabel faculty = new JLabel("Faculty of Computing & Technology");
        faculty.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        faculty.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.insets = new Insets(80, 0, 5, 0);
        panel.add(faculty, gbc);

        JLabel subtitle = new JLabel("Manage your academic journey");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(subtitle, gbc);

        return panel;
    }

    private JPanel rightPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 25));
        header.setBackground(Color.WHITE);

        lblSignInTab = tab("Sign In", true);
        lblSignUpTab = tab("Sign Up", false);
        header.add(lblSignInTab);
        header.add(lblSignUpTab);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(signInForm(), "signin");
        cardPanel.add(signUpForm(), "signup");

        p.add(header, BorderLayout.NORTH);
        p.add(cardPanel, BorderLayout.CENTER);
        return p;
    }

    private JLabel tab(String text, boolean active) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 20));
        l.setForeground(active ? PRIMARY : TEXT_GRAY);
        l.setBorder(active ? BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY) : null);
        l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        l.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                boolean signIn = text.equals("Sign In");
                cardLayout.show(cardPanel, signIn ? "signin" : "signup");
                lblSignInTab.setForeground(signIn ? PRIMARY : TEXT_GRAY);
                lblSignInTab.setBorder(signIn ? BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY) : null);
                lblSignUpTab.setForeground(!signIn ? PRIMARY : TEXT_GRAY);
                lblSignUpTab.setBorder(!signIn ? BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY) : null);
            }
        });
        return l;
    }

    private JPanel signInForm() {
        JPanel p = baseForm(false);
        p.add(Box.createVerticalStrut(30));
        btnSignIn = roundedButton("Sign In");
        p.add(btnSignIn);
        return p;
    }

    private JPanel signUpForm() {
        JPanel p = baseForm(true);
        p.add(Box.createVerticalStrut(30));
        btnSignUp = roundedButton("Sign Up");
        p.add(btnSignUp);
        return p;
    }

    private JPanel baseForm(boolean signup) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 40));

        p.add(label("Username"));
        if (signup) {
            txtSignUpUsername = field();
            p.add(txtSignUpUsername);
        } else {
            txtSignInUsername = field();
            p.add(txtSignInUsername);
        }

        p.add(Box.createVerticalStrut(15));
        p.add(label("Password"));
        if (signup) {
            txtSignUpPassword = password();
            p.add(txtSignUpPassword);

            p.add(Box.createVerticalStrut(15));
            p.add(label("Confirm Password"));
            txtSignUpConfirm = password();
            p.add(txtSignUpConfirm);
        } else {
            txtSignInPassword = password();
            p.add(txtSignInPassword);
        }

        p.add(Box.createVerticalStrut(15));
        p.add(label("Role"));
        p.add(rolePanel());

        return p;
    }

    private JPanel rolePanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        p.setBackground(Color.WHITE);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnAdmin = role("Admin", true);
        btnStudent = role("Student", false);
        btnLecturer = role("Lecturer", false);

        ButtonGroup g = new ButtonGroup();
        g.add(btnAdmin); g.add(btnStudent); g.add(btnLecturer);

        p.add(btnAdmin); p.add(btnStudent); p.add(btnLecturer);
        return p;
    }

    private JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(PRIMARY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField field() {
        JTextField f = new JTextField();
        f.setMaximumSize(new Dimension(380, 40));
        f.setBorder(new RoundedBorder(PRIMARY, 2, 15));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        return f;
    }

    private JPasswordField password() {
        JPasswordField f = new JPasswordField();
        f.setMaximumSize(new Dimension(380, 40));
        f.setBorder(new RoundedBorder(PRIMARY, 2, 15));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        return f;
    }

    private JButton roundedButton(String t) {
        JButton b = new JButton(t) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(t, (getWidth() - fm.stringWidth(t)) / 2, (getHeight() + fm.getAscent()) / 2 - 4);
            }
        };
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setMaximumSize(new Dimension(380, 45));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        return b;
    }

    private JToggleButton role(String t, boolean sel) {
        JToggleButton b = new JToggleButton(t, sel);
        b.setBackground(sel ? PRIMARY : LIGHT_GRAY);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        b.addActionListener(e -> {
            btnAdmin.setBackground(btnAdmin.isSelected() ? PRIMARY : LIGHT_GRAY);
            btnStudent.setBackground(btnStudent.isSelected() ? PRIMARY : LIGHT_GRAY);
            btnLecturer.setBackground(btnLecturer.isSelected() ? PRIMARY : LIGHT_GRAY);
        });
        return b;
    }

    static class RoundedBorder extends AbstractBorder {
        Color c; int t, r;
        RoundedBorder(Color c, int t, int r) { this.c = c; this.t = t; this.r = r; }
        public void paintBorder(Component comp, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(t));
            g2.drawRoundRect(x, y, w - 1, h - 1, r, r);
        }
    }

    // -------------------- Getters --------------------
    public JButton getSignInButton() { return btnSignIn; }
    public JButton getSignUpButton() { return btnSignUp; }

    public String getSignInUsername() { return txtSignInUsername.getText().trim(); }
    public String getSignInPassword() { return new String(txtSignInPassword.getPassword()).trim(); }

    public String getSignUpUsername() { return txtSignUpUsername.getText().trim(); }
    public String getSignUpPassword() { return new String(txtSignUpPassword.getPassword()).trim(); }
    public String getSignUpConfirm() { return new String(txtSignUpConfirm.getPassword()).trim(); }

    public String getSelectedRole() {
        if (btnAdmin.isSelected()) return "Admin";
        if (btnLecturer.isSelected()) return "Lecturer";
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

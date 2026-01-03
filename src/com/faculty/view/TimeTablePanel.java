package com.faculty.view;

import com.faculty.model.User;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTablePanel extends JPanel {

    private final Color PURPLE = new Color(132, 84, 255);
    private JLabel imageLabel;
    private JComboBox<Integer> yearCombo;
    private JLabel lblStatus;
    private JLabel lblDateTime; // Label for date and time

    public TimeTablePanel(User user) {
        setBackground(new Color(245, 245, 250));
        setLayout(new BorderLayout());

        // Top Panel: Title + Date/Time + Year Selector
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 250));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left: Title only
        JLabel title = new JLabel("My Timetable");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(PURPLE);
        topPanel.add(title, BorderLayout.WEST);

        // Right Panel: Date/Time on top, Year selector at bottom
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(new Color(245, 245, 250));

        // Current Date & Time at top-right (bold and larger font)
        lblDateTime = new JLabel();
        lblDateTime.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Bold and larger
        lblDateTime.setForeground(PURPLE); // Theme color
        lblDateTime.setHorizontalAlignment(SwingConstants.RIGHT);
        updateDateTime();

        Timer timer = new Timer(1000, e -> updateDateTime());
        timer.start();

        rightPanel.add(lblDateTime, BorderLayout.NORTH);

        // Year selector at bottom-right (more aligned to corner)
        JPanel yearPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        yearPanel.setBackground(new Color(245, 245, 250));

        JLabel lblYear = new JLabel("Select Year:");
        lblYear.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblYear.setForeground(new Color(132, 84, 255));

        yearCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        yearCombo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        yearCombo.setPreferredSize(new Dimension(80, 30));

        yearPanel.add(lblYear);
        yearPanel.add(Box.createHorizontalStrut(5)); // Small gap
        yearPanel.add(yearCombo);

        rightPanel.add(yearPanel, BorderLayout.SOUTH);

        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel: Scrollable Image Area
        imageLabel = new JLabel("Select a year to view timetable", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getIcon() != null && getIcon() instanceof ImageIcon) {
                    if (isOpaque()) {
                        g.setColor(getBackground());
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }

                    Image img = ((ImageIcon) getIcon()).getImage();
                    int panelW = getWidth();
                    int panelH = getHeight();
                    int imgW = img.getWidth(null);
                    int imgH = img.getHeight(null);

                    if (imgW > 0 && imgH > 0) {
                        double scale = Math.min((double) panelW / imgW, (double) panelH / imgH);
                        int scaledW = (int) (imgW * scale);
                        int scaledH = (int) (imgH * scale);
                        int x = (panelW - scaledW) / 2;
                        int y = (panelH - scaledH) / 2;

                        Graphics2D g2 = (Graphics2D) g;
                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g2.drawImage(img, x, y, scaledW, scaledH, null);
                    }
                } else {
                    super.paintComponent(g);
                }
            }
        };
        imageLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        imageLabel.setForeground(Color.GRAY);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(imageLabel, BorderLayout.CENTER);

        // Bottom Panel: Status/Info
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(Color.WHITE);
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        bottomPanel.add(lblStatus);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM yyyy HH:mm:ss");
        lblDateTime.setText(sdf.format(new Date()));
    }

    public JComboBox<Integer> getYearComboBox() {
        return yearCombo;
    }

    public void setImage(ImageIcon icon) {
        if (icon != null) {
            imageLabel.setText("");
            imageLabel.setIcon(icon);
        } else {
            imageLabel.setIcon(null);
            imageLabel.setText("No timetable image available for this year.");
        }
    }

    public void setStatus(String text) {
        lblStatus.setText(text);
    }
}

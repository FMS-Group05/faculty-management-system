package com.faculty.view;

import com.faculty.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTablePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTime;

    public TimeTablePanel(User user) {
        setBackground(new Color(245, 245, 250)); // light background
        setLayout(new BorderLayout());

        Color themeColor = new Color(132, 84, 255);
        Color lightRowColor = new Color(250, 250, 255);
        Color darkRowColor = new Color(240, 240, 250);

        // ---------- Top Panel with Title and Current Time ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 250));

        // Title label
        JLabel lblTitle = new JLabel("Student Timetable", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(themeColor);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));
        topPanel.add(lblTitle, BorderLayout.WEST);

        // Current time label
        lblTime = new JLabel();
        lblTime.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTime.setForeground(themeColor);
        lblTime.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
        topPanel.add(lblTime, BorderLayout.EAST);

        // Start the timer to update time
        startClock();

        add(topPanel, BorderLayout.NORTH);

        // ---------- Table Columns ----------
        String[] columns = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        // ---------- Table Data ----------
        String[][] data = {
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""},
                {"INTERVAL", "", "", "", "", ""},
                {"", "", "", "", "", ""},
                {"", "", "", "", "", ""}
        };

        // ---------- Table Model ----------
        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only for students
            }
        };

        // ---------- JTable ----------
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (row == 2) { // INTERVAL row
                    c.setBackground(themeColor);
                    c.setForeground(Color.WHITE);
                    c.setFont(new Font("Segoe UI", Font.BOLD, 20));
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    if (!isRowSelected(row)) {
                        c.setBackground(row % 2 == 0 ? lightRowColor : darkRowColor);
                        c.setForeground(themeColor);
                        c.setFont(new Font("Segoe UI", Font.PLAIN, 17));
                    } else {
                        c.setBackground(themeColor.darker());
                        c.setForeground(Color.WHITE);
                    }
                }
                return c;
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw INTERVAL text across all columns
                int intervalRow = 2;
                Rectangle rect = getCellRect(intervalRow, 0, true);
                for (int i = 1; i < getColumnCount(); i++) {
                    rect = rect.union(getCellRect(intervalRow, i, true));
                }

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(themeColor);
                g2.fillRect(rect.x, rect.y, rect.width, rect.height);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                String spacedText = String.join(" ", "INTERVAL".split(""));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(spacedText);
                int textHeight = fm.getAscent();
                int textX = rect.x + (rect.width - textWidth) / 2;
                int textY = rect.y + (rect.height + textHeight) / 2 - 4;
                g2.drawString(spacedText, textX, textY);
                g2.dispose();
            }
        };

        table.setRowHeight(60);
        table.setShowGrid(true);
        table.setGridColor(themeColor);

        // ---------- Header ----------
        JTableHeader header = table.getTableHeader();
        header.setBackground(themeColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // ---------- Center all cells ----------
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // ---------- Scroll Pane ----------
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        scrollPane.getViewport().setBackground(new Color(245, 245, 250));
        add(scrollPane, BorderLayout.CENTER);
    }

    // ---------- Clock Timer ----------
    private void startClock() {
        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd MMM yyyy");
            lblTime.setText(sdf.format(new Date()));
        });
        timer.start();
    }
}

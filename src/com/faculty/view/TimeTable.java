package com.faculty.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.JTableHeader;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class TimeTable extends JPanel {
    private JTable table;
    private final Color PURPLE = new Color(132, 84, 255);
    private JButton addBtn, editBtn, deleteBtn, saveBtn;

    public TimeTable() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title Section
        JLabel title = new JLabel("Time Table", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(PURPLE);
        title.setBorder(new EmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(0, 30, 0, 30));

        // Action Buttons
        JPanel topActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        topActions.setBackground(Color.WHITE);
        addBtn = actionButton("Add new", PURPLE);
        editBtn = actionButton("Edit", new Color(180, 180, 180));
        deleteBtn = actionButton("Delete", new Color(180, 180, 180));
        topActions.add(addBtn);
        topActions.add(editBtn);
        topActions.add(deleteBtn);
        content.add(topActions, BorderLayout.NORTH);

        // Table Model with Sample Rows to Check Scrolling
        String[] columns = {"Degree", "Year", "Image"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);


        for (int i = 1; i <= 15; i++) {
            model.addRow(new Object[]{"Degree " + i, i, "Upload files"});
        }

        table = new JTable(model);
        table.setRowHeight(45);
        table.setGridColor(PURPLE);
        table.setShowGrid(true);

        // Header Formatting
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setForeground(PURPLE);
        header.setBackground(Color.WHITE);

        // Custom Renderer and Editor for the Image Column
        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));

        // ✅ ScrollPane with Persistent Vertical Scrollbar
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(PURPLE, 2));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // සැමවිටම scroll bar එක පෙන්වීමට
        scroll.getVerticalScrollBar().setUnitIncrement(16); // Scroll කිරීම සුමට කිරීමට

        content.add(scroll, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        // Save Button at bottom
        saveBtn = new JButton("Save changes");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        saveBtn.setBackground(PURPLE);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setPreferredSize(new Dimension(400, 50));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottom.setBackground(Color.WHITE);
        bottom.add(saveBtn);
        add(bottom, BorderLayout.SOUTH);
    }


    public void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Time Table Image");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            int row = table.getSelectedRow();
            if (row != -1) {
                table.setValueAt(file.getName(), row, 2);
            }
        }
    }

    // --- Button Renderer ---
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null || value.toString().isEmpty()) ? "Upload files" : value.toString());
            setBackground(Color.WHITE);
            setForeground(PURPLE);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            return this;
        }
    }

    // --- Button Editor (Click Access) ---
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Upload files");
            button.addActionListener(e -> {
                fireEditingStopped();
                openFileChooser(); // පරිගණකයේ ෆයිල් මැෂින් එක විවෘත වේ
            });
        }
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }
    }

    private JButton actionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBorder(new LineBorder(bg, 1, true));
        return btn;
    }

    public JButton getAddButton() { return addBtn; }
    public JButton getSaveButton() { return saveBtn; }
    public JTable getTable() { return table; }
}
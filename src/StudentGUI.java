import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class StudentGUI extends JFrame {
    private final StudentManager manager = new StudentManager();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Roll No", "ERP ID", "Name", "Branch", "Course", "Marks", "Grade"}, 0
    );
    private final JTable table = new JTable(model);
    private final JTextField rollField = new JTextField(8);
    private final JTextField erpField = new JTextField(10);
    private final JTextField nameField = new JTextField(12);
    private final JTextField branchField = new JTextField(10);
    private final JTextField courseField = new JTextField(10);
    private final JTextField marksField = new JTextField(6);
    private final JTextField searchField = new JTextField(15);
    private final JLabel statusLabel = new JLabel("Ready.");
    private JPanel dimOverlay;

    public StudentGUI() {
        setTitle("Student Management System");
        setSize(1080, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);
        initMenuBar();
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createBodyPanel(), BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
        addDemoStudents();
        refresh();
        setVisible(true);
    }

    private void addDemoStudents() {
        manager.addStudent(new Student(1, "ERP001", "Rahul Sharma", "CSE", "B.Tech", 89));
        manager.addStudent(new Student(2, "ERP002", "Priya Singh", "IT", "B.Tech", 76));
        manager.addStudent(new Student(3, "ERP003", "Amit Verma", "CSE", "B.Tech", 92));
        manager.addStudent(new Student(4, "ERP004", "Sneha Patel", "ECE", "B.Tech", 68));
        manager.addStudent(new Student(5, "ERP005", "Arjun Mehta", "AIML", "B.Tech", 95));
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setPreferredSize(new Dimension(1080, 60));
        JLabel title = new JLabel("  Student Management Dashboard", JLabel.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JPanel createBodyPanel() {
        JPanel main = new JPanel(new BorderLayout(15, 10));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        toolbar.setBackground(new Color(33, 150, 243));
        JButton addBtn = flatButton("Add");
        JButton updateBtn = flatButton("Update");
        JButton deleteBtn = flatButton("Delete");
        JButton sortNameBtn = flatButton("Sort by Name");
        JButton sortMarksBtn = flatButton("Sort by Marks");
        JButton resetBtn = flatButton("Reset");
        JButton searchBtn = flatButton("Search");
        JButton viewAllBtn = flatButton("View All");
        toolbar.add(addBtn);
        toolbar.add(updateBtn);
        toolbar.add(deleteBtn);
        toolbar.add(sortNameBtn);
        toolbar.add(sortMarksBtn);
        toolbar.add(resetBtn);
        toolbar.add(new JLabel("Search:"));
        toolbar.add(searchField);
        toolbar.add(searchBtn);
        toolbar.add(viewAllBtn);
        main.add(toolbar, BorderLayout.NORTH);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setTableColumnWidths();
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 70, 140));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setOpaque(true);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(204, 229, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(200, 200, 200));
        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Student Details"));
        form.add(label("Roll No:")); form.add(rollField);
        form.add(label("ERP ID:")); form.add(erpField);
        form.add(label("Name:")); form.add(nameField);
        form.add(label("Branch:")); form.add(branchField);
        form.add(label("Course:")); form.add(courseField);
        form.add(label("Marks:")); form.add(marksField);
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(table),
                new JScrollPane(form)
        );
        splitPane.setDividerLocation(700);
        splitPane.setResizeWeight(0.7);
        main.add(splitPane, BorderLayout.CENTER);
        addBtn.addActionListener(e -> onAdd());
        updateBtn.addActionListener(e -> onUpdate());
        deleteBtn.addActionListener(e -> onDelete());
        sortNameBtn.addActionListener(e -> { manager.sortByName(); refresh(); });
        sortMarksBtn.addActionListener(e -> { manager.sortByMarksDescending(); refresh(); });
        resetBtn.addActionListener(e -> resetFields());
        searchBtn.addActionListener(e -> onSearch());
        viewAllBtn.addActionListener(e -> { searchField.setText(""); refresh(); show("Showing all student records."); });
        searchField.addActionListener(e -> searchBtn.doClick());
        return main;
    }

    private void initMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem clear = new JMenuItem("Clear All");
        JMenuItem about = new JMenuItem("About Developer");
        clear.addActionListener(e -> {
            manager.clearAll();
            refresh();
            show("All records cleared.");
        });
        about.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Developed by Sahil Raj\nBranch: CSE-AIML\nJava Project (Swing + Collections)",
                "About", JOptionPane.INFORMATION_MESSAGE));
        file.add(clear);
        file.addSeparator();
        file.add(about);
        bar.add(file);
        setJMenuBar(bar);
    }

    private JPanel createStatusBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(240, 240, 240));
        p.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        p.add(statusLabel, BorderLayout.WEST);
        return p;
    }

    private JButton flatButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(25, 118, 210));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(30, 136, 229)); }
            public void mouseExited(MouseEvent e) { b.setBackground(new Color(25, 118, 210)); }
        });
        return b;
    }

    private JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return l;
    }

    private void setTableColumnWidths() {
        int[] widths = {70, 100, 120, 100, 100, 70, 70};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
    }

    private void refresh() {
        model.setRowCount(0);
        for (Student s : manager.getAllStudents()) {
            model.addRow(new Object[]{
                    s.getRollNo(), s.getErpId(), s.getName(),
                    s.getBranch(), s.getCourse(), s.getMarks(), s.getGrade()
            });
        }
    }

    private void resetFields() {
        rollField.setText("");
        erpField.setText("");
        nameField.setText("");
        branchField.setText("");
        courseField.setText("");
        marksField.setText("");
        show("Fields cleared.");
    }

    private void onAdd() {
        try {
            int roll = parseRoll();
            if (manager.existsRoll(roll)) throw new ValidationException("Roll exists!");
            Student s = new Student(roll, parseErp(), parseName(), parseBranch(), parseCourse(), parseMarks());
            manager.addStudent(s);
            refresh();
            resetFields();
            show("Student added: " + s.getName());
        } catch (ValidationException ex) { error(ex.getMessage()); }
    }

    private void onUpdate() {
        try {
            int roll = parseRoll();
            if (!manager.existsRoll(roll)) throw new ValidationException("Roll not found!");
            manager.updateStudent(roll, parseErp(), parseName(), parseBranch(), parseCourse(), parseMarks());
            refresh();
            show("Updated roll " + roll);
        } catch (ValidationException ex) { error(ex.getMessage()); }
    }

    private void onDelete() {
        try {
            int roll = parseRoll();
            if (!manager.deleteStudent(roll)) throw new ValidationException("Roll not found!");
            refresh();
            show("Deleted roll " + roll);
        } catch (ValidationException ex) { error(ex.getMessage()); }
    }

    private void onSearch() {
        String key = searchField.getText().trim().toLowerCase();
        if (key.isEmpty()) { refresh(); show("Showing all students."); return; }
        java.util.List<Student> results = new java.util.ArrayList<>();
        for (Student s : manager.getAllStudents()) {
            if (s.getName().toLowerCase().contains(key)
                    || s.getErpId().toLowerCase().contains(key)
                    || s.getBranch().toLowerCase().contains(key)
                    || s.getCourse().toLowerCase().contains(key)
                    || String.valueOf(s.getRollNo()).contains(key)) {
                results.add(s);
            }
        }
        model.setRowCount(0);
        for (Student s : results) {
            model.addRow(new Object[]{
                    s.getRollNo(), s.getErpId(), s.getName(),
                    s.getBranch(), s.getCourse(), s.getMarks(), s.getGrade()
            });
        }
        show(results.size() + " result(s) found for \"" + key + "\"");
    }

    private int parseRoll() throws ValidationException {
        try {
            int r = Integer.parseInt(rollField.getText().trim());
            if (r <= 0) throw new ValidationException("Roll must be positive!");
            return r;
        } catch (Exception e) {
            throw new ValidationException("Invalid roll number!");
        }
    }

    private String parseErp() throws ValidationException {
        String s = erpField.getText().trim();
        if (s.isEmpty()) throw new ValidationException("Enter ERP ID!");
        return s;
    }

    private String parseName() throws ValidationException {
        String s = nameField.getText().trim();
        if (s.isEmpty()) throw new ValidationException("Enter name!");
        return s;
    }

    private String parseBranch() throws ValidationException {
        String s = branchField.getText().trim();
        if (s.isEmpty()) throw new ValidationException("Enter branch!");
        return s;
    }

    private String parseCourse() throws ValidationException {
        String s = courseField.getText().trim();
        if (s.isEmpty()) throw new ValidationException("Enter course!");
        return s;
    }

    private double parseMarks() throws ValidationException {
        try {
            double m = Double.parseDouble(marksField.getText().trim());
            if (m < 0 || m > 100) throw new ValidationException("Marks 0–100 only!");
            return m;
        } catch (Exception e) { throw new ValidationException("Invalid marks!"); }
    }

    private void show(String msg) { statusLabel.setText(msg); }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
        show("Error: " + msg);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b && dimOverlay == null) {
            dimOverlay = new JPanel();
            dimOverlay.setBackground(new Color(0, 0, 0, 100));
            dimOverlay.setBounds(0, 0, getWidth(), getHeight());
            dimOverlay.setVisible(false);
            getLayeredPane().add(dimOverlay, JLayeredPane.PALETTE_LAYER);
        }
    }

    public void setDimmed(boolean dim) {
        if (dimOverlay != null) dimOverlay.setVisible(dim);
        setEnabled(!dim);
    }

    private static class ValidationException extends Exception {
        ValidationException(String m) { super(m); }
    }
}

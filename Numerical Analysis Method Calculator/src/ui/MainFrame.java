package ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import RootMethods.Bisection;
import RootMethods.FalsePosition;
import RootMethods.NewtonRaphson;
import RootMethods.Secant;
import RootMethods.FixedPoint;
import MatrixSolver.GaussianElimination;
import MatrixSolver.PartialPivoting;
import MatrixSolver.LUDecomposition;
public class MainFrame extends JFrame {


    // COLORS

    Color backgroundColor = new Color(18, 18, 18);

    Color panelColor = new Color(28, 28, 35);

    Color accentColor = new Color(88, 101, 242);

    Color buttonColor = new Color(88, 101, 242);

    Color clearColor = new Color(231, 76, 60);

    Color textColor = Color.WHITE;


    // COMPONENTS

    JComboBox<String> methodBox;

    JTextField equationField;
    JTextField xlField;
    JTextField xuField;
    JTextField toleranceField;

    JTextArea outputArea;

    JButton calculateButton;
    JButton clearButton;


    JLabel equationLabel;
    JLabel xlLabel;
    JLabel xuLabel;


    private JTextField matrixSizeField;

    private JTable matrixTable;

    private JScrollPane matrixScrollPane;

    private JTextArea matrixOutputArea;

    private JButton generateMatrixButton;

    private JButton solveGaussianButton;
    private JButton solvePivotButton;
    private JButton solveLUButton;

    // CONSTRUCTOR

    public MainFrame() {

        try {

            UIManager.setLookAndFeel(
                    "javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {

            e.printStackTrace();
        }


        // FRAME

        setTitle("Numerical Methods Calculator");

        setSize(1000, 750);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(backgroundColor);

        // MAIN TAB PANE

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.setFont(new Font("Poppins", Font.BOLD, 16));

        tabbedPane.setBackground(panelColor);

        tabbedPane.setForeground(textColor);

        // ADD PANELS

        tabbedPane.addTab("Root Finding", createRootPanel());

        tabbedPane.addTab("Matrix Solver", createMatrixPanel());

        add(tabbedPane);

        setVisible(true);
    }

    // ROOT PANEL

    private JPanel createRootPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(20, 20, 25));

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // TITLE

        JLabel title = new JLabel("Root Finding Algorithms", SwingConstants.CENTER);

        title.setFont(new Font("SansSerif", Font.BOLD, 34));

        title.setForeground(accentColor);

        mainPanel.add(title, BorderLayout.NORTH);

        // CENTER PANEL

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 20));

        centerPanel.setBackground(backgroundColor);

        // INPUT PANEL

        JPanel inputPanel = new JPanel(new GridBagLayout());

        inputPanel.setBackground(panelColor);

        inputPanel.setBorder(new CompoundBorder(new LineBorder(accentColor, 2), new EmptyBorder(20, 20, 20, 20)
                ));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(12, 12, 12, 12);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Poppins", Font.BOLD, 17);

        Font fieldFont = new Font("Poppins", Font.PLAIN, 16);

        // METHOD

        methodBox = new JComboBox<>(new String[]{

                        "Bisection",
                        "False Position",
                        "Newton Raphson",
                        "Secant",
                        "Fixed Point"
                });

        styleComboBox(methodBox);

        methodBox.addActionListener(e -> {

            String method = methodBox.getSelectedItem().toString();

            if(method.equals("Newton Raphson") || method.equals("Fixed Point")) {
                xlLabel.setText("Initial Guess (x0):");

                xuLabel.setVisible(false);
                xuField.setVisible(false);
            }

            else {

                xuLabel.setVisible(true);
                xuField.setVisible(true);
            }

            revalidate();
            repaint();
        });


        // TEXTFields

        equationField = createTextField();

        xlField = createTextField();

        xuField = createTextField();

        toleranceField = createTextField();


        // LABels

        JLabel methodLabel = createLabel("Select Method:", labelFont);

        equationLabel = createLabel("Equation f(x):", labelFont);

        xlLabel = createLabel("xL:", labelFont);

         xuLabel = createLabel("xU:", labelFont);

        JLabel toleranceLabel = createLabel("Tolerance:", labelFont);

        // ADD COMPONENTS
        gbc.gridx = 0;
        gbc.gridy = 0;

        inputPanel.add(methodLabel, gbc);

        gbc.gridx = 1;

        inputPanel.add(methodBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        inputPanel.add(equationLabel, gbc);

        gbc.gridx = 1;

        inputPanel.add(equationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        inputPanel.add(xlLabel, gbc);

        gbc.gridx = 1;

        inputPanel.add(xlField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;

        inputPanel.add(xuLabel, gbc);

        gbc.gridx = 1;

        inputPanel.add(xuField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;

        inputPanel.add(toleranceLabel, gbc);

        gbc.gridx = 1;

        inputPanel.add(toleranceField, gbc);


        // BUTTON PANEL

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBackground(panelColor);

        calculateButton = createButton("Calculate", buttonColor);

        clearButton = createButton("Clear", clearColor);

        buttonPanel.add(calculateButton);
        calculateButton.addActionListener(e -> {

            try {

                String method = methodBox.getSelectedItem().toString();

                String equation = equationField.getText();

                double tolerance = Double.parseDouble(toleranceField.getText());

                String result = "";

                if (method.equals("Bisection")) {
                    double xl = Double.parseDouble(xlField.getText());

                    double xu = Double.parseDouble(xuField.getText());

                    result = RootMethods.Bisection.solve(equation, xl, xu, tolerance);
                } else if (method.equals("False Position")) {
                    double xl = Double.parseDouble(xlField.getText());

                    double xu = Double.parseDouble(xuField.getText());

                    String eq = equationField.getText().trim();
                    System.out.println("Equation = " + eq);
                    result = RootMethods.FalsePosition.solve(
                            equation, xl, xu, tolerance);
                } else if (method.equals("Newton Raphson")) {

                    double x0 = Double.parseDouble(xlField.getText());

                    result = NewtonRaphson.solve(equation, x0, tolerance);
                } else if (method.equals("Secant")) {

                    double x0 = Double.parseDouble(xlField.getText());

                    double x1 = Double.parseDouble(xuField.getText());

                    result = Secant.solve(equation, x0, x1, tolerance);
                } else if (method.equals("Fixed Point")) {


                    double x0 = Double.parseDouble(xlField.getText());


                    result = FixedPoint.solve(equation, x0, tolerance);
                }
                outputArea.setText(result);

            } catch (Exception ex) {

                outputArea.setText("Invalid Input!");
            }

        });

        buttonPanel.add(Box.createHorizontalStrut(15));

        buttonPanel.add(clearButton);
        clearButton.addActionListener(e -> {

            equationField.setText("");

            xlField.setText("");

            xuField.setText("");

            toleranceField.setText("");

            outputArea.setText("");
        });

        // LEFT PANEL

        JPanel leftPanel = new JPanel(new BorderLayout());

        leftPanel.setBackground(backgroundColor);

        leftPanel.add(inputPanel, BorderLayout.CENTER);

        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // OUTPUT PANEL

        JPanel outputPanel = new JPanel(new BorderLayout());

        outputPanel.setBackground(panelColor);

        outputPanel.setBorder(new CompoundBorder(new LineBorder(accentColor, 2), new EmptyBorder(15, 15, 15, 15)
                ));

        JLabel outputTitle = new JLabel("Iteration Output");

        outputTitle.setFont(new Font("Poppins", Font.BOLD, 22));

        outputTitle.setForeground(accentColor);

        // TEXT AREA

        outputArea = new JTextArea();

        outputArea.setBackground(new Color(15, 15, 15));

        outputArea.setForeground(new Color(57, 255, 20));
        outputArea.setCaretColor(Color.WHITE);

        outputArea.setFont(new Font("Consolas", Font.PLAIN, 16));

        outputArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        scrollPane.setBorder(null);

        // ADD OUTPUT

        outputPanel.add(outputTitle, BorderLayout.NORTH);

        outputPanel.add(scrollPane, BorderLayout.CENTER);



        centerPanel.add(leftPanel);

        centerPanel.add(outputPanel);


        mainPanel.add(centerPanel, BorderLayout.CENTER);

        return mainPanel;
    }


    private JPanel createMatrixPanel() {


        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel(
                "Matrix & Linear Equation Solvers", SwingConstants.CENTER);

        title.setFont(new Font("Poppins", Font.BOLD, 30));

        title.setForeground(accentColor);


        JPanel topSection = new JPanel(new BorderLayout(10, 10));

        topSection.setBackground(backgroundColor);

        topSection.add(title, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        topPanel.setBackground(panelColor);

        JLabel sizeLabel = new JLabel("Matrix Size:");

        sizeLabel.setForeground(Color.WHITE);

        matrixSizeField = new JTextField(5);

        matrixSizeField.setHorizontalAlignment(JTextField.CENTER);

        matrixSizeField.setFont(new Font("Poppins", Font.BOLD, 16));

        matrixSizeField.setBackground(new Color(40, 40, 50));

        matrixSizeField.setForeground(Color.WHITE);

        generateMatrixButton = createButton("Generate Matrix", accentColor);

        topPanel.add(sizeLabel);
        topPanel.add(matrixSizeField);
        topPanel.add(generateMatrixButton);

        topSection.add(topPanel, BorderLayout.SOUTH);

        panel.add(topSection, BorderLayout.NORTH);

        // TABLE

        matrixTable = new JTable(3, 4);

        styleMatrixTable(matrixTable);

        matrixScrollPane = new JScrollPane(matrixTable);

        JPanel tablePanel = new JPanel(new BorderLayout());

        tablePanel.setBackground(panelColor);

        tablePanel.setBorder(
                new CompoundBorder(new LineBorder(accentColor, 1, true),
                        new EmptyBorder(15, 15, 15, 15)));

        JLabel tableTitle = new JLabel(" Matrix Input");

        tableTitle.setForeground(accentColor);

        tableTitle.setFont(new Font("Poppins", Font.BOLD, 20));

        tablePanel.add(tableTitle, BorderLayout.NORTH);

        tablePanel.add(matrixScrollPane, BorderLayout.CENTER);

        panel.add(tablePanel, BorderLayout.CENTER);


        solveGaussianButton = createButton("Gaussian", new Color(46, 204, 113));

        solvePivotButton = createButton("Partial Pivot", new Color(52, 152, 219));

        solveLUButton = createButton("LU Decomposition", new Color(155, 89, 182));

        JPanel methodPanel = new JPanel(new GridLayout(1, 3, 15, 15));

        methodPanel.setBackground(backgroundColor);

        methodPanel.add(solveGaussianButton);

        methodPanel.add(solvePivotButton);

        methodPanel.add(solveLUButton);

        matrixOutputArea = new JTextArea(8, 20);

        matrixOutputArea.setEditable(false);

        matrixOutputArea.setBackground(new Color(12, 12, 18));

        matrixOutputArea.setForeground(new Color(0, 255, 140));

        matrixOutputArea.setFont(new Font("Consolas", Font.PLAIN, 15));

        matrixOutputArea.setMargin(new Insets(15, 15, 15, 15));

        JPanel outputPanel = new JPanel(new BorderLayout());

        outputPanel.setBackground(panelColor);

        outputPanel.setBorder(new CompoundBorder(new LineBorder(accentColor, 1, true),
                        new EmptyBorder(10, 10, 10, 10)));

        JLabel outputTitle = new JLabel(" Solution Output");

        outputTitle.setForeground(accentColor);

        outputTitle.setFont(new Font("Poppins", Font.BOLD, 18));

        outputPanel.add(outputTitle, BorderLayout.NORTH);

        outputPanel.add(new JScrollPane(matrixOutputArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        bottomPanel.setBackground(backgroundColor);

        bottomPanel.add(methodPanel, BorderLayout.NORTH);

        bottomPanel.add(outputPanel, BorderLayout.CENTER);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        generateMatrixButton.addActionListener(e -> {

            try {

                int n = Integer.parseInt(matrixSizeField.getText());

                matrixTable = new JTable(n, n + 1);

                matrixTable.setRowHeight(35);

                matrixScrollPane.setViewportView(matrixTable);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Matrix Size");
            }
        });
        solveGaussianButton.addActionListener(e -> {

            try {

                double[][] matrix = getMatrixFromTable();

                int n = matrix.length;

                matrixOutputArea.setText(GaussianElimination.solve(matrix, n));

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage());
            }
        });
        solvePivotButton.addActionListener(e -> {

            try {

                if (matrixTable.isEditing()) {
                    matrixTable.getCellEditor().stopCellEditing();
                }

                int n = matrixTable.getRowCount();

                double[][] matrix = new double[n][n + 1];

                for (int i = 0; i < n; i++) {

                    for (int j = 0; j <= n; j++) {

                        matrix[i][j] = Double.parseDouble(matrixTable.getValueAt(i, j).toString());
                    }
                }
                String result = PartialPivoting.solve(matrix, n);

                matrixOutputArea.setText(result);

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Matrix Input");
            }


        });

        solveLUButton.addActionListener(e -> {

            try {

                double[][] matrix = getMatrixFromTable();

                int n = matrix.length;

                String result = LUDecomposition.solve(matrix, n);

                matrixOutputArea.setText(result);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        return panel;
    }


    // CUSTOM METHODS

    private JTextField createTextField() {

        JTextField field = new JTextField(15);

        field.setFont(new Font("Poppins", Font.PLAIN, 16));

        field.setBackground(new Color(40, 40, 50));

        field.setForeground(Color.WHITE);

        field.setCaretColor(Color.WHITE);

        field.setBorder(new CompoundBorder(new LineBorder(accentColor, 2, true),

                new EmptyBorder(10, 12, 10, 12)
        ));

        return field;
    }

    private JLabel createLabel(String text, Font font) {

        JLabel label = new JLabel(text);

        label.setForeground(textColor);

        label.setFont(font);

        return label;
    }


    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 16));

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setOpaque(true);

        button.setBorderPainted(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setPreferredSize(new Dimension(180, 50));

        button.setBorder(new EmptyBorder(12, 25, 12, 25));
        button.setOpaque(true);

        button.setBorderPainted(false);

        // HOVER EFFect

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {

                button.setBackground(color.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {

                button.setBackground(color);
            }
        });

        return button;
    }

    private void styleComboBox(JComboBox<String> comboBox) {

        comboBox.setBackground(new Color(45, 45, 45));

        comboBox.setForeground(Color.WHITE);

        comboBox.setFont(new Font("Poppins", Font.PLAIN, 16));
    }

    private double[][] getMatrixFromTable()
            throws Exception {

        if (matrixTable.isEditing()) {
            matrixTable.getCellEditor().stopCellEditing();
        }

        int n = matrixTable.getRowCount();

        double[][] matrix = new double[n][n + 1];

        for (int i = 0; i < n; i++) {

            for (int j = 0; j <= n; j++) {

                Object value = matrixTable.getValueAt(i, j);

                if (value == null || value.toString().trim().isEmpty()) {

                    throw new Exception("Fill all matrix cells");
                }

                matrix[i][j] = Double.parseDouble(value.toString());
            }
        }

        return matrix;
    }


    private void styleMatrixTable(JTable matrixTable) {

        matrixTable.setBackground(new Color(35, 35, 45));

        matrixTable.setForeground(Color.WHITE);

        matrixTable.setGridColor(new Color(70, 70, 90));

        matrixTable.setRowHeight(40);

        matrixTable.setFont(new Font("Poppins", Font.PLAIN, 15));

        matrixTable.getTableHeader().setBackground(accentColor);

        matrixTable.getTableHeader().setForeground(Color.WHITE);

        matrixTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 15));
    }

    public static void main(String[] args) {
        new MainFrame();

    }
}


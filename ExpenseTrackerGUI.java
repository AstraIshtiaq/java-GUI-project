import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseTrackerGUI extends JFrame {

    private List<Expense> expenses = new ArrayList<>();
    private JTextField nameField;
    private JTextField amountField;
    private JTextField dateField;
    private JTextArea expenseArea;

    public ExpenseTrackerGUI() {
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField = new JTextField());
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField = new JTextField());
        inputPanel.add(new JLabel("Date (yyyy-mm-dd):"));
        inputPanel.add(dateField = new JTextField());
        add(inputPanel, BorderLayout.NORTH);

        expenseArea = new JTextArea();
        expenseArea.setEditable(false);
        add(new JScrollPane(expenseArea), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> addExpense());

        JButton viewButton = new JButton("View Expenses");
        viewButton.addActionListener(e -> viewExpenses());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addExpense() {
        String name = nameField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String dateText = dateField.getText();
        Date date = null;

        try {
            date = java.sql.Date.valueOf(dateText);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-mm-dd.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Expense expense = new Expense(name, amount, date);
        expenses.add(expense);

        nameField.setText("");
        amountField.setText("");
        dateField.setText("");

        expenseArea.append("Added Expense: " + expense + "\n");
    }

    private void viewExpenses() {
        if (expenses.isEmpty()) {
            expenseArea.setText("No expenses found.");
        } else {
            expenseArea.setText("Expense List:\n");
            expenses.forEach(expense -> expenseArea.append(expense + "\n"));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTrackerGUI::new);
    }
}

class Expense {

    private String name;
    private double amount;
    private Date date;

    public Expense(String name, double amount, Date date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Amount: " + amount + ", Date: " + date;
    }
}

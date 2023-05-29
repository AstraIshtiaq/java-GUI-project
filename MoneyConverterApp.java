import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class MoneyConverterApp extends JFrame implements ActionListener {
    private JTextField amountField;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JButton convertButton;
    private JLabel resultLabel;

    private static final String[] CURRENCIES = { "USD", "EUR", "TRY", "JPY", "BDT" };

    public MoneyConverterApp() {
        // Set up the frame
        setTitle("Money Converter");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the amount field
        amountField = new JTextField();
        add(amountField, BorderLayout.NORTH);

        // Create the currency conversion components
        JPanel currencyPanel = new JPanel();
        currencyPanel.setLayout(new FlowLayout());

        fromCurrencyComboBox = new JComboBox<>(CURRENCIES);
        currencyPanel.add(fromCurrencyComboBox);

        currencyPanel.add(new JLabel("to"));

        toCurrencyComboBox = new JComboBox<>(CURRENCIES);
        currencyPanel.add(toCurrencyComboBox);

        convertButton = new JButton("Convert");
        convertButton.addActionListener(this);
        currencyPanel.add(convertButton);

        add(currencyPanel, BorderLayout.CENTER);

        // Create the result label
        resultLabel = new JLabel();
        add(resultLabel, BorderLayout.CENTER);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == convertButton) {
            try {
                String amountText = amountField.getText();
                double amount = Double.parseDouble(amountText);
                String fromCurrency = (String) fromCurrencyComboBox.getSelectedItem();
                String toCurrency = (String) toCurrencyComboBox.getSelectedItem();
                double convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                String resultText = decimalFormat.format(amount) + " " + fromCurrency + " = " +
                        decimalFormat.format(convertedAmount) + " " + toCurrency;
                resultLabel.setText(resultText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        // Currency conversion logic
        double rateFromUSD = 1.0;
        double rateToUSD = 1.0;

        switch (fromCurrency) {
            case "EUR":
                rateFromUSD = 1.22;
                break;
            case "TRY":
                rateFromUSD = 1.41;
                break;
            case "JPY":
                rateFromUSD = 0.0091;
                break;
            case "BDT":
                rateFromUSD = 0.011;
                break;
        }

        switch (toCurrency) {
            case "EUR":
                rateToUSD = 0.9323;
                break;
            case "TRY":
                rateToUSD = 19.97;
                break;
            case "JPY":
                rateToUSD = 140.66;
                break;
            case "BDT":
                rateToUSD = 107.20;
                break;
        }

        double amountInUSD = amount / rateFromUSD;
        double convertedAmount = amountInUSD * rateToUSD;

        return convertedAmount;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MoneyConverterApp::new);
    }
}

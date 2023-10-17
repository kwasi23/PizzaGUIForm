import javax.swing.*; // Importing Swing package for GUI components like JFrame, JButton, etc.
import java.awt.*; // Importing Java AWT package for layouts, borders, and events.
import java.awt.event.*; // Importing event package for event handling like button click.

public class PizzaGUIFrame extends JFrame { // Creating a class that inherits JFrame.

    // Defining constants for the prices and tax rate.
    private final double[] SIZE_COSTS = {8.00, 12.00, 16.00, 20.00}; // Base costs for different pizza sizes.
    private final double TOPPING_COST = 1.00; // Cost per topping.
    private final double TAX_RATE = 0.07; // Tax rate.

    // Declaring GUI components.
    private JRadioButton thinCrust, regularCrust, deepDishCrust; // Radio buttons for crust types.
    private JComboBox<String> sizes; // Combo box for pizza sizes.
    private JCheckBox cheese, pepperoni, sausage, bacon, mushrooms, onions; // Checkboxes for toppings.
    private JTextArea receiptArea; // Text area to display the receipt.

    public PizzaGUIFrame() { // Constructor to set up the GUI.
        createInterface(); // Calling method to set up the interface.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setting the close operation for the JFrame.
        setSize(600, 600); // Setting the size of the JFrame.
        setTitle("Pizza Order"); // Setting the title of the JFrame.
        setVisible(true); // Making the JFrame visible.
    }

    private void createInterface() { // Method to create and add components to the JFrame.
        setLayout(new BorderLayout()); // Setting the layout manager for the JFrame.

        JPanel mainPanel = new JPanel(); // Creating the main panel to hold other components.
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Setting the layout manager for the main panel.

        // Adding sub-panels to the main panel.
        mainPanel.add(createCrustPanel()); // Adding crust panel.
        mainPanel.add(createSizePanel()); // Adding size panel.
        mainPanel.add(createToppingPanel()); // Adding topping panel.

        // Setting up the receipt area.
        receiptArea = new JTextArea(10, 40); // Creating the text area with specific rows and columns.
        receiptArea.setEditable(false); // Making the text area non-editable.
        JScrollPane scrollPane = new JScrollPane(receiptArea); // Adding the text area to a scroll pane.
        scrollPane.setBorder(BorderFactory.createTitledBorder("Receipt")); // Setting a titled border for the scroll pane.
        mainPanel.add(scrollPane); // Adding the scroll pane to the main panel.

        // Adding the main panel and button panel to the JFrame.
        add(mainPanel, BorderLayout.CENTER); // Adding the main panel at the center.
        add(createButtonPanel(), BorderLayout.SOUTH); // Adding the button panel at the bottom.
    }

    private JPanel createCrustPanel() { // Method to create and setup the crust panel.
        JPanel panel = new JPanel(); // Creating a panel for the crust options.
        panel.setBorder(BorderFactory.createTitledBorder("Crust")); // Setting a titled border.

        // Creating radio buttons for crust types and adding them to the panel.
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");

        // Grouping the radio buttons so only one can be selected at a time.
        ButtonGroup group = new ButtonGroup();
        group.add(thinCrust);
        group.add(regularCrust);
        group.add(deepDishCrust);

        // Adding the radio buttons to the panel.
        panel.add(thinCrust);
        panel.add(regularCrust);
        panel.add(deepDishCrust);

        return panel; // Returning the crust panel.
    }

    private JPanel createSizePanel() { // Method to create and setup the size panel.
        JPanel panel = new JPanel(); // Creating a panel for the size options.
        panel.setBorder(BorderFactory.createTitledBorder("Size")); // Setting a titled border.

        // Creating a combo box for size options and adding it to the panel.
        sizes = new JComboBox<>(new String[]{"Small", "Medium", "Large", "Super"});
        panel.add(sizes);

        return panel; // Returning the size panel.
    }

    private JPanel createToppingPanel() { // Method to create and setup the topping panel.
        JPanel panel = new JPanel(); // Creating a panel for the topping options.
        panel.setBorder(BorderFactory.createTitledBorder("Toppings")); // Setting a titled border.

        // Creating checkboxes for toppings and adding them to the panel.
        cheese = new JCheckBox("Cheese");
        pepperoni = new JCheckBox("Pepperoni");
        sausage = new JCheckBox("Sausage");
        bacon = new JCheckBox("Bacon");
        mushrooms = new JCheckBox("Mushrooms");
        onions = new JCheckBox("Onions");

        // Adding the checkboxes to the panel.
        panel.add(cheese);
        panel.add(pepperoni);
        panel.add(sausage);
        panel.add(bacon);
        panel.add(mushrooms);
        panel.add(onions);

        return panel; // Returning the topping panel.
    }

    private JPanel createButtonPanel() { // Method to create and setup the button panel.
        JPanel panel = new JPanel(); // Creating a panel for the buttons.

        // Creating and setting up the order button.
        JButton orderButton = new JButton("Order");
        orderButton.addActionListener(e -> displayOrder()); // Adding an action listener to handle button click.
        panel.add(orderButton); // Adding the button to the panel.

        // Creating and setting up the clear button.
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearOrder()); // Adding an action listener to handle button click.
        panel.add(clearButton); // Adding the button to the panel.

        // Creating and setting up the quit button.
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quit()); // Adding an action listener to handle button click.
        panel.add(quitButton); // Adding the button to the panel.

        return panel; // Returning the button panel.
    }

    private void displayOrder() { // Method to display the order details in the receipt area.
        double subTotal = calculateSubTotal(); // Calculating the subtotal.
        double tax = subTotal * TAX_RATE; // Calculating the tax.
        double total = subTotal + tax; // Calculating the total.

        // Formatting and displaying the receipt.
        receiptArea.setText("============================\n");
        receiptArea.append("Type of Crust & Size\t\tPrice\n");

        // Displaying selected crust, size, and toppings.
        receiptArea.append(getSelectedCrust() + " & " + getSelectedSize() + "\t\t$" + String.format("%.2f", subTotal) + "\n");
        for (JCheckBox topping : new JCheckBox[]{cheese, pepperoni, sausage, bacon, mushrooms, onions}) {
            if (topping.isSelected()) {
                receiptArea.append(topping.getText() + "\t\t$" + String.format("%.2f", TOPPING_COST) + "\n");
            }
        }

        // Displaying subtotal, tax, and total.
        receiptArea.append("Sub-total: \t\t\t$" + String.format("%.2f", subTotal) + "\n");
        receiptArea.append("Tax: \t\t\t\t$" + String.format("%.2f", tax) + "\n");
        receiptArea.append("---------------------------------\n");
        receiptArea.append("Total: \t\t\t\t$" + String.format("%.2f", total) + "\n");
        receiptArea.append("============================");
    }

    private double calculateSubTotal() { // Method to calculate the subtotal.
        double subTotal = SIZE_COSTS[sizes.getSelectedIndex()]; // Getting the cost based on selected size.
        for (JCheckBox topping : new JCheckBox[]{cheese, pepperoni, sausage, bacon, mushrooms, onions}) {
            if (topping.isSelected()) { // If a topping is selected, add its cost to the subtotal.
                subTotal += TOPPING_COST;
            }
        }
        return subTotal; // Returning the calculated subtotal.
    }

    private String getSelectedCrust() { // Method to get the selected crust type.
        for (JRadioButton crust : new JRadioButton[]{thinCrust, regularCrust, deepDishCrust}) {
            if (crust.isSelected()) { // If a crust type is selected, return its text.
                return crust.getText();
            }
        }
        return ""; // Returning an empty string if no crust type is selected.
    }

    private String getSelectedSize() { // Method to get the selected size.
        return sizes.getSelectedItem().toString(); // Returning the selected item from the combo box.
    }

    private void clearOrder() { // Method to clear the order.
        ButtonGroup group = new ButtonGroup(); // Creating a button group.
        group.clearSelection(); // Clearing the selection of radio buttons.
        sizes.setSelectedIndex(0); // Resetting the combo box selection.
        for (JCheckBox topping : new JCheckBox[]{cheese, pepperoni, sausage, bacon, mushrooms, onions}) {
            topping.setSelected(false); // Deselecting all topping checkboxes.
        }
        receiptArea.setText(""); // Clearing the receipt area.
    }

    private void quit() { // Method to handle quit operation.
        // Showing a confirmation dialog to confirm quit.
        int confirmed = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Confirm Quit", JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) { // If yes is selected, dispose the JFrame.
            dispose();
        }
    }
}

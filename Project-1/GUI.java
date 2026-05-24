import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    
    // Data lists
    private ArrayList<Product> products;
    private ArrayList<Customer> customers;
    private ArrayList<Order> orders;
    
    // Tables
    private JTable productTable;
    private JTable customerTable;
    private JTable orderTable;
    private DefaultTableModel productTableModel;
    private DefaultTableModel customerTableModel;
    private DefaultTableModel orderTableModel;
    
    // Current order building
    private Order currentOrder;
    private DefaultTableModel cartTableModel;
    private JTable cartTable;
    
    public GUI() {
        // Load data
        products = DataStorage.loadProducts();
        customers = DataStorage.loadCustomers();
        orders = DataStorage.loadOrders();
        
        // Initialize GUI
        frame = new JFrame("Wholesale Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        
        // Create menu bar
        createMenuBar();
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Products", createProductsPanel());
        tabbedPane.addTab("Customers", createCustomersPanel());
        tabbedPane.addTab("New Order", createOrderPanel());
        tabbedPane.addTab("Orders History", createOrdersPanel());
        tabbedPane.addTab("Reports", createReportsPanel());
        
        frame.add(tabbedPane);
        frame.setVisible(true);
        
        // Add shutdown hook to save data
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveData()));
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem saveItem = new JMenuItem("Save Data");
        saveItem.addActionListener(e -> saveData());
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            saveData();
            System.exit(0);
        });
        
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }
    
    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create table model
        String[] columns = {"ID", "Name", "Wholesale Price", "Retail Price", "Stock", "Min Stock", "Status"};
        productTableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(productTableModel);
        refreshProductTable();
        
        JScrollPane scrollPane = new JScrollPane(productTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Product");
        JButton editButton = new JButton("Edit Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> showAddProductDialog());
        editButton.addActionListener(e -> showEditProductDialog());
        deleteButton.addActionListener(e -> deleteProduct());
        refreshButton.addActionListener(e -> refreshProductTable());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createCustomersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Name", "Phone", "Address", "GST Number", "Credit Balance"};
        customerTableModel = new DefaultTableModel(columns, 0);
        customerTable = new JTable(customerTableModel);
        refreshCustomerTable();
        
        JScrollPane scrollPane = new JScrollPane(customerTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Customer");
        JButton editButton = new JButton("Edit Customer");
        JButton deleteButton = new JButton("Delete Customer");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> showAddCustomerDialog());
        editButton.addActionListener(e -> showEditCustomerDialog());
        deleteButton.addActionListener(e -> deleteCustomer());
        refreshButton.addActionListener(e -> refreshCustomerTable());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createOrderPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Top panel for customer and order info
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Order Information"));
        
        JComboBox<Customer> customerCombo = new JComboBox<>();
        for (Customer c : customers) {
            customerCombo.addItem(c);
        }
        
        JComboBox<String> paymentCombo = new JComboBox<>(new String[]{"Cash", "Credit"});
        JTextField taxField = new JTextField("0.18");
        
        JButton startOrderButton = new JButton("Start New Order");
        
        topPanel.add(new JLabel("Select Customer:"));
        topPanel.add(customerCombo);
        topPanel.add(new JLabel("Payment Method:"));
        topPanel.add(paymentCombo);
        topPanel.add(new JLabel("Tax Rate (decimal):"));
        topPanel.add(taxField);
        
        // Center panel - product selection and cart
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Left side - available products
        JPanel productsPanel = new JPanel(new BorderLayout());
        productsPanel.setBorder(BorderFactory.createTitledBorder("Available Products"));
        
        String[] productColumns = {"ID", "Name", "Price", "Stock"};
        DefaultTableModel availableTableModel = new DefaultTableModel(productColumns, 0);
        JTable availableTable = new JTable(availableTableModel);
        refreshAvailableProducts(availableTableModel);
        
        JScrollPane availableScroll = new JScrollPane(availableTable);
        
        JPanel addToCartPanel = new JPanel(new FlowLayout());
        JTextField quantityField = new JTextField(10);
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartPanel.add(new JLabel("Quantity:"));
        addToCartPanel.add(quantityField);
        addToCartPanel.add(addToCartButton);
        
        productsPanel.add(availableScroll, BorderLayout.CENTER);
        productsPanel.add(addToCartPanel, BorderLayout.SOUTH);
        
        // Right side - cart
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        
        String[] cartColumns = {"Product", "Quantity", "Price", "Discount", "Total"};
        cartTableModel = new DefaultTableModel(cartColumns, 0);
        cartTable = new JTable(cartTableModel);
        JScrollPane cartScroll = new JScrollPane(cartTable);
        
        cartPanel.add(cartScroll, BorderLayout.CENTER);
        
        centerPanel.add(productsPanel);
        centerPanel.add(cartPanel);
        
        // Bottom panel - order total and actions
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        JTextArea totalArea = new JTextArea(5, 30);
        totalArea.setEditable(false);
        
        JPanel actionPanel = new JPanel();
        JButton placeOrderButton = new JButton("Place Order");
        JButton clearCartButton = new JButton("Clear Cart");
        
        actionPanel.add(placeOrderButton);
        actionPanel.add(clearCartButton);
        
        bottomPanel.add(new JScrollPane(totalArea), BorderLayout.CENTER);
        bottomPanel.add(actionPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Event handlers
        startOrderButton.addActionListener(e -> {
            Customer selected = (Customer) customerCombo.getSelectedItem();
            String payment = (String) paymentCombo.getSelectedItem();
            double tax = Double.parseDouble(taxField.getText());
            
            int orderId = orders.size() + 1;
            currentOrder = new Order(orderId, selected, payment, tax);
            cartTableModel.setRowCount(0);
            totalArea.setText("");
            JOptionPane.showMessageDialog(frame, "New order started for " + selected.getName());
        });
        
        addToCartButton.addActionListener(e -> {
            if (currentOrder == null) {
                JOptionPane.showMessageDialog(frame, "Please start a new order first!");
                return;
            }
            
            int selectedRow = availableTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a product!");
                return;
            }
            
            int productId = (int) availableTableModel.getValueAt(selectedRow, 0);
            Product product = findProductById(productId);
            int quantity = Integer.parseInt(quantityField.getText());
            
            if (!BusinessLogic.checkStock(product, quantity)) {
                JOptionPane.showMessageDialog(frame, "Insufficient stock! Available: " + product.getStockQuantity());
                return;
            }
            
            double discount = BusinessLogic.calculateDiscount(quantity);
            OrderItem item = new OrderItem(product, quantity, product.getWholesalePrice(), discount);
            currentOrder.addItem(item);
            
            // Update cart table
            cartTableModel.addRow(new Object[]{
                product.getName(),
                quantity,
                product.getWholesalePrice(),
                (discount * 100) + "%",
                item.getTotal()
            });
            
            // Update total display
            updateTotalDisplay(totalArea);
            quantityField.setText("");
        });
        
        placeOrderButton.addActionListener(e -> {
            if (currentOrder == null || currentOrder.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No order to place!");
                return;
            }
            
            // Update stock
            for (OrderItem item : currentOrder.getItems()) {
                BusinessLogic.updateStock(item.getProduct(), item.getQuantity());
            }
            
            orders.add(currentOrder);
            refreshOrderTable();
            JOptionPane.showMessageDialog(frame, "Order placed successfully! Total: ₹" + currentOrder.getTotal());
            
            // Reset
            currentOrder = null;
            cartTableModel.setRowCount(0);
            totalArea.setText("");
            refreshProductTable();
        });
        
        clearCartButton.addActionListener(e -> {
            if (currentOrder != null) {
                currentOrder = null;
                cartTableModel.setRowCount(0);
                totalArea.setText("");
            }
        });
        
        return mainPanel;
    }
    
    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"Order ID", "Customer", "Date", "Payment", "Total"};
        orderTableModel = new DefaultTableModel(columns, 0);
        orderTable = new JTable(orderTableModel);
        refreshOrderTable();
        
        JScrollPane scrollPane = new JScrollPane(orderTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshOrderTable());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        
        JButton refreshReportButton = new JButton("Generate Report");
        
        refreshReportButton.addActionListener(e -> {
            StringBuilder report = new StringBuilder();
            report.append("=== WHOLESALE MANAGEMENT REPORT ===\n\n");
            report.append("Total Products: ").append(products.size()).append("\n");
            report.append("Total Customers: ").append(customers.size()).append("\n");
            report.append("Total Orders: ").append(orders.size()).append("\n");
            report.append("Total Sales: ₹").append(BusinessLogic.calculateTotalSales(orders)).append("\n\n");
            
            report.append("=== LOW STOCK ALERTS ===\n");
            ArrayList<Product> lowStock = BusinessLogic.getLowStockProducts(products);
            if (lowStock.isEmpty()) {
                report.append("No low stock products\n");
            } else {
                for (Product p : lowStock) {
                    report.append(p.getName()).append(" - Stock: ").append(p.getStockQuantity())
                          .append(" (Min: ").append(p.getMinStockLevel()).append(")\n");
                }
            }
            
            reportArea.setText(report.toString());
        });
        
        panel.add(new JScrollPane(reportArea));
        panel.add(refreshReportButton);
        
        return panel;
    }
    
    // Helper methods
    private void refreshProductTable() {
        productTableModel.setRowCount(0);
        for (Product p : products) {
            String status = p.isLowStock() ? "LOW STOCK" : "OK";
            productTableModel.addRow(new Object[]{
                p.getId(), p.getName(), p.getWholesalePrice(), 
                p.getRetailPrice(), p.getStockQuantity(), p.getMinStockLevel(), status
            });
        }
    }
    
    private void refreshCustomerTable() {
        customerTableModel.setRowCount(0);
        for (Customer c : customers) {
            customerTableModel.addRow(new Object[]{
                c.getId(), c.getName(), c.getPhone(), c.getAddress(), c.getGstNumber(), c.getCreditBalance()
            });
        }
    }
    
    private void refreshOrderTable() {
        orderTableModel.setRowCount(0);
        for (Order o : orders) {
            orderTableModel.addRow(new Object[]{
                o.getOrderId(), o.getCustomer().getName(), o.getOrderDate(), 
                o.getPaymentMethod(), o.getTotal()
            });
        }
    }
    
    private void refreshAvailableProducts(DefaultTableModel model) {
        model.setRowCount(0);
        for (Product p : products) {
            model.addRow(new Object[]{p.getId(), p.getName(), p.getWholesalePrice(), p.getStockQuantity()});
        }
    }
    
    private void updateTotalDisplay(JTextArea area) {
        if (currentOrder != null) {
            area.setText(String.format(
                "Subtotal: ₹%.2f\nTax (%.0f%%): ₹%.2f\nTOTAL: ₹%.2f",
                currentOrder.getSubtotal(),
                currentOrder.getTaxRate() * 100,
                currentOrder.getTaxAmount(),
                currentOrder.getTotal()
            ));
        }
    }
    
    private void showAddProductDialog() {
        JDialog dialog = new JDialog(frame, "Add Product", true);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));
        dialog.setSize(400, 350);
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField wholesaleField = new JTextField();
        JTextField retailField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField minStockField = new JTextField();
        
        dialog.add(new JLabel("ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Wholesale Price:"));
        dialog.add(wholesaleField);
        dialog.add(new JLabel("Retail Price:"));
        dialog.add(retailField);
        dialog.add(new JLabel("Stock Quantity:"));
        dialog.add(stockField);
        dialog.add(new JLabel("Min Stock Level:"));
        dialog.add(minStockField);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Product p = new Product(
                Integer.parseInt(idField.getText()),
                nameField.getText(),
                Double.parseDouble(wholesaleField.getText()),
                Double.parseDouble(retailField.getText()),
                Integer.parseInt(stockField.getText()),
                Integer.parseInt(minStockField.getText())
            );
            products.add(p);
            refreshProductTable();
            dialog.dispose();
        });
        
        dialog.add(saveButton);
        dialog.setVisible(true);
    }
    
    private void showEditProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a product to edit!");
            return;
        }
        
        int id = (int) productTableModel.getValueAt(selectedRow, 0);
        Product product = findProductById(id);
        
        JDialog dialog = new JDialog(frame, "Edit Product", true);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));
        dialog.setSize(400, 350);
        
        JTextField nameField = new JTextField(product.getName());
        JTextField wholesaleField = new JTextField(String.valueOf(product.getWholesalePrice()));
        JTextField retailField = new JTextField(String.valueOf(product.getRetailPrice()));
        JTextField stockField = new JTextField(String.valueOf(product.getStockQuantity()));
        JTextField minStockField = new JTextField(String.valueOf(product.getMinStockLevel()));
        
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Wholesale Price:"));
        dialog.add(wholesaleField);
        dialog.add(new JLabel("Retail Price:"));
        dialog.add(retailField);
        dialog.add(new JLabel("Stock Quantity:"));
        dialog.add(stockField);
        dialog.add(new JLabel("Min Stock Level:"));
        dialog.add(minStockField);
        
        JButton saveButton = new JButton("Update");
        saveButton.addActionListener(e -> {
            product.setName(nameField.getText());
            product.setWholesalePrice(Double.parseDouble(wholesaleField.getText()));
            product.setRetailPrice(Double.parseDouble(retailField.getText()));
            product.setStockQuantity(Integer.parseInt(stockField.getText()));
            product.setMinStockLevel(Integer.parseInt(minStockField.getText()));
            refreshProductTable();
            dialog.dispose();
        });
        
        dialog.add(saveButton);
        dialog.setVisible(true);
    }
    
    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a product to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) productTableModel.getValueAt(selectedRow, 0);
            products.remove(findProductById(id));
            refreshProductTable();
        }
    }
    
    private void showAddCustomerDialog() {
        JDialog dialog = new JDialog(frame, "Add Customer", true);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));
        dialog.setSize(400, 300);
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField gstField = new JTextField();
        JTextField creditField = new JTextField("0");
        
        dialog.add(new JLabel("ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Phone:"));
        dialog.add(phoneField);
        dialog.add(new JLabel("Address:"));
        dialog.add(addressField);
        dialog.add(new JLabel("GST Number:"));
        dialog.add(gstField);
        dialog.add(new JLabel("Credit Balance:"));
        dialog.add(creditField);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Customer c = new Customer(
                Integer.parseInt(idField.getText()),
                nameField.getText(),
                phoneField.getText(),
                addressField.getText(),
                gstField.getText(),
                Double.parseDouble(creditField.getText())
            );
            customers.add(c);
            refreshCustomerTable();
            dialog.dispose();
        });
        
        dialog.add(saveButton);
        dialog.setVisible(true);
    }
    
    private void showEditCustomerDialog() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a customer to edit!");
            return;
        }
        
        int id = (int) customerTableModel.getValueAt(selectedRow, 0);
        Customer customer = findCustomerById(id);
        
        JDialog dialog = new JDialog(frame, "Edit Customer", true);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));
        dialog.setSize(400, 300);
        
        JTextField nameField = new JTextField(customer.getName());
        JTextField phoneField = new JTextField(customer.getPhone());
        JTextField addressField = new JTextField(customer.getAddress());
        JTextField gstField = new JTextField(customer.getGstNumber());
        JTextField creditField = new JTextField(String.valueOf(customer.getCreditBalance()));
        
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Phone:"));
        dialog.add(phoneField);
        dialog.add(new JLabel("Address:"));
        dialog.add(addressField);
        dialog.add(new JLabel("GST Number:"));
        dialog.add(gstField);
        dialog.add(new JLabel("Credit Balance:"));
        dialog.add(creditField);
        
        JButton saveButton = new JButton("Update");
        saveButton.addActionListener(e -> {
            customer.setName(nameField.getText());
            customer.setPhone(phoneField.getText());
            customer.setAddress(addressField.getText());
            customer.setGstNumber(gstField.getText());
            customer.setCreditBalance(Double.parseDouble(creditField.getText()));
            refreshCustomerTable();
            dialog.dispose();
        });
        
        dialog.add(saveButton);
        dialog.setVisible(true);
    }
    
    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a customer to delete!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) customerTableModel.getValueAt(selectedRow, 0);
            customers.remove(findCustomerById(id));
            refreshCustomerTable();
        }
    }
    
    private Product findProductById(int id) {
        for (Product p : products) {
            if (p.getId() == id) return p;
        }
        return null;
    }
    
    private Customer findCustomerById(int id) {
        for (Customer c : customers) {
            if (c.getId() == id) return c;
        }
        return null;
    }
    
    private void saveData() {
        DataStorage.saveProducts(products);
        DataStorage.saveCustomers(customers);
        DataStorage.saveOrders(orders);
        System.out.println("Data saved successfully!");
    }
}
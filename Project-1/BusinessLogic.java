import java.util.ArrayList;

public class BusinessLogic {
    
    // Calculate discount based on quantity
    public static double calculateDiscount(int quantity) {
        if (quantity >= 100) return 0.15;  // 15% off
        if (quantity >= 50) return 0.10;   // 10% off
        if (quantity >= 25) return 0.05;   // 5% off
        return 0;
    }
    
    // Validate stock availability
    public static boolean checkStock(Product product, int quantity) {
        return product.getStockQuantity() >= quantity;
    }
    
    // Update stock after order
    public static void updateStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() - quantity);
    }
    
    // Check low stock products
    public static ArrayList<Product> getLowStockProducts(ArrayList<Product> products) {
        ArrayList<Product> lowStock = new ArrayList<>();
        for (Product p : products) {
            if (p.isLowStock()) {
                lowStock.add(p);
            }
        }
        return lowStock;
    }
    
    // Calculate total sales
    public static double calculateTotalSales(ArrayList<Order> orders) {
        double total = 0;
        for (Order order : orders) {
            total += order.getTotal();
        }
        return total;
    }
    
    // Get orders by customer
    public static ArrayList<Order> getOrdersByCustomer(ArrayList<Order> orders, int customerId) {
        ArrayList<Order> customerOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomer().getId() == customerId) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }
}
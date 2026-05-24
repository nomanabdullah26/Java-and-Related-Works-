import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int orderId;
    private Customer customer;
    private ArrayList<OrderItem> items;
    private Date orderDate;
    private String paymentMethod;
    private double taxRate;
    
    public Order(int orderId, Customer customer, String paymentMethod, double taxRate) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.orderDate = new Date();
        this.paymentMethod = paymentMethod;
        this.taxRate = taxRate;
    }
    
    public void addItem(OrderItem item) {
        items.add(item);
    }
    
    public double getSubtotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
    
    public double getTaxAmount() {
        return getSubtotal() * taxRate;
    }
    
    public double getTotal() {
        return getSubtotal() + getTaxAmount();
    }
    
    // Getters
    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public ArrayList<OrderItem> getItems() { return items; }
    public Date getOrderDate() { return orderDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getTaxRate() { return taxRate; }  // ← ADD THIS METHOD
    
    @Override
    public String toString() {
        return "Order #" + orderId + " - " + customer.getName() + " - ₹" + getTotal();
    }
}
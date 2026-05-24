import java.io.Serializable;

public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Product product;
    private int quantity;
    private double priceAtTime;
    private double discount;
    
    public OrderItem(Product product, int quantity, double priceAtTime, double discount) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
        this.discount = discount;
    }
    
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPriceAtTime() { return priceAtTime; }
    public double getDiscount() { return discount; }
    
    public double getSubtotal() {
        return quantity * priceAtTime;
    }
    
    public double getDiscountAmount() {
        return getSubtotal() * discount;
    }
    
    public double getTotal() {
        return getSubtotal() - getDiscountAmount();
    }
}
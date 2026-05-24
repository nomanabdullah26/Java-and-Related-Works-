import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private double wholesalePrice;
    private double retailPrice;
    private int stockQuantity;
    private int minStockLevel;
    
    public Product(int id, String name, double wholesalePrice, double retailPrice, int stockQuantity, int minStockLevel) {
        this.id = id;
        this.name = name;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
        this.stockQuantity = stockQuantity;
        this.minStockLevel = minStockLevel;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getWholesalePrice() { return wholesalePrice; }
    public void setWholesalePrice(double wholesalePrice) { this.wholesalePrice = wholesalePrice; }
    
    public double getRetailPrice() { return retailPrice; }
    public void setRetailPrice(double retailPrice) { this.retailPrice = retailPrice; }
    
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public int getMinStockLevel() { return minStockLevel; }
    public void setMinStockLevel(int minStockLevel) { this.minStockLevel = minStockLevel; }
    
    public boolean isLowStock() {
        return stockQuantity <= minStockLevel;
    }
    
    @Override
    public String toString() {
        return id + " - " + name + " (Stock: " + stockQuantity + ")";
    }
}
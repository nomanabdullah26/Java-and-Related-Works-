import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String phone;
    private String address;
    private String gstNumber;
    private double creditBalance;
    
    public Customer(int id, String name, String phone, String address, String gstNumber, double creditBalance) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gstNumber = gstNumber;
        this.creditBalance = creditBalance;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }
    
    public double getCreditBalance() { return creditBalance; }
    public void setCreditBalance(double creditBalance) { this.creditBalance = creditBalance; }
    
    @Override
    public String toString() {
        return id + " - " + name + " (Credit: ₹" + creditBalance + ")";
    }
}
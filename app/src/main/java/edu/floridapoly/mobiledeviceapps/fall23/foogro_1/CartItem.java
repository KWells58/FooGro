package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

public class CartItem {
    private String itemName;
    private double itemPrice;
    private String storeName;

    public CartItem(String itemName, double itemPrice, String storeName) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.storeName = storeName;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getStoreName() {
        return storeName;
    }

    // Method for unique identifier
    public String getItemId() {
        // Using item price as the identifier
        return String.valueOf(itemPrice);
    }
}

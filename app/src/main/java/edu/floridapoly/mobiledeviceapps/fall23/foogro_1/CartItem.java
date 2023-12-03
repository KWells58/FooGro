package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

public class CartItem {
    private String itemName;
    private double itemPrice;
    private String storeName;
    private int ItemId;

    public CartItem(String itemName, double itemPrice, String storeName, int ItemId) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.storeName = storeName;
        this.ItemId = ItemId;
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
    public int getItemId() {
        // Using item price as the identifier
        return ItemId;
    }
}

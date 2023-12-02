package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

public class CartItem {
    private String itemName;
    private double itemPrice;
    private String itemLocation;

    public CartItem(String itemName, double itemPrice, String itemLocation) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemLocation = itemLocation;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getItemLocation() {
        return itemLocation;
    }
}

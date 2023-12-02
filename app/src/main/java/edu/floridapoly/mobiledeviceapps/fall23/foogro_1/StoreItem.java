package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

public class StoreItem {
    private String storeName;
    private double storePrice;
    private String description; // Add a field for the description

    // Update the constructor to include a description parameter
    public StoreItem(String storeName, double storePrice, String description) {
        this.storeName = storeName;
        this.storePrice = storePrice;
        this.description = description; // Initialize the description field
    }

    // Getter for the store name
    public String getStoreName() {
        return storeName;
    }

    // Getter for the store price
    public double getStorePrice() {
        return storePrice;
    }

    // Getter for the description
    public String getDescription() {
        return description;
    }


}

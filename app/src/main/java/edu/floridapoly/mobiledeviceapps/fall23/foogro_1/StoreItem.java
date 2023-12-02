package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

public class StoreItem {
    private int id; // ID field is now an integer
    private String storeName;
    private double storePrice;
    private String description;

    public StoreItem(int id, String storeName, double storePrice, String description) {
        this.id = id; // Initialize the ID field
        this.storeName = storeName;
        this.storePrice = storePrice;
        this.description = description;
    }

    // Getter for ID
    public int getId() {
        return id;
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

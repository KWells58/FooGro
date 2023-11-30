package edu.floridapoly.mobiledeviceapps.fall23.foogro_1;

public class StoreItem {
    private String storeName;
    private double storePrice;

    public StoreItem(String storeName, double storePrice) {
        this.storeName = storeName;
        this.storePrice = storePrice;
    }

    public String getStoreName() {
        return storeName;
    }

    public double getStorePrice() {
        return storePrice;
    }
}

package cs.nyuad.se.inventory;

import java.util.HashMap;
import java.util.Map;

import cs.nyuad.se.database.DatabaseService;
import cs.nyuad.se.notification.NotificationService;

public class Inventory {
    private Map<String, Product> products = new HashMap<>();
    private NotificationService notificationService;
    private DatabaseService databaseService;
    private final int LOW_STOCK_THRESHOLD = 5;

    public Inventory(NotificationService notificationService, DatabaseService databaseService) {
        this.notificationService = notificationService;
        this.databaseService = databaseService;
    }

    /**
     * Adds a product to the inventory
     * If the product already exists, a notification is sent and no updates happen
     * @param product
     */
    public void addProduct(Product product) {
        if (databaseService.productExists(product.getId())) {
            notificationService.sendNotification(
                "Attempted duplicate product entry: " + product.getName()
            );
            return;
        }
        products.put(product.getId(), product);
        databaseService.saveProduct(product);
    }

    /**
     * Updates the quantity of a product in the inventory
     * If the quantity is below the LOW_STOCK_THRESHOLD, a notification is sent
     * @param productId
     * @param newQuantity
     */
    public void updateQuantity(String productId, int newQuantity) {
        Product product = products.get(productId);
        if (product != null) {
            product.setQuantity(newQuantity);
            databaseService.saveProduct(product); // Saves updated quantity

            if (newQuantity < LOW_STOCK_THRESHOLD) {
                notificationService.sendNotification(
                    "Stock for product " + product.getName() + " is low: " + newQuantity
                );
            }
        }
    }

    public Product getProduct(String productId) {
        return products.get(productId);
    }
}

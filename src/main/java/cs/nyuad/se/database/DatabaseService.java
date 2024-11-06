package cs.nyuad.se.database;

import cs.nyuad.se.inventory.Product;

public interface DatabaseService {
    void saveProduct(Product product);
    Product getProductById(String productId);
    boolean productExists(String productId); 
}


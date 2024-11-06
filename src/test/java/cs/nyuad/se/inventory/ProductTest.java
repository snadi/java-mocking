package cs.nyuad.se.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {

    Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("PF1245", "Apple", 100);
    }

    @Test
    public void testProductConstruction() {
        assert(product.getId().equals("PF1245"));
        assert(product.getName() == "Apple");
        assert(product.getQuantity() == 100);
    }

    @Test
    public void testSetQuantity(){
        product.setQuantity(50);
        assert(product.getQuantity() == 50);
    }
    
}

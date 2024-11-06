package cs.nyuad.se.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs.nyuad.se.database.DatabaseService;
import cs.nyuad.se.notification.NotificationService;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import java.lang.String;
import static org.mockito.Mockito.when;

public class InventoryTest {

    private DatabaseService databaseServiceMock;
    private NotificationService notificationServiceMock;
    private Inventory inventory;
    private Product product;

    @BeforeEach
    public void setUp(){
        databaseServiceMock = mock(DatabaseService.class);
        notificationServiceMock = mock(NotificationService.class);
        inventory = new Inventory(notificationServiceMock, databaseServiceMock);
        product = new Product("PF1245", "Apple", 100);
        inventory.addProduct(product);
    }

    @Test
    public void testAddProduct(){
        assert(inventory.getProduct("PF1245").equals(product));
        verify(databaseServiceMock).saveProduct(product);
    }

    @Test
    public void testAddProductExists(){
        when(databaseServiceMock.productExists("NEWID")).thenReturn(true);
        Product newProduct = new Product("NEWID", "Banana", 100);
        inventory.addProduct(newProduct);
        verify(notificationServiceMock).sendNotification("Attempted duplicate product entry: Banana");
        verify(databaseServiceMock, never()).saveProduct(newProduct);
        assert(inventory.getProduct(null) == null);
    }

    @Test
    public void testUpdateQuantity(){
        inventory.updateQuantity("PF1245", 50);

        assert(inventory.getProduct("PF1245").getQuantity() == 50);
        verify(notificationServiceMock, never()).sendNotification(any(String.class));
        verify(databaseServiceMock, times(2)).saveProduct(product);
    }

    @Test
    public void testUpdateQuantityLowInventory(){
        inventory.updateQuantity("PF1245", 4);

        assert(inventory.getProduct("PF1245").getQuantity() == 4);
        verify(notificationServiceMock).sendNotification("Stock for product Apple is low: 4");
        verify(databaseServiceMock, times(2)).saveProduct(product);
    }

    @Test
    public void testUpdateNonExistantProduct(){
        inventory.updateQuantity("NonexistantID", 50);
        assert(inventory.getProduct("NonexistantID") == null);
        assert(inventory.getProduct("PF1245").getQuantity() == 100);
        verify(notificationServiceMock, never()).sendNotification(any(String.class));
        verify(databaseServiceMock, times(1)).saveProduct(any(Product.class));
    }


    
}

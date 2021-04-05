package cz.cvut.fel.ts1.shop;

import cz.cvut.fel.ts1.archive.PurchasesArchive;
import cz.cvut.fel.ts1.storage.NoItemInStorage;
import cz.cvut.fel.ts1.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class EShopControllerTest {
    @Test
    public void process() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Test creation of eshop
        EShopController.startEShop();
        ArrayList<ShoppingCart> carts = EShopController.getCarts();
        Storage storage = EShopController.getStorage();
        PurchasesArchive purchasesArchive = EShopController.getArchive();
        ArrayList<Order> orders = EShopController.getOrders();

        assertEquals(0, carts.size());
        storage.printListOfStoredItems();;
        purchasesArchive.printItemPurchaseStatistics();
        assertEquals("STORAGE IS CURRENTLY CONTAINING:" + System.lineSeparator() +
                "ITEM PURCHASE STATISTICS:", outputStream.toString().trim());
        outputStream.reset();

        // Test creation of a new shopping cart
        ShoppingCart cart = EShopController.newCart();

        assertEquals(1, EShopController.getCarts().size());

        // Test purchasing empty shopping cart
        try {
            EShopController.purchaseShoppingCart(cart, "Vojtěch Plocica", "Your Mom 69/420");
            assertEquals("Error: shopping cart is empty", outputStream.toString().trim());
            outputStream.reset();
        } catch (NoItemInStorage error) {
            error.printStackTrace();
        }

        // Test purchasing item that is not in storage
        Item[] items = {
                new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
                new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
                new StandardItem(3, "Screwdriver", 200, "TOOLS", 5),
                new DiscountedItem(4, "Star Wars Jedi buzzer", 500, "GADGETS", 30, "1.8.2013", "1.12.2013"),
                new DiscountedItem(5, "Angry bird cup", 300, "GADGETS", 20, "1.9.2013", "1.12.2013"),
                new DiscountedItem(6, "Soft toy Angry bird (size 40cm)", 800, "GADGETS", 10, "1.8.2013", "1.12.2013")
        };

        cart.addItem(items[0]);
        assertEquals("Item with ID " + items[0].getID() + " added to the shopping cart.", outputStream.toString().trim());
        outputStream.reset();
        assertSame(items[0], cart.getCartItems().get(0));
        ShoppingCart finalCart = cart;
        assertThrows(NoItemInStorage.class, () -> {
            EShopController.purchaseShoppingCart(finalCart, "", "");
        });

        // Test adding and removing items from cart
        cart.removeItem(items[0].getID());
        assertEquals(0, cart.getItemsCount());
        assertEquals("Item with ID " + items[0].getID() + " removed from the shopping cart.", outputStream.toString().trim());
        outputStream.reset();
        cart.addItem(items[1]);
        assertEquals(1, cart.getItemsCount());
        assertEquals("Item with ID " + items[1].getID() + " added to the shopping cart.", outputStream.toString().trim());
        outputStream.reset();

        // Test purchasing multiple items
        int[] itemCount = {10,10,4,5,10,2};
        cart = EShopController.newCart();

        for (int i = 0; i < items.length; i++) {
            storage.insertItems(items[i], itemCount[i]);
            cart.addItem(items[i]);
            assertEquals(1 + i, cart.getItemsCount());
            assertEquals("Item with ID " + items[i].getID() + " added to the shopping cart.", outputStream.toString().trim());
            outputStream.reset();
        }
        try {
            EShopController.purchaseShoppingCart(cart, null, "Peepee street");
            for (int i = 0; i < items.length; i++) {
                assertEquals(itemCount[i] - 1, storage.getItemCount(items[i]));
                assertEquals(1, purchasesArchive.getOrderArchiveCount());
                assertEquals(1, purchasesArchive.getHowManyTimesHasBeenItemSold(items[i]));
            }
        } catch (NoItemInStorage error) {
            error.printStackTrace();
        }

        // Test purchasing item in storage
        int expectedCount = itemCount[1] - 2;
        try {
            EShopController.purchaseShoppingCart(cart, null, null);
            assertEquals(expectedCount, storage.getItemCount(items[1]));
            assertEquals(2, purchasesArchive.getOrderArchiveCount());
            assertEquals(2, purchasesArchive.getHowManyTimesHasBeenItemSold(items[1]));
        } catch (NoItemInStorage error) {
            error.printStackTrace();
        }
    }
}
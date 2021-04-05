package cz.cvut.fel.ts1.archive;

import cz.cvut.fel.ts1.shop.Item;
import cz.cvut.fel.ts1.shop.Order;
import cz.cvut.fel.ts1.shop.ShoppingCart;
import cz.cvut.fel.ts1.shop.StandardItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PurchasesArchiveTest {
    private ByteArrayOutputStream outputStream;
    private PurchasesArchive purchasesArchive;
    private HashMap<Integer, ItemPurchaseArchiveEntry> itemPurchaseArchive = new HashMap<>();

    @Mock
    private ArrayList<Order> mockedOrderArchive;

    @Mock
    ItemPurchaseArchiveEntry purchaseArchiveEntryMock;

    @Mock
    StandardItem item;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        when(purchaseArchiveEntryMock.toString()).thenReturn("ITEM  Item   ID 0   NAME Standard Item   CATEGORY Category   HAS BEEN SOLD 1 TIMES");
        when(purchaseArchiveEntryMock.getCountHowManyTimesHasBeenSold()).thenReturn(1);
        itemPurchaseArchive.put(0, purchaseArchiveEntryMock);
        purchasesArchive = new PurchasesArchive(itemPurchaseArchive, mockedOrderArchive);
    }

    @Test
    void printItemPurchaseStatistics_EmptyArchive_NoStatistics() {
        itemPurchaseArchive = new HashMap<>();
        purchasesArchive = new PurchasesArchive(itemPurchaseArchive, mockedOrderArchive);
        purchasesArchive.printItemPurchaseStatistics();

        assertEquals("ITEM PURCHASE STATISTICS:", outputStream.toString().trim());
    }

    @Test
    void printItemPurchaseStatistics_OneItem_CorrectStatistics() {
        purchasesArchive.printItemPurchaseStatistics();

        assertEquals("ITEM PURCHASE STATISTICS:" + System.lineSeparator() +
                "ITEM  Item   ID 0   NAME Standard Item   CATEGORY Category   HAS BEEN SOLD 1 TIMES", outputStream.toString().trim());
    }

    @Test
    void getHowManyTimesHasBeenItemSold_InArchiveOneSold_One() {
        when(item.getID()).thenReturn(0);
        int expectedSoldCount = 1;

        assertEquals(expectedSoldCount, purchasesArchive.getHowManyTimesHasBeenItemSold(item));
    }

    @Test
    void getHowManyTimesHasBeenItemSold_NotInArchive_Zero() {
        when(item.getID()).thenReturn(1);
        int expectedSoldCount = 0;

        assertEquals(expectedSoldCount, purchasesArchive.getHowManyTimesHasBeenItemSold(item));
    }

    @Test
    void putOrderToPurchasesArchive_orderItemAlreadyInArchive_IncreasedSoldCount() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        when(item.getID()).thenReturn(0);
        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "customerName", "customerAddress");

        purchasesArchive.putOrderToPurchasesArchive(order);

        verify(purchaseArchiveEntryMock, times(1)).increaseCountHowManyTimesHasBeenSold(anyInt());
    }

    @Test
    void putOrderToPurchasesArchive_orderItemNotInArchive_NewPurchaseArchiveEntry() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        when(item.getID()).thenReturn(1);
        ShoppingCart cart = new ShoppingCart(items);
        Order order = new Order(cart, "customerName", "customerAddress");

        try (MockedConstruction mocked = mockConstruction(ItemPurchaseArchiveEntry.class)) {
            purchasesArchive.putOrderToPurchasesArchive(order);

            assertEquals(1, mocked.constructed().size());
        }
    }
}
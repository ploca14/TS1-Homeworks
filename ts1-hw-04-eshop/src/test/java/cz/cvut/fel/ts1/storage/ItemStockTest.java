package cz.cvut.fel.ts1.storage;

import cz.cvut.fel.ts1.shop.Item;
import cz.cvut.fel.ts1.shop.StandardItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ItemStockTest {
    private final StandardItem standardItem = new StandardItem(1, "Standard item", 69.69F, "Standard", 420);
    private final ItemStock itemStock = new ItemStock(standardItem);


    @Test
    public void constructor_ValidData_Success() {
        assertEquals(standardItem, itemStock.getItem());
    }

    @ParameterizedTest(
            name =  "Initial stock {0} increased by {1} should end up being {2}"
    )
    @CsvSource({
            "0, 1, 1",
            "60, 9, 69",
    })
    void increaseItemCount_fromAByB_returnsC(int a, int b, int c) {
        itemStock.setCount(a);
        itemStock.IncreaseItemCount(b);

        assertEquals(c, itemStock.getCount());
    }

    @ParameterizedTest(
            name =  "Initial stock {0} decreased by {1} should end up being {2}"
    )
    @CsvSource({
            "1, 1, 0",
            "0, 69, -69"
    })
    void decreaseItemCount_fromAByB_returnsC(int a, int b, int c) {
        itemStock.setCount(a);
        itemStock.decreaseItemCount(b);

        assertEquals(c, itemStock.getCount());
    }
}
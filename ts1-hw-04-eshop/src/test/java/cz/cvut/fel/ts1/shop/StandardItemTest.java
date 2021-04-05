package cz.cvut.fel.ts1.shop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StandardItemTest {

    @Test
    public void constructor_ValidData_Success() {
        int id = 1;
        String name = "Standard item";
        float price = 999.99F;
        String category = "Standard";
        int loyaltyPoints = 10;
        StandardItem standardItem = new StandardItem(id, name, price, category, loyaltyPoints);

        assertEquals(id, standardItem.getID());
        assertEquals(name, standardItem.getName());
        assertEquals(price, standardItem.getPrice());
        assertEquals(category, standardItem.getCategory());
        assertEquals(loyaltyPoints, standardItem.getLoyaltyPoints());
    }

    @Test
    public void copy_equalsButNotSame_True() {
        StandardItem original = new StandardItem(5, "Original standard item", 499.99F, "Original", 0);
        StandardItem copy = original.copy();

        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @ParameterizedTest
    @MethodSource("provideItems")
    public void equals_ItemOneAndItemTwo_(
            Item itemOne,
            Item itemTwo,
            Boolean expectedResult
    ) {
        assertEquals(itemOne.equals(itemTwo), expectedResult);
    }

    private static Stream<Arguments> provideItems() {
        return Stream.of(
                Arguments.of(
                    new StandardItem(5, "Equally standard item", 420F, "Equal", 69),
                    new StandardItem(5, "Equally standard item", 420F, "Equal", 69),
                    true
                ),
                Arguments.of(
                    new StandardItem(5, "Equally standard item", 420F, "Equal", 69),
                    new StandardItem(4, "Equally standard item", 420F, "Equal", 69),
                    false
                ),
                Arguments.of(
                    new StandardItem(5, "Equally standard item", 420F, "Equal", 69),
                    new StandardItem(5, "Different standard item", 420F, "Equal", 69),
                    false
                ),
                Arguments.of(
                    new StandardItem(5, "Equally standard item", 420F, "Equal", 69),
                    new StandardItem(5, "Equally standard item", 69.69F, "Equal", 69),
                    false
                ),
                Arguments.of(
                    new StandardItem(5, "Equally standard item", 420F, "Equal", 69),
                    new StandardItem(5, "Equally standard item", 420F, "Different", 69),
                    false
                ),
                Arguments.of(
                    new StandardItem(5, "Equally standard item", 420F, "Equal", 69),
                    new StandardItem(5, "Equally standard item", 420F, "Equal", 420),
                    false
                )
        );
    }
}
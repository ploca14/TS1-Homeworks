package cz.cvut.fel.ts1.shop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    public void constructor_ValidDataEmptyCart_Success() {
        ShoppingCart cart = new ShoppingCart();
        String customerName = "John Butt";
        String customerAddress = "2727 Butt street, Pennsylvania, USA";
        Order order = new Order(cart, customerName, customerAddress);
        int expectedState = 0;

        assertEquals(cart.getCartItems(), order.getItems());
        assertEquals(customerName, order.getCustomerName());
        assertEquals(customerAddress, order.getCustomerAddress());
        assertEquals(expectedState, order.getState());
    }

    @Test
    public void constructor_ValidDataItemInCart_Success() {
        ArrayList<Item> items = new ArrayList<>();
        Item item = new StandardItem(1, "Item", 69.69F, "Category", 420);
        items.add(item);
        ShoppingCart cart = new ShoppingCart(items);
        String customerName = "John Butt";
        String customerAddress = "2727 Butt street, Pennsylvania, USA";
        int expectedState = 0;
        Order order = new Order(cart, customerName, customerAddress);

        assertEquals(cart.getCartItems(), order.getItems());
        assertEquals(customerName, order.getCustomerName());
        assertEquals(customerAddress, order.getCustomerAddress());
        assertEquals(expectedState, order.getState());
    }

    @Test
    public void constructorWithState_ValidDataEmptyCart_Success() {
        ShoppingCart cart = new ShoppingCart();
        String customerName = "John Butt";
        String customerAddress = "2727 Butt street, Pennsylvania, USA";
        int state = 1;
        Order order = new Order(cart, customerName, customerAddress, state);

        assertEquals(cart.getCartItems(), order.getItems());
        assertEquals(customerName, order.getCustomerName());
        assertEquals(customerAddress, order.getCustomerAddress());
        assertEquals(state, order.getState());
    }

    @Test
    public void constructorWithState_ValidDataItemInCart_Success() {
        ArrayList<Item> items = new ArrayList<>();
        Item item = new StandardItem(1, "Item", 69.69F, "Category", 420);
        items.add(item);
        ShoppingCart cart = new ShoppingCart(items);
        String customerName = "John Butt";
        String customerAddress = "2727 Butt street, Pennsylvania, USA";
        int state = 1;
        Order order = new Order(cart, customerName, customerAddress, state);

        assertEquals(cart.getCartItems(), order.getItems());
        assertEquals(customerName, order.getCustomerName());
        assertEquals(customerAddress, order.getCustomerAddress());
        assertEquals(state, order.getState());
    }

    @Test
    public void constructor_NullValues_Exception() {
        assertThrows(Exception.class, () -> new Order(null, null, null));
    }
}
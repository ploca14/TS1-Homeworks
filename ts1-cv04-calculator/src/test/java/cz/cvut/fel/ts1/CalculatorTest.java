package cz.cvut.fel.ts1;

import org.junit.jupiter.api.*;

public class CalculatorTest {
    Calculator calc;

    @BeforeAll
    public static void init() {
    }

    @BeforeEach
    public void setup() {
        calc = new Calculator();
    }

    @Test
    public void Add_TwoPlusFour_Six() {
        int a = 2;
        int b = 4;
        int expectedResult = 6;

        int result = calc.add(a, b);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void Add_TenMinusFive_Five() {
        int a = 10;
        int b = 5;
        int expectedResult = 5;

        int result = calc.subtract(a, b);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void Add_TwoTimesFour_Eight() {
        int a = 2;
        int b = 4;
        int expectedResult = 8;

        int result = calc.multiply(a, b);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void Divide_TwoDividedByOne_Two() {
        int a = 2;
        int b = 1;
        int expectedResult = 2;

        int result = calc.divide(a, b);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void Divide_TwoDividedByZero_ExceptionThrow() {
        int a = 2;
        int b = 0;

        Assertions.assertThrows(Exception.class,() -> calc.divide(a, b));
    }

    @AfterEach
    public void closeEach() {

    }

    @AfterAll
    public static void closeAll() {

    }
}
